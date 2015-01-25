package net.watsonplace.ecobee.api;

public class SecuritySettings {
	private String userAccessCode; // The 4-digit user access code for the thermostat. The code must be set when enabling access control. See the callout above for more information.
	private Boolean allUserAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control. Default value is false. If all other values are true this value will default to true.
	private Boolean programAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat.Program. Default value is false, unless allUserAccess is true.
	private Boolean detailsAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat system and settings. Default value is false, unless allUserAccess is true.
	private Boolean quickSaveAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat quick save functionality. Default value is false, unless allUserAccess is true.
	private Boolean vacationAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat vacation functionality. Default value is false, unless allUserAccess is true.
}
