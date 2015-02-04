package net.watsonplace.climate;

import java.util.Set;

import net.watsonplace.climate.Thermostat;

public interface ClimateControl {
	
	public Set<Thermostat> getThermostats();

	public float getLowestTemperature();
	
	public int getHighestHumidity();
	
	public long getSampleTimestamp();
	
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int hours) throws Exception;
	
}
