package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Electricity extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "electricity" };
	public static Type APIObjectType = new TypeToken<Electricity>(){}.getType();

	ElectricityDevice[] devices; // The list of ElectricityDevice objects associated with the thermostat, each representing a device such as an electric meter or remote load control. 
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}
	
	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
