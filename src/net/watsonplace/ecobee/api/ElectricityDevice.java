package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ElectricityDevice extends APIObject {
	public static String[] APIObjectIdentifier = { "electricitydevice" };
	public static Type APIObjectType = new TypeToken<ElectricityDevice>(){}.getType();

	String name; // The name of the device
	ElectricityTier[] tiers; // The list of Electricity Tiers containing the break down of daily electricity consumption of the device for the day, broken down per pricing tier.
	String lastUpdate; // The last date/time the reading was updated in UTC time.
	String[] cost; // The last three daily electricity cost reads from the device in cents with a three decimal place precision.
	String[] consumption; // The last three daily electricity consumption reads from the device in KWh with a three decimal place precision.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
