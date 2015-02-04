package net.watsonplace.climate;

public class Weather {
	private String dateTime; // The time stamp of the weather forecast.
	private Float temperature; // The current temperature.
	private Integer relativeHumidity; // The current humidity.
	private Float windSpeed; // The wind speed in mph.
	private String windDirection; // The wind direction.
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Integer getRelativeHumidity() {
		return relativeHumidity;
	}
	public void setRelativeHumidity(Integer relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	public Float getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(Float windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
}
