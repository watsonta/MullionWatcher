package net.watsonplace.climate.ecobee;

import java.util.Set;

import net.watsonplace.climate.ClimateControl;
import net.watsonplace.climate.Thermostat;
import net.watsonplace.climate.Runtime;
import net.watsonplace.climate.Weather;
import net.watsonplace.ecobee.api.API;
import net.watsonplace.ecobee.api.APIWrapper;

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
	
	// Ecobee
	private API api;
	private Set<net.watsonplace.ecobee.api.Thermostat> ecobeeThermostats;
	
	// Local
	private Set<Thermostat> thermostats;

	// Climate state variables
	private float temperature = Float.MAX_VALUE;
	private int humidity = 50;
	private long timestamp = 0;
	
	private Control() throws Exception {
		api = APIWrapper.getInstance();
	}
	
	public void run() {
		while (true) {
			int delay = 15; // Normal time between checks
			
			try {
				
				// Initialize extreme holders
				float temperature = Float.MIN_VALUE;
				int humidity = 0;
				
				// Fetch all Ecobee thermostats and populate the abstraction
				ecobeeThermostats = api.getThermostats();
				for (net.watsonplace.ecobee.api.Thermostat ecobeeThermostat : ecobeeThermostats) {
					Thermostat thermostat = new Thermostat();
					thermostat.setIdentifier(ecobeeThermostat.getIdentifier());
					thermostat.setName(ecobeeThermostat.getName());
					thermostat.setThermostatTime(ecobeeThermostat.getThermostatTime());
					thermostat.setUtcTime(ecobeeThermostat.getUtcTime());
					// Populate the abstraction runtime from the Ecobee runtime
					net.watsonplace.ecobee.api.Runtime ecobeeRuntime = ecobeeThermostat.getRuntime();
					if (ecobeeRuntime != null) {
						Runtime runtime = new Runtime();
						runtime.setActualTemperature(ecobeeRuntime.getActualTemperature());
						runtime.setActualHumidity(ecobeeRuntime.getActualHumidity());
						runtime.setDesiredCool(ecobeeRuntime.getDesiredCool());
						runtime.setDesiredHeat(ecobeeRuntime.getDesiredHeat());
						runtime.setDesiredHumidity(ecobeeRuntime.getDesiredDehumidity());
						runtime.setRuntimeDate(ecobeeRuntime.getRuntimeDate());
						thermostat.setRuntime(runtime);
					}
					// Populate the abstraction weather from the Ecobee weather forecast
					net.watsonplace.ecobee.api.WeatherForecast[] ecobeeForecasts = ecobeeThermostat.getWeather().getForecasts();
					if (ecobeeForecasts.length > 0) {
						Weather weather = new Weather();
						weather.setDateTime(ecobeeForecasts[0].getDateTime());
						weather.setTemperature(ecobeeForecasts[0].getTemperature());
						weather.setRelativeHumidity(ecobeeForecasts[0].getRelativeHumidity());
						weather.setWindSpeed(ecobeeForecasts[0].getWindSpeed());
						weather.setWindDirection(ecobeeForecasts[0].getWindDirection());
						thermostat.setWeather(weather);
					}

					// Find the extremes among all the thermostats
					if (thermostat.getRuntime() != null) {
						float t = thermostat.getRuntime().getActualTemperature();
						temperature = t > temperature ? t : temperature; // highest temperature
						int h = thermostat.getRuntime().getActualHumidity();
						humidity = h > humidity ? h : humidity; // highest humidity
					}
				}
				
				// Update climate state
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
				delay = 5; // Try again in 5 minutes
			} finally {
				try {
					Thread.sleep(delay*60*1000); // Delay
				} catch (InterruptedException e) {}
			}
		}
	}

	@Override
	public Set<Thermostat> getThermostats() {
		return thermostats;
	}

	// Returns lowest temperature (worst case) among all thermostats
	@Override
	public float getLowestTemperature() {
		return temperature;
	}

	// Returns highest humidity (worst case) among all thermostats
	@Override
	public int getHighestHumidity() {
		return humidity;
	}
	
	@Override
	public long getSampleTimestamp() {
		return timestamp;
	}

	@Override
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int hours) throws Exception {
		api.setHold(desiredHeatTemp, desiredColdTemp, hours);
	}

}
