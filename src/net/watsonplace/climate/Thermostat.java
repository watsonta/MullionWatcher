package net.watsonplace.climate;

public class Thermostat {
	private String identifier; // The unique thermostat serial number.
	private String name; // A user defined name for a thermostat.
	private String thermostatTime; // The current time in the thermostat's time zone
	private String utcTime; // The current time in UTC.
	private Runtime runtime; // The Runtime state object for the thermostat
	private Weather weather; // The Weather object linked to the thermostat representing the current weather on the thermostat.
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThermostatTime() {
		return thermostatTime;
	}

	public void setThermostatTime(String thermostatTime) {
		this.thermostatTime = thermostatTime;
	}

	public String getUtcTime() {
		return utcTime;
	}

	public void setUtcTime(String utcTime) {
		this.utcTime = utcTime;
	}

	public Runtime getRuntime() {
		return runtime;
	}

	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

}
