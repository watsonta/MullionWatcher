package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Technician extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "technician" };
	public static Type APIObjectType = new TypeToken<Technician>(){}.getType();
	
	String contractorRef; // The internal ecobee unique identifier for this contractor.
	String name; // The company name of the technician.
	String phone; // The technician's contact phone number.
	String streetAddress; // The technician's street address.
	String city; // The technician's city.
	String provinceState; // The technician's State or Province.
	String country; // The technician's country.
	String postalCode; // The technician's ZIP or Postal Code.
	String email; // The technician's email address.
	String web; // The technician's web site.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
