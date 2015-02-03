package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Program extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "program" };
	public static Type APIObjectType = new TypeToken<Program>(){}.getType();
	
	String[][] schedule; // The Schedule object defining the program schedule.
	Climate[] climates; // The list of Climate objects defining all the climates in the program schedule.
	String currentClimateRef; // The currently active climate, identified by its ClimateRef.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
