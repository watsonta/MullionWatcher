package net.watsonplace.ecobee.api;

public class Action {
	private String type; // Values: activateRelay, adjustTemp, doNothing, shutdownAC, shutdownAuxHeat, shutdownSystem, shutdownCompression, switchToOccupied, switchToUnoccupied, turnOffDehumidifer, turnOffHumidifier, turnOnCool, turnOnDehumidifier, turnOnFan, turnOnHeat, turnOnHumidifier.
	private Boolean sendAlert; // Flag to enable an alert to be generated when the state is triggered
	private Boolean sendUpdate; // Whether to send an update message.
	private Integer activationDelay; // Delay in seconds before the action is triggered by the state change.
	private Integer deactivationDelay; // The amount of time to wait before deactivating this state after the state has been cleared.
	private Integer minActionDuration; // The minimum length of time to maintain action after sensor has been deactivated.
	private Integer heatAdjustTemp; // The amount to increase/decrease current setpoint if the type = adjustTemp.
	private Integer coolAdjustTemp; // The amount to increase/decrease current setpoint if the type = adjustTemp.
	private String activateRelay; // The user defined relay to be activated, only used for type == activateRelay.
	private Boolean activateRelayOpen; // Select if relay is to be open or closed when activated, only used for type == activateRelay.
}
