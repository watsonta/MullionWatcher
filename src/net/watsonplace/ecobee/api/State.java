package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class State extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "state" };
	public static Type APIObjectType = new TypeToken<State>(){}.getType();
	
	Integer maxValue; // The maximum value the sensor can generate.
	Integer minValue; // The minimum value the sensor can generate.
	String type; // Values: coolHigh, coolLow, heatHigh, heatLow, high, low, transitionCount, normal.
	Action[] actions; // The list of StateAction objects associated with the sensor.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
