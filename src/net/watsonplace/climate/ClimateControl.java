package net.watsonplace.climate;

import java.util.Collection;

public interface ClimateControl {
	
	public Collection<Thermostat> getThermostats();

	public float getLowestTemperature();
	
	public int getHighestHumidity();
	
	public long getSampleTimestamp();
	
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int hours, boolean message) throws Exception;
	
}
