package net.watsonplace.ecobee.api;

public class Event {
	private String type; // The type of event. Values: hold, demandResponse, sensor, switchOccupancy, vacation, quickSave, today
	private String name; // The unique event name.
	private Boolean running; // Whether the event is currently active or not.
	private String startDate; // The event start date in thermostat local time.
	private String startTime; // The event start time in thermostat local time.
	private String endDate; // The event end date in thermostat local time.
	private String endTime; // The event end time in thermostat local time.
	private Boolean isOccupied; // Whether there are persons occupying the property during the event.
	private Boolean isCoolOff; // Whether cooling will be turned off during the event.
	private Boolean isHeatOff; // Whether heating will be turned off during the event.
	private Integer coolHoldTemp; // The cooling absolute temperature to set.
	private Integer heatHoldTemp; // The heating absolute temperature to set.
	private String fan; // The fan mode during the event. Values: auto, on Default: based on current climate and hvac mode.
	private String vent; // The ventilator mode during the vent. Values: auto, minontime, on, off.
	private Integer ventilatorMinOnTime; // The minimum amount of time the ventilator equipment must stay on on each duty cycle.
	private Boolean isOptional; // Whether this event is mandatory or the end user can cancel it.
	private Boolean isTemperatureRelative; // Whether the event is using a relative temperature setting to the currently active program climate.
	private Integer coolRelativeTemp; // The relative cool temperature adjustment.
	private Integer heatRelativeTemp; // The relative heat temperature adjustment.
	private Boolean isTemperatureAbsolute; // Whether the event uses absolute temperatures to set the values. Default: true for DRs.
	private Integer dutyCyclePercentage; // Indicates the % scheduled runtime during a Demand Response event. Valid range is 0 - 100%. Default = 100, indicates no change to schedule.
	private Integer fanMinOnTime; // The minimum number of minutes to run the fan each hour. Range: 0-60, Default: 0
	private Boolean occupiedSensorActive; // True if this calendar event was created because of the occupied sensor.
	private Boolean unoccupiedSensorActive; // True if this calendar event was created because of the unoccupied sensor
	private Integer drRampUpTemp; // Unsupported. Future feature.
	private Integer drRampUpTime; // Unsupported. Future feature.
	private String linkRef; // Unique identifier set by the server to link one or more events and alerts together.
	private String holdClimateRef; // Used for display purposes to indicate what climate (if any) is being used for the hold.
}
