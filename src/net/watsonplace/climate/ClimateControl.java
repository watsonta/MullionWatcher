package net.watsonplace.climate;

import java.util.Set;

import net.watsonplace.ecobee.api.Thermostat;

public interface ClimateControl {
	
	public Set<Thermostat> getThermostats();

	public float getLowestTemperature();
	
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int hours) throws Exception;
	
	public int getHighestHumidity();
	
	public long getSampleTimestamp();
	
	public float getCoolingSetPoint(Thermostat thermostat);
	
	public float getHeatingSetPoint(Thermostat thermostat);
	
}
