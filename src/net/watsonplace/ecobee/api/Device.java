package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Device extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "device" };
	public static Type APIObjectType = new TypeToken<Device>(){}.getType();
	
	Integer deviceId; // A unique ID for the device
	String name; // The user supplied device name
	Sensor[] sensors; // The list of Sensor Objects associated with the device.
	Output[] outputs; // The list of Output Objects associated with the device 

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
