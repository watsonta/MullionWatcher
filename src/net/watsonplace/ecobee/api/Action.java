package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Action extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "action" };
	public static Type APIObjectType = new TypeToken<Action>(){}.getType();
	
	enum ActionType { activateRelay, adjustTemp, doNothing, shutdownAC, shutdownAuxHeat, shutdownSystem, shutdownCompression,
		switchToOccupied, switchToUnoccupied, turnOffDehumidifer, turnOffHumidifier, turnOnCool, turnOnDehumidifier, turnOnFan, 
		turnOnHeat, turnOnHumidifier
	}
	
	ActionType type;
	Boolean sendAlert; // Flag to enable an alert to be generated when the state is triggered
	Boolean sendUpdate; // Whether to send an update message.
	Integer activationDelay; // Delay in seconds before the action is triggered by the state change.
	Integer deactivationDelay; // The amount of time to wait before deactivating this state after the state has been cleared.
	Integer minActionDuration; // The minimum length of time to maintain action after sensor has been deactivated.
	Integer heatAdjustTemp; // The amount to increase/decrease current setpoint if the type = adjustTemp.
	Integer coolAdjustTemp; // The amount to increase/decrease current setpoint if the type = adjustTemp.
	String activateRelay; // The user defined relay to be activated, only used for type == activateRelay.
	Boolean activateRelayOpen; // Select if relay is to be open or closed when activated, only used for type == activateRelay.
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}
	
	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
