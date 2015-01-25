package net.watsonplace.ecobee.api;

public class EquipmentSetting {
	private String filterLastChanged; // The date the filter was last changed for this equipment. String format: YYYY-MM-DD
	private Integer filterLife; // The value representing the life of the filter. This value is expressed in month or hour, which is specified in the the filterLifeUnits property.
	private String filterLifeUnits; // The units the filterLife field is measured in. Possible values are: month, hour. month has a range of 1 - 12. hour has a range of 100 - 10000.
	private String remindMeDate; // The date the reminder will be triggered. This is a read-only field and cannot be modified through the API. The value is calculated and set by the thermostat.
	private Boolean enabled; // Value representing whether or not alerts/reminders are enabled for this notification type or not.
	private String type; // The type of notification. Possible values are: hvac, furnaceFilter, humidifierFilter, dehumidifierFilter, ventilator, ac, airFilter, airCleaner, uvLamp
	private Boolean remindTechnician; // Value representing whether or not alerts/reminders should be sent to the technician/contractor assoicated with the thermostat.
}
