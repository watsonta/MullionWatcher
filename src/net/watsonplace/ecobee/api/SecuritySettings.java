package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class SecuritySettings extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "securitysettings" };
	public static Type APIObjectType = new TypeToken<SecuritySettings>(){}.getType();
	
	String userAccessCode; // The 4-digit user access code for the thermostat. The code must be set when enabling access control. See the callout above for more information.
	Boolean allUserAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control. Default value is false. If all other values are true this value will default to true.
	Boolean programAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat.Program. Default value is false, unless allUserAccess is true.
	Boolean detailsAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat system and settings. Default value is false, unless allUserAccess is true.
	Boolean quickSaveAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat quick save functionality. Default value is false, unless allUserAccess is true.
	Boolean vacationAccess; // The flag for determing whether there are any restrictions on the thermostat regarding access control to the Thermostat vacation functionality. Default value is false, unless allUserAccess is true.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
