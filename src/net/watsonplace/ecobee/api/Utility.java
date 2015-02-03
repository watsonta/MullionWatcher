package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Utility extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "utility" };
	public static Type APIObjectType = new TypeToken<Utility>(){}.getType();
	
	String name; // The Utility company name.
	String phone; // The Utility company contact phone number.
	String email; // The Utility company email address.
	String web; // The Utility company web site.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
