package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EquipmentSetting extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "equipmentsetting" };
	public static Type APIObjectType = new TypeToken<EquipmentSetting>(){}.getType();

	
	String filterLastChanged; // The date the filter was last changed for this equipment. String format: YYYY-MM-DD
	Integer filterLife; // The value representing the life of the filter. This value is expressed in month or hour, which is specified in the the filterLifeUnits property.
	String filterLifeUnits; // The units the filterLife field is measured in. Possible values are: month, hour. month has a range of 1 - 12. hour has a range of 100 - 10000.
	String remindMeDate; // The date the reminder will be triggered. This is a read-only field and cannot be modified through the API. The value is calculated and set by the thermostat.
	Boolean enabled; // Value representing whether or not alerts/reminders are enabled for this notification type or not.
	String type; // The type of notification. Possible values are: hvac, furnaceFilter, humidifierFilter, dehumidifierFilter, ventilator, ac, airFilter, airCleaner, uvLamp
	Boolean remindTechnician; // Value representing whether or not alerts/reminders should be sent to the technician/contractor assoicated with the thermostat.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
