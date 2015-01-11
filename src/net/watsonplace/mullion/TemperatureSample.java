package net.watsonplace.mullion;

public class TemperatureSample {
	private String sensorName;
	private float temperature;
	private long timestamp;
	
	public TemperatureSample(String sensorName, float temperature, long timestamp) {
		this.sensorName = sensorName;
		this.temperature = temperature;
		this.timestamp = timestamp;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

}
