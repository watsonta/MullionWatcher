package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class LimitSetting extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "limitsetting" };
	public static Type APIObjectType = new TypeToken<LimitSetting>(){}.getType();
	
	Integer limit; // The value of the limit to set. For temperatures the value is expressed as degrees Fahrenheit, multipled by 10. For humidity values are expressed as a percentage from 5 to 95. See here for more information.
	Boolean enabled; // Boolean value representing whether or not alerts/reminders are enabled for this notification type or not.
	String type; // The type of notification. Possible values are: lowTemp, highTemp, lowHumidity, highHumidity, auxHeat, auxOutdoor
	Boolean remindTechnician; // Boolean value representing whether or not alerts/reminders should be sent to the technician/contractor associated with the thermostat.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
