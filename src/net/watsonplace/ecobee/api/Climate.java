package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Climate extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "climate" };
	public static Type APIObjectType = new TypeToken<Climate>(){}.getType();
	
	String name; //  	no 	yes 	The unique climate name. The name may be changed without affecting the program integrity so long as uniqueness is maintained.
	String climateRef; //  	yes 	no 	The unique climate identifier. Changing the identifier is not possible and it is generated on the server for each climate. If this value is not supplied a new climate will be created. For the default climates and existing user created climates the climateRef should be supplied - see note above.
	Boolean isOccupied; //  	no 	no 	A flag indicating whether the property is occupied by persons during this climate
	Boolean isOptimized; //  	no 	no 	A flag indicating whether ecobee optimized climate settings are used by this climate.
	String coolFan; //  	no 	no 	The cooling fan mode. Default: on. Values: auto, on.
	String heatFan; //  	no 	no 	The heating fan mode. Default: on. Values: auto, on.
	String vent; //  	no 	no 	The ventilator mode. Default: off. Values: auto, minontime, on, off.
	Integer ventilatorMinOnTime; //  	no 	no 	The minimum time, in minutes, to run the ventilator each hour.
	String owner; //  	no 	no 	The climate owner. Default: system. Values: adHoc, demandResponse, quickSave, sensorAction, switchOccupancy, system, template, user.
	String type; //  	no 	no 	The type of climate. Default: program. Values: calendarEvent, program.
	Integer colour; //  	no 	no 	The integer conversion of the HEX color value used to display this climate on the thermostat and on the web portal.
	Integer coolTemp; //  	no 	no 	The cool temperature for this climate.
	Integer heatTemp; //  	no 	no 	The heat temperature for this climate.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
