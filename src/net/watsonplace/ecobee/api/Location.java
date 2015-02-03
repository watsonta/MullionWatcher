package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Location extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "location" };
	public static Type APIObjectType = new TypeToken<Location>(){}.getType();
	
	Integer timeZoneOffsetMinutes; // The timezone offset in minutes from UTC.
	String timeZone; // The Olson timezone the thermostat resides in (e.g America/Toronto).
	Boolean isDaylightSaving; // Whether the thermostat should factor in daylight savings when displaying the date and time.
	String streetAddress; // The thermostat location street address.
	String city; // The thermostat location city.
	String provinceState; // The thermostat location State or Province.
	String country; // The thermostat location country.
	String postalCode; // The thermostat location ZIP or Postal code.
	String phoneNumber; // The thermostat owner's phone number
	String mapCoordinates; // The lat/long geographic coordinates of the thermostat location.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
