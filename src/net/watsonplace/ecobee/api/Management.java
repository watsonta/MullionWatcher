package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Management extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "management" };
	public static Type APIObjectType = new TypeToken<Management>(){}.getType();
	
	String administrativeContact; // The administrative contact name.
	String billingContact; // The billing contact name.
	String name; // The company name.
	String phone; // The phone number.
	String email; // The contact email address.
	String web; // The company web site.
	Boolean showAlertIdt; // Whether to show management alerts on the thermostat.
	Boolean showAlertWeb; // Whether to show management alerts in the web portal.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
