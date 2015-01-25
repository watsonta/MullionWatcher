package net.watsonplace.ecobee.api;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class EcobeeResponse<T> {
	private Page page;
	private HashSet<T> response = new HashSet<>();
	private Status status;
	
	@SuppressWarnings("unchecked")
	public EcobeeResponse(String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		JsonReader rdr = new JsonReader(new StringReader(json));
		rdr.beginObject();
		while (rdr.hasNext()) {
			switch (rdr.nextName()) {
			case "page":
				page = gson.fromJson(rdr, Page.class);
				break;
			case "thermostatList":
				rdr.beginArray();
				while (rdr.hasNext()) {
					response.add((T)gson.fromJson(rdr, Thermostat.class));
				}
				rdr.endArray();
				break;
			case "status":
				status = gson.fromJson(rdr, Status.class);
				break;
			default:
				rdr.close();
				throw new Exception("Unrecognized element");
			}
		}
		rdr.endObject();
	}
	
	public Page getPage() {
		return page;
	}
	
	public Set<T> getResponse() {
		return response;
	}
	
	public Status getStatus() {
		return status;
	}
}
