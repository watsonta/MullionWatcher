package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Event extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "event" };
	public static Type APIObjectType = new TypeToken<Event>(){}.getType();
	
	String type; // The type of event. Values: hold, demandResponse, sensor, switchOccupancy, vacation, quickSave, today
	String name; // The unique event name.
	Boolean running; // Whether the event is currently active or not.
	String startDate; // The event start date in thermostat local time.
	String startTime; // The event start time in thermostat local time.
	String endDate; // The event end date in thermostat local time.
	String endTime; // The event end time in thermostat local time.
	Boolean isOccupied; // Whether there are persons occupying the property during the event.
	Boolean isCoolOff; // Whether cooling will be turned off during the event.
	Boolean isHeatOff; // Whether heating will be turned off during the event.
	Integer coolHoldTemp; // The cooling absolute temperature to set.
	Integer heatHoldTemp; // The heating absolute temperature to set.
	String fan; // The fan mode during the event. Values: auto, on Default: based on current climate and hvac mode.
	String vent; // The ventilator mode during the vent. Values: auto, minontime, on, off.
	Integer ventilatorMinOnTime; // The minimum amount of time the ventilator equipment must stay on on each duty cycle.
	Boolean isOptional; // Whether this event is mandatory or the end user can cancel it.
	Boolean isTemperatureRelative; // Whether the event is using a relative temperature setting to the currently active program climate.
	Integer coolRelativeTemp; // The relative cool temperature adjustment.
	Integer heatRelativeTemp; // The relative heat temperature adjustment.
	Boolean isTemperatureAbsolute; // Whether the event uses absolute temperatures to set the values. Default: true for DRs.
	Integer dutyCyclePercentage; // Indicates the % scheduled runtime during a Demand Response event. Valid range is 0 - 100%. Default = 100, indicates no change to schedule.
	Integer fanMinOnTime; // The minimum number of minutes to run the fan each hour. Range: 0-60, Default: 0
	Boolean occupiedSensorActive; // True if this calendar event was created because of the occupied sensor.
	Boolean unoccupiedSensorActive; // True if this calendar event was created because of the unoccupied sensor
	Integer drRampUpTemp; // Unsupported. Future feature.
	Integer drRampUpTime; // Unsupported. Future feature.
	String linkRef; // Unique identifier set by the server to link one or more events and alerts together.
	String holdClimateRef; // Used for display purposes to indicate what climate (if any) is being used for the hold.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
