package net.watsonplace.climate.ecobee;

import java.util.Set;

import net.watsonplace.climate.ClimateControl;
import net.watsonplace.ecobee.api.API;
import net.watsonplace.ecobee.api.APIWrapper;
import net.watsonplace.ecobee.api.Runtime;
import net.watsonplace.ecobee.api.Thermostat;

import org.apache.log4j.Logger;

public class Control extends Thread implements ClimateControl {
	private static final Logger logger = Logger.getLogger(Control.class.getName());

	private static Control singleton = null;
	public static Control getInstance() throws Exception {
		if (singleton == null) {
			singleton = new Control();
		}
		return singleton;
	}
	
	private API api;
	private float temperature = Float.MAX_VALUE;
	private int humidity = 50;
	private long timestamp = 0;
	
	private Control() throws Exception {
		api = APIWrapper.getInstance();
	}
	
	public void run() {
		while (true) {
			try {
				Set<Thermostat> thermostats = api.getThermostats();
				float temperature = Float.MIN_VALUE;
				int humidity = 0;
				for (Thermostat thermostat : thermostats) {
					Runtime r = thermostat.getRuntime();
					float t = r.getTemperature();
					temperature = t > temperature ? t : temperature; // highest temperature
					int h = r.getHumidity();
					humidity = h > humidity ? h : humidity; // highest humidity
				}
				timestamp = System.currentTimeMillis();
				if (humidity > 0) {
					this.humidity = humidity;
				} else {
					logger.error("Temperature fetch failed");
				}
				if (temperature > Float.MIN_VALUE) {
					this.temperature = temperature;
				} else {
					logger.error("Humidity fetch failed");
				}
				Thread.sleep(15*60*1000); // 15 minutes
			} catch (Exception e) {
				logger.error("Unable to fetch Ecobee temperature and humidity", e);
			}
		}
	}

	// Returns lowest temperature among all thermostats
	@Override
	public float getTemperature() {
		return temperature;
	}

	@Override
	public void setTemperature() throws Exception {
	}

	@Override
	public int getHumidity() {
		return humidity;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	public static void main(String[] args) {
		Control c = null;
		try {
			c = Control.getInstance();
			c.setDaemon(true);
			c.start();
		} catch (Exception e) {
			logger.fatal("Unable to instantiate Control object", e);
			System.exit(1);
		}

		while (true) {
			try {
				float temperature = c.getTemperature();
				int humidity = c.getHumidity();
				long timestamp = c.getTimestamp();
				logger.info("timestamp="+timestamp+", temperature="+temperature+", humidity="+humidity);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			
			// Wait 30 minutes
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {}
		}
	}

}
