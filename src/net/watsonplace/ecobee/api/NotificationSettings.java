package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class NotificationSettings extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "notificationsettings" };
	public static Type APIObjectType = new TypeToken<NotificationSettings>(){}.getType();
	
	String[] emailAddresses; // The list of email addresses alerts and reminders will be sent to. The full list of email addresses must be sent in any update request. If any are missing from that list they will be deleted. If an empty list is sent, any email addresses will be deleted.
	Boolean emailNotificationsEnabled; // Boolean values representing whether or not alerts and reminders will be sent to the email addresses listed above when triggered.
	EquipmentSetting[] equipment; // The list of equipment specific alert and reminder settings.
	GeneralSetting[] general; // The list of general alert and reminder settings.
	LimitSetting[] limit; // The list of limit specific alert and reminder settings. 

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
