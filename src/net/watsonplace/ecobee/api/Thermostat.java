package net.watsonplace.ecobee.api;

public class Thermostat {
	private String identifier; // The unique thermostat serial number.
	private String name; // A user defined name for a thermostat.
	private String thermostatRev; // The current thermostat configuration revision.
	private boolean isRegistered; // Whether the user registered the thermostat.
	private String modelNumber; // The thermostat model number. Values: idtSmart, idtEms, siSmart, siEms
	private String lastModified; // The last modified date time for the thermostat configuration.
	private String thermostatTime; // The current time in the thermostat's time zone
	private String utcTime; // The current time in UTC.
	private Alert[] alerts; // The list of Alert objects tied to the thermostat
	private Settings settings; // The thermostat Setting object linked to the thermostat
	private Runtime runtime; // The Runtime state object for the thermostat
	private ExtendedRuntime extendedRuntime; // The ExtendedRuntime object for the thermostat
	private Electricity electricity; // The Electricity object for the thermostat
	private Device[] devices; // The list of Device objects linked to the thermostat
	private Location location; // The Location object for the thermostat
	private Technician technician; // The Technician object associated with the thermostat containing the technician contact information
	private Utility utility; // The Utility object associated with the thermostat containing the utility company information
	private Management management; // The Management object associated with the thermostat containing the management company information
	private Weather weather; // The Weather object linked to the thermostat representing the current weather on the thermostat.
	private Event[] events; // The list of Event objects linked to the thermostat representing any events that are active or scheduled.
	private Program program; // The Program object for the thermostat
	private HouseDetails houseDetails; // The houseDetails object contains contains the information about the house the thermostat is installed in.
	private Object oemCfg; // The OemCfg object contains information about the OEM specific thermostat.
	private String equipmentStatus; // The status of all equipment controlled by this Thermostat. Only running equipment is listed in the CSV String. If no equipment is currently running an empty String is returned. Values: heatPump, heatPump2, heatPump3, compCool1, compCool2, auxHeat1, auxHeat2, auxHeat3, fan, humidifier, dehumidifier, ventilator, economizer, compHotWater, auxHotWater.	
	private NotificationSettings notificationSettings; // The NotificationSettings object containing the configuration for Alert and Reminders for the Thermostat.
	private Object privacy; // The Privacy object containing the privacy settings for the Thermostat. Note: access to this object is restricted to callers with implict authentication.
	private Version version;
	private SecuritySettings securitySettings;
	
	public String getName() {
		return name;
	}
	
	public Runtime getRuntime() {
		return runtime;
	}
}
