package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Status extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "status" };
	public static Type APIObjectType = new TypeToken<Status>(){}.getType();
	
	private int code; // The status code for this status.
	private String message; // The detailed message for this status.
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
