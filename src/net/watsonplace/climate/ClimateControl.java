package net.watsonplace.climate;

public interface ClimateControl {

	public float getTemperature();
	
	public void setTemperature() throws Exception;
	
	public int getHumidity();
	
	public long getTimestamp();
}
