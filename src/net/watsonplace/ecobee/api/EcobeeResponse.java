package net.watsonplace.ecobee.api;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class EcobeeResponse<T extends APIObject> {
	private static final Logger logger = Logger.getLogger(EcobeeResponse.class.getName());
	
	private static final Map<String, Type> typeMap;
	static {
		Map<String, Type> types = new HashMap<>();
		putTypes(types, Action.APIObjectIdentifier, Action.APIObjectType);
		putTypes(types, Alert.APIObjectIdentifier, Alert.APIObjectType);
		putTypes(types, Climate.APIObjectIdentifier, Climate.APIObjectType);
		putTypes(types, Device.APIObjectIdentifier, Device.APIObjectType);
		putTypes(types, Electricity.APIObjectIdentifier, Electricity.APIObjectType);
		putTypes(types, ElectricityDevice.APIObjectIdentifier, ElectricityDevice.APIObjectType);
		putTypes(types, ElectricityTier.APIObjectIdentifier, ElectricityTier.APIObjectType);
		putTypes(types, EquipmentSetting.APIObjectIdentifier, EquipmentSetting.APIObjectType);
		putTypes(types, Event.APIObjectIdentifier, Event.APIObjectType);
		putTypes(types, ExtendedRuntime.APIObjectIdentifier, ExtendedRuntime.APIObjectType);
		putTypes(types, GeneralSetting.APIObjectIdentifier, GeneralSetting.APIObjectType);
		putTypes(types, HouseDetails.APIObjectIdentifier, HouseDetails.APIObjectType);
		putTypes(types, LimitSetting.APIObjectIdentifier, LimitSetting.APIObjectType);
		putTypes(types, Location.APIObjectIdentifier, Location.APIObjectType);
		putTypes(types, Management.APIObjectIdentifier, Management.APIObjectType);
		putTypes(types, NotificationSettings.APIObjectIdentifier, NotificationSettings.APIObjectType);
		putTypes(types, Output.APIObjectIdentifier, Output.APIObjectType);
		putTypes(types, Page.APIObjectIdentifier, Page.APIObjectType);
		putTypes(types, Program.APIObjectIdentifier, Program.APIObjectType);
		putTypes(types, Runtime.APIObjectIdentifier, Runtime.APIObjectType);
		putTypes(types, SecuritySettings.APIObjectIdentifier, SecuritySettings.APIObjectType);
		putTypes(types, Selection.APIObjectIdentifier, Selection.APIObjectType);
		putTypes(types, Sensor.APIObjectIdentifier, Sensor.APIObjectType);
		putTypes(types, Settings.APIObjectIdentifier, Settings.APIObjectType);
		putTypes(types, State.APIObjectIdentifier, State.APIObjectType);
		putTypes(types, Technician.APIObjectIdentifier, Technician.APIObjectType);
		putTypes(types, Thermostat.APIObjectIdentifier, Thermostat.APIObjectType);
		putTypes(types, Utility.APIObjectIdentifier, Utility.APIObjectType);
		putTypes(types, Version.APIObjectIdentifier, Version.APIObjectType);
		putTypes(types, Weather.APIObjectIdentifier, Weather.APIObjectType);
		putTypes(types, WeatherForecast.APIObjectIdentifier, WeatherForecast.APIObjectType);
		typeMap = types;
	}
	private static void putTypes(Map<String, Type> aMap, String[] identifiers, Type type) {
		for (String identifier : identifiers) {
			aMap.put(identifier, type);
		}
	}
	
	private Page page;
	private HashSet<T> response = new HashSet<>();
	private Status status;
	
	@SuppressWarnings("unchecked")
	public EcobeeResponse(String json) throws Exception {
		Gson gson = new GsonBuilder().create();
		JsonReader rdr = new JsonReader(new StringReader(json));
		rdr.beginObject();
		while (rdr.hasNext()) {
			String name = rdr.nextName();
			switch (name) {
			case "page":
				page = gson.fromJson(rdr, Page.class);
				break;
			case "status":
				status = gson.fromJson(rdr, Status.class);
				break;
			default:
				Type type = typeMap.get(name);
				if (type == null) {
					throw new Exception("Unknown APIObject type");
				}
				if (rdr.peek() == JsonToken.BEGIN_ARRAY) {
					rdr.beginArray();
					while (rdr.hasNext()) {
						response.add((T) gson.fromJson(rdr, type));
					}
					rdr.endArray();
				} else {
					response.add((T)gson.fromJson(rdr, type));
				}
			}
		}
		rdr.endObject();
		rdr.close();
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
