package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Selection extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "selection" };
	public static Type APIObjectType = new TypeToken<Selection>(){}.getType();
	
	enum SelType {registered, thermostats, managementSet };

	SelType selectionType; // The type of match data supplied: Values: none, thermostats, user, managementSet.
	String selectionMatch; // The match data based on selectionType (e.g. a list of thermostat idendifiers in the case of a selectionType of thermostats)
	Boolean includeRuntime; // Include the thermostat runtime object. If not specified, defaults to false.
	Boolean includeExtendedRuntime; // Include the extended thermostat runtime object. If not specified, defaults to false.
	Boolean includeElectricity; // Include the electricity readings object. If not specified, defaults to false.
	Boolean includeSettings; // Include the thermostat settings object. If not specified, defaults to false.
	Boolean includeLocation; // Include the thermostat location object. If not specified, defaults to false.
	Boolean includeProgram; // Include the thermostat program object. If not specified, defaults to false.
	Boolean includeEvents; // Include the thermostat calendar events objects. If not specified, defaults to false.
	Boolean includeDevice; // Include the thermostat device configuration objects. If not specified, defaults to false.
	Boolean includeTechnician; // Include the thermostat technician object. If not specified, defaults to false.
	Boolean includeUtility; // Include the thermostat utility company object. If not specified, defaults to false.
	Boolean includeManagement; // Include the thermostat management company object. If not specified, defaults to false.
	Boolean includeAlerts; // Include the thermostat's unacknowledged alert objects. If not specified, defaults to false.
	Boolean includeWeather; // Include the current thermostat weather forecast object. If not specified, defaults to false.
	Boolean includeHouseDetails; // Include the current thermostat house details object. If not specified, defaults to false.
	Boolean includeOemCfg; // Include the current thermostat OemCfg object. If not specified, defaults to false.
	Boolean includeEquipmentStatus; // Include the current thermostat equipment status information. If not specified, defaults to false.
	Boolean includeNotificationSettings; // Include the current thermostat alert and reminders settings. If not specified, defaults to false.
	Boolean includePrivacy; // Include the current thermostat privacy settings. Note: access to this object is restricted to callers with implict authentication, setting this value to true without proper credentials will result in an authentication exception.
	Boolean includeVersion; // Include the current firmware version the Thermostat is running. If not specified, defaults to false.
	Boolean includeSecuritySettings; // Include the current securitySettings object for the specified Thermostat. If not specified, defaults to false. 
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}
	
	public Selection(SelType selType, String selMatch) {
		this.selectionType = selType;
		this.selectionMatch = selMatch;
	}

	public void setIncludeRuntime(Boolean includeRuntime) {
		this.includeRuntime = includeRuntime;
	}

	public void setIncludeExtendedRuntime(Boolean includeExtendedRuntime) {
		this.includeExtendedRuntime = includeExtendedRuntime;
	}

	public void setIncludeElectricity(Boolean includeElectricity) {
		this.includeElectricity = includeElectricity;
	}

	public void setIncludeSettings(Boolean includeSettings) {
		this.includeSettings = includeSettings;
	}

	public void setIncludeLocation(Boolean includeLocation) {
		this.includeLocation = includeLocation;
	}

	public void setIncludeProgram(Boolean includeProgram) {
		this.includeProgram = includeProgram;
	}

	public void setIncludeEvents(Boolean includeEvents) {
		this.includeEvents = includeEvents;
	}

	public void setIncludeDevice(Boolean includeDevice) {
		this.includeDevice = includeDevice;
	}

	public void setIncludeTechnician(Boolean includeTechnician) {
		this.includeTechnician = includeTechnician;
	}

	public void setIncludeUtility(Boolean includeUtility) {
		this.includeUtility = includeUtility;
	}

	public void setIncludeManagement(Boolean includeManagement) {
		this.includeManagement = includeManagement;
	}

	public void setIncludeAlerts(Boolean includeAlerts) {
		this.includeAlerts = includeAlerts;
	}

	public void setIncludeWeather(Boolean includeWeather) {
		this.includeWeather = includeWeather;
	}

	public void setIncludeHouseDetails(Boolean includeHouseDetails) {
		this.includeHouseDetails = includeHouseDetails;
	}

	public void setIncludeOemCfg(Boolean includeOemCfg) {
		this.includeOemCfg = includeOemCfg;
	}

	public void setIncludeEquipmentStatus(Boolean includeEquipmentStatus) {
		this.includeEquipmentStatus = includeEquipmentStatus;
	}

	public void setIncludeNotificationSettings(Boolean includeNotificationSettings) {
		this.includeNotificationSettings = includeNotificationSettings;
	}

	public void setIncludePrivacy(Boolean includePrivacy) {
		this.includePrivacy = includePrivacy;
	}

	public void setIncludeVersion(Boolean includeVersion) {
		this.includeVersion = includeVersion;
	}

	public void setIncludeSecuritySettings(Boolean includeSecuritySettings) {
		this.includeSecuritySettings = includeSecuritySettings;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}