package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Settings extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "settings" };
	public static Type APIObjectType = new TypeToken<Settings>(){}.getType();
	
	String hvacMode; //The current HVAC mode the thermostat is in. Values: auto, auxHeatOnly, cool, heat, off.
	String lastServiceDate; //The last service date of the HVAC equipment.	
	Boolean serviceRemindMe; //Whether to send an alert when service is required again.
	Integer monthsBetweenService; // The user configured monthly interval between HVAC service reminders
	String remindMeDate; // Date to be reminded about the next HVAC service date.
	String vent; // The ventilator mode. Values: auto, minontime, on, off.
	Integer ventilatorMinOnTime; // The minimum time in minutes the ventilator is configured to run. The thermostat will always guarantee that the ventilator runs for this minimum duration whenever engaged.
	Boolean serviceRemindTechnician; // Whether the technician associated with this thermostat should receive the HVAC service reminders as well.
	String eiLocation; // A note about the physical location where the SMART or EMS Equipment Interface module is located.
	Integer coldTempAlert; // The temperature at which a cold temp alert is triggered.
	Boolean coldTempAlertEnabled; // Whether cold temperature alerts are enabled.
	Integer hotTempAlert; // The temperature at which a hot temp alert is triggered.
	Boolean hotTempAlertEnabled; // Whether hot temperature alerts are enabled.
	Integer coolStages; // The number of cool stages the connected HVAC equipment supports.
	Integer heatStages; // The number of heat stages the connected HVAC equipment supports.
	Integer maxSetBack; // The maximum automated set point set back offset allowed in degrees.
	Integer maxSetForward; // The maximum automated set point set forward offset allowed in degrees.
	Integer quickSaveSetBack; // The set point set back offset, in degrees, configured for a quick save event.
	Integer quickSaveSetForward; // The set point set forward offset, in degrees, configured for a quick save event.
	Boolean hasHeatPump; // Whether the thermostat is controlling a heat pump.
	Boolean hasForcedAir; // Whether the thermostat is controlling a forced air furnace.
	Boolean hasBoiler; // Whether the thermostat is controlling a boiler.
	Boolean hasHumidifier; // Whether the thermostat is controlling a humidifier.
	Boolean hasErv; // Whether the thermostat is controlling an energy recovery ventilator.
	Boolean hasHrv; // Whether the thermostat is controlling a heat recovery ventilator.
	Boolean condensationAvoid; // Whether the thermostat is in frost control mode.
	Boolean useCelsius; // Whether the thermostat is configured to report in degrees Celcius.
	Boolean useTimeFormat12; // Whether the thermostat is using 12hr time format.
	String locale; // Multilanguage support, currently only "en" - english is supported. In future others locales can be supported.
	String humidity; // The minimum humidity level (in percent) set point for the humidifier
	String humidifierMode; // The humidifier mode. Values: auto, manual, off.
	Integer backlightOnIntensity; // The thermostat backlight intensity when on. A value between 0 and 10, with 0 meaning 'off' - the zero value may not be honored by all ecobee versions.
	Integer backlightSleepIntensity; // The thermostat backlight intensity when asleep. A value between 0 and 10, with 0 meaning 'off' - the zero value may not be honored by all ecobee versions.
	Integer backlightOffTime; // The time in seconds before the thermostat screen goes into sleep mode.
	Integer soundTickVolume; // The volume level for key presses on the thermostat. A value between 0 and 10, with 0 meaning 'off' - the zero value may not be honored by all ecobee versions.
	Integer soundAlertVolume; // The volume level for alerts on the thermostat. A value between 0 and 10, with 0 meaning 'off' - the zero value may not be honored by all ecobee versions.
	Integer compressorProtectionMinTime; // The minimum time the compressor must be off for in order to prevent short-cycling.
	Integer compressorProtectionMinTemp; // The minimum outdoor temperature that the compressor can operate at - applies more to air source heat pumps than geothermal.
	Integer stage1HeatingDifferentialTemp; // The difference between current temperature and set-point that will trigger stage 2 heating.
	Integer stage1CoolingDifferentialTemp; // The difference between current temperature and set-point that will trigger stage 2 cooling.
	Integer stage1HeatingDissipationTime; // The time after a heating cycle that the fan will run for to extract any heating left in the system - 30 second default.
	Integer stage1CoolingDissipationTime; // The time after a cooling cycle that the fan will run for to extract any cooling left in the system - 30 second default (for not)
	Boolean heatPumpReversalOnCool; // The flag to tell if the heat pump is in heating mode or in cooling when the relay is engaged. If set to zero it's heating when the reversing valve is open, cooling when closed and if it's one - it's the opposite.
	Boolean fanControlRequired; // Whether fan control by the Thermostat is required in auxiliary heating (gas/electric/boiler), otherwise controlled by furnace.
	Integer fanMinOnTime; // The minimum time, in minutes, to run the fan each hour. Value from 1 to 60.
	Integer heatCoolMinDelta; // The minimum temperature difference between the heat and cool values. Used to ensure that when thermostat is in auto mode, the heat and cool values are separated by at least this value.
	Integer tempCorrection; // The amount to adjust the temperature reading in degrees F - this value is subtracted from the temperature read from the sensor.
	String holdAction; // The default end time setting the thermostat applies to user temperature holds. Values useEndTime4hour, useEndTime2hour (EMS Only), nextPeriod, indefinite, askMe
	Boolean heatPumpGroundWater; // Whether the Thermostat uses a geothermal / ground source heat pump.
	Boolean hasElectric; // Whether the thermostat is connected to an electric HVAC system.
	Boolean hasDehumidifier; // Whether the thermostat is connected to a dehumidifier
	String dehumidifierMode; // The dehumidifier mode. Values: on, off.
	Integer dehumidifierLevel; // The dehumidification set point in percentage.
	Boolean dehumidifyWithAC; // Whether the thermostat should use AC overcool to dehumidify. When set to true a postive integer value must be supplied for dehumidifyOvercoolOffset otherwise an API validation exception will be thrown.
	Integer dehumidifyOvercoolOffset; // Whether the thermostat should use AC overcool to dehumidify and what that temperature offset should be. A value of 0 means this feature is disabled and dehumidifyWithAC will be set to false. Value represents the value in F to subract from the current set point. Values should be in the range 0 - 50 and be divisible by 5.
	Boolean autoHeatCoolFeatureEnabled; // If enabled, allows the Thermostat to be put in HVACAuto mode.
	Boolean wifiOfflineAlert; // Whether the alert for when wifi is offline is enabled.
	Integer heatMinTemp; // The minimum heat set point allowed by the thermostat firmware.
	Integer heatMaxTemp; // The maximum heat set point allowed by the thermostat firmware.
	Integer coolMinTemp; // The minimum cool set point allowed by the thermostat firmware.
	Integer coolMaxTemp; // The maximum cool set point allowed by the thermostat firmware.
	Integer heatRangeHigh; // The maximum heat set point configured by the user's preferences.
	Integer heatRangeLow; // The minimum heat set point configured by the user's preferences.
	Integer coolRangeHigh; // The maximum cool set point configured by the user's preferences.
	Integer coolRangeLow; // The minimum heat set point configured by the user's preferences.
	String userAccessCode; // The user access code value for this thermostat. See the SecuritySettings object for more information.
	Integer userAccessSetting; // The integer representation of the user access settings. See the SecuritySettings object for more information.
	Integer auxRuntimeAlert; // The temperature at which an auxHeat temperature alert is triggered.
	Integer auxOutdoorTempAlert; // The temperature at which an auxOutdoor temperature alert is triggered.
	Integer auxMaxOutdoorTemp; // The maximum outdoor temperature above which aux heat will not run.
	Boolean auxRuntimeAlertNotify; // Whether the auxHeat temperature alerts are enabled.
	Boolean auxOutdoorTempAlertNotify; // Whether the auxOutdoor temperature alerts are enabled.
	Boolean auxRuntimeAlertNotifyTechnician; // Whether the auxHeat temperature alerts for the technician are enabled.
	Boolean auxOutdoorTempAlertNotifyTechnician; // Whether the auxOutdoor temperature alerts for the technician are enabled.
	Boolean disablePreHeating; // Whether the thermostat should use pre heating to reach the set point on time.
	Boolean disablePreCooling; // Whether the thermostat should use pre cooling to reach the set point on time.
	Boolean installerCodeRequired; // Whether an installer code is required.
	String drAccept; // Whether Demand Response requests are accepted by this thermostat. Possible values are: always, askMe, customerSelect, defaultAccept, defaultDecline, never.
	Boolean isRentalProperty; // Whether the property is a rental, or not.
	Boolean useZoneController; // Whether to use a zone controller or not.
	Integer randomStartDelayCool; // Whether random start delay is enabled for cooling.
	Integer randomStartDelayHeat; // Whether random start delay is enabled for heating.
	Integer humidityHighAlert; // The humidity level to trigger a high humidity alert.
	Integer humidityLowAlert; // The humidity level to trigger a low humidity alert.
	Boolean disableHeatPumpAlerts; // Whether heat pump alerts are disabled.
	Boolean disableAlertsOnIdt; // Whether alerts are disabled from showing on the thermostat.
	Boolean humidityAlertNotify; // Whether humidification alerts are enabled to the thermsotat owner.
	Boolean humidityAlertNotifyTechnician; // Whether humidification alerts are enabled to the technician associated with the thermsotat.
	Boolean tempAlertNotify; // Whether temperature alerts are enabled to the thermsotat owner.
	Boolean tempAlertNotifyTechnician; // Whether temperature alerts are enabled to the technician associated with the thermostat.
	Integer monthlyElectricityBillLimit; // The dollar amount the owner specifies for their desired maximum electricy bill.
	Boolean enableElectricityBillAlert; // Whether electricity bill alerts are enabled.
	Boolean enableProjectedElectricityBillAlert; // Whether electricity bill projection alerts are enabled
	Integer electricityBillingDayOfMonth; // The day of the month the owner's electricty usage is billed.
	Integer electricityBillCycleMonths; // The owners billing cycle duration in months.
	Integer electricityBillStartMonth; // The annual start month of the owners billing cycle.
	Integer ventilatorMinOnTimeHome; // The number of minutes to run ventilator per hour when home.
	Integer ventilatorMinOnTimeAway; // The number of minutes to run ventilator per hour when away.
	Boolean backlightOffDuringSleep; // Determines whether or not to turn the backlight off during sleep.
	Boolean autoAway; // When set to true if no occupancy motion detected thermostat will go into indefinite away hold, until either the user presses resume schedule or motion is detected.
	Boolean smartCirculation; // When set to true if a larger than normal delta is found between sensors the fan will be engaged for 15min/hour.
	Boolean followMeComfort; // When set to true if a sensor has detected presense for more than 10 minutes then include that sensor in temp average. If no activity has been seen on a sensor for more than 1 hour then remove this sensor from temperature average.
	String ventilatorType; // This read-only field represents the type of ventilator present for the Thermostat. The possible values are none, ventilator, hrv, and erv.
	Boolean isVentilatorTimerOn; // This Boolean field represents whether the ventilator timer is on or off. The default value is false. If set to true the ventilatorOffDateTime is set to now() + 20 minutes. If set to false the ventilatorOffDateTime is set to it's default value.
	String ventilatorOffDateTime; // This read-only field represents the Date and Time the ventilator will run until. The default value is 2014-01-01 00:00:00.
	Boolean hasUVFilter; // This Boolean field represents whether the HVAC system has a UV filter. The default value is true.
	Boolean coolingLockout; // This field represents whether to permit the cooling to operate when the Outdoor temeperature is under a specific threshold, currently 55F. The default value is false.
	Boolean ventilatorFreeCooling; // Whether to use the ventilator to dehumidify when climate or calendar event indicates the owner is home. The default value is false.
	Boolean dehumidifyWhenHeating; // This field represents whether to permit dehumidifer to operate when the heating is running. The default value is false.
	Boolean ventilatorDehumidify; // This field represents whether or not to allow dehumification when cooling. The default value is true.
	String groupRef; // The unique reference to the group this thermostat belongs to, if any. See GET Group request and POST Group request for more information.
	String groupName; // The name of the the group this thermostat belongs to, if any. See GET Group request and POST Group request for more information.
	Integer groupSetting; // The setting value for the group this thermostat belongs to, if any. See GET Group request and POST Group request for more information. 
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
