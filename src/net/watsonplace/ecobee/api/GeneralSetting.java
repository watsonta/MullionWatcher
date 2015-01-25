package net.watsonplace.ecobee.api;

public class GeneralSetting {
	private Boolean enabled; // Boolean value representing whether or not alerts/reminders are enabled for this notification type or not.
	private String type; // The type of notification. Possible values are: temp
	private Boolean remindTechnician; // Boolean value representing whether or not alerts/reminders should be sent to the technician/contractor assoicated with the thermostat.
}
