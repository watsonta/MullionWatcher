package net.watsonplace.ecobee.api;

public class Runtime {
	private String runtimeRev; // The current runtime revision. Equivalent in meaning to the runtime revision number in the thermostat summary call.
	private Boolean connected; // Whether the thermostat is currently connected to the server.
	private String firstConnected; // The UTC date/time stamp of when the thermostat first connected to the ecobee server.	
	private String connectDateTime; // The last recorded connection date and time.
	private String disconnectDateTime; // The last recorded disconnection date and time.
	private String lastModified; // The UTC date/time stamp of when the thermostat was updated. Format: YYYY-MM-DD HH:MM:SS
	private String lastStatusModified; // The UTC date/time stamp of when the thermostat last posted its runtime information. Format: YYYY-MM-DD HH:MM:SS
	private String runtimeDate; // The UTC date of the last runtime reading. Format: YYYY-MM-DD
	private Integer runtimeInterval; // The last 5 minute interval which was updated by the thermostat telemetry update. Subtract 2 from this interval to obtain the beginning interval for the last 3 readings. Multiply by 5 mins to obtain the minutes of the day. Range: 0-287
	private Integer actualTemperature; // The current temperature displayed on the thermostat.
	private Integer actualHumidity; // The current humidity % shown on the thermostat.
	private Integer desiredHeat; // The desired heat temperature as per the current running program or active event.
	private Integer desiredCool; // The desired cool temperature as per the current running program or active event.
	private Integer desiredHumidity; // The desired humidity set point.
	private Integer desiredDehumidity; // The desired dehumidification set point.
	private String desiredFanMode; // The desired fan mode. Values: auto, on or null if the HVAC system is off and the thermostat is not controlling a fan independently. 
	
	public boolean isConnected() {
		return connected;
	}
	
	public float getTemperature() {
		return actualTemperature/10.0f;
	}
	
	public int getHumidity() {
		return actualHumidity;
	}
}
