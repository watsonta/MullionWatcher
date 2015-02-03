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
	private Set<Thermostat> thermostats;
	private float temperature = Float.MAX_VALUE;
	private int humidity = 50;
	private long timestamp = 0;
	
	private Control() throws Exception {
		api = APIWrapper.getInstance();
	}
	
	public void run() {
		while (true) {
			try {
				thermostats = api.getThermostats();
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
			} catch (Exception e) {
				logger.error("Unable to fetch Ecobee temperature and humidity", e);
			} finally {
				try {
					Thread.sleep(15*60*1000); // Sleep for 15 minutes
				} catch (InterruptedException e) {}
			}
		}
	}

	@Override
	public Set<Thermostat> getThermostats() {
		return thermostats;
	}

	// Returns lowest temperature among all thermostats
	@Override
	public float getLowestTemperature() {
		return temperature;
	}

	@Override
	public int getHighestHumidity() {
		return humidity;
	}
	
	@Override
	public long getSampleTimestamp() {
		return timestamp;
	}

	@Override
	public float getCoolingSetPoint(Thermostat thermostat) {
		Runtime runtime = thermostat.getRuntime();
		return runtime.getDesiredCool()/10.0f;
	}

	@Override
	public float getHeatingSetPoint(Thermostat thermostat) {
		Runtime runtime = thermostat.getRuntime();
		return runtime.getDesiredHeat()/10.0f;
	}
	
	@Override
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int hours) throws Exception {
		api.setHold(desiredHeatTemp, desiredColdTemp, hours);
	}

}
