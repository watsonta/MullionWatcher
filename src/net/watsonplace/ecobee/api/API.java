package net.watsonplace.ecobee.api;

import java.util.Set;

public interface API {
	
	public Set<Thermostat> getThermostats() throws Exception;
	
	public float getCoolingSetPoint(Thermostat thermostat);
	
	public float getHeatingSetPoint(Thermostat thermostat);
	
	public void setHold(int desiredHeatTemp, int desiredColdTemp, int holdHours) throws Exception;
	
}
