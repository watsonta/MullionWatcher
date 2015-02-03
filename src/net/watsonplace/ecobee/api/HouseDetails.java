package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HouseDetails extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "housedetails" };
	public static Type APIObjectType = new TypeToken<HouseDetails>(){}.getType();
	
	String style; // The style of house. Values: other, apartment, condominium, detached, loft, multiPlex, rowHouse, semiDetached, townhouse, and 0 for unknown.
	Integer size; // The size of the house in square feet.
	Integer numberOfFloors; // The number of floors or levels in the house.
	Integer numberOfRooms; // The number of rooms in the house.
	Integer numberOfOccupants; // The number of occupants living in the house.
	Integer age; // The age of house in years.
	Integer windowEfficiency; // This field defines the window efficiency of the house. Valid values are in the range 1 - 7. Changing the value of this field alters the settings the thermostat uses for the humidifier when in 'frost Control' mode.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
