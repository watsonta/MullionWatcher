package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ElectricityTier extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "electricitytier" };
	public static Type APIObjectType = new TypeToken<ElectricityTier>(){}.getType();
	
	String name; // The tier name as defined by the Utility. May be an empty string if the tier is undefined or the usage falls outside the defined tiers.
	String consumption; // The last daily consumption reading collected. The reading format and precision is to three decimal places in kWh.
	String cost; // The daily cumulative tier cost in dollars if defined by the Utility. May be an empty string if undefined.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
