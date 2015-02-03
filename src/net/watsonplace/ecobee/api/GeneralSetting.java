package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GeneralSetting extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "generalsetting" };
	public static Type APIObjectType = new TypeToken<GeneralSetting>(){}.getType();
	
	Boolean enabled; // Boolean value representing whether or not alerts/reminders are enabled for this notification type or not.
	String type; // The type of notification. Possible values are: temp
	Boolean remindTechnician; // Boolean value representing whether or not alerts/reminders should be sent to the technician/contractor assoicated with the thermostat.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
