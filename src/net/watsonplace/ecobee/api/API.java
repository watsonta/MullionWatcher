package net.watsonplace.ecobee.api;

import java.util.Set;

public interface API {
	
	public Set<Thermostat> getThermostats() throws Exception;
	
}
