package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Output extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "output" };
	public static Type APIObjectType = new TypeToken<Output>(){}.getType();
	
	String name; // The name of the outpute
	Integer zone; // The thermostat zone the output is associated with
	Integer outputId; // The unique output identifier number.
	String type; // The type of output. Values: compressor1, compressor2, dehumidifier, economizer, fan, heat1, heat2, heat3, heatPumpReversal, humidifer, none, occupancy, userDefined, ventilator, zoneCool, zoneFan, zoneHeat.
	Boolean sendUpdate; // Whether to send an update message.
	Boolean activeClosed; // If true, when this output is activated it will close the relay. Otherwise, activating the relay will open the relay.
	Integer activationTime; // Time to activate relay - in seconds.
	Integer deactivationTime; // Time to deactivate relay - in seconds.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
