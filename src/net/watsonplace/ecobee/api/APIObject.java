package net.watsonplace.ecobee.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class APIObject {
	public static final Gson gson = new GsonBuilder().create();

	public abstract String[] getAPIObjectIdentifier();
	
	public abstract String toJson();
	
	protected String toJson(String identifier) {
		StringBuilder sb = new StringBuilder("\""+identifier+"\": ");
//		Gson gson = new GsonBuilder().create();
		sb.append(gson.toJson(this));
		return sb.toString();
	}

}
