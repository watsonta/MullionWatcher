package net.watsonplace.climate;

import java.util.Collection;

public interface ClimateControl {
	
	public Collection<Thermostat> getThermostats();

	public float getLowestTemperature();
	
	public int getHighestHumidity();
	
	public long getSampleTimestamp();
	
	public void setHold(String[] identifier, int desiredHeatTemp, int desiredColdTemp, int hours, boolean message) throws Exception;
	
	public void releaseHold(String[] identifier) throws Exception;
	
}
