package net.watsonplace.ecobee.api;

enum selType {registered, thermostats, managementSet };

public class Selection {
	SelectionAttributes selection = new SelectionAttributes();
	
	public Selection() {
		selection.selectionType = selType.registered;
		selection.selectionMatch = "";
		selection.includeRuntime = true;
	}
	
	public class SelectionAttributes {
		selType selectionType; // The type of match data supplied: Values: none, thermostats, user, managementSet.
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
	}
	
}