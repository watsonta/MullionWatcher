package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ExtendedRuntime extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "extendedruntime" };
	public static Type APIObjectType = new TypeToken<ExtendedRuntime>(){}.getType();
	
	String lastReadingTimestamp; // The UTC timestamp of the last value read. This timestamp is updated at a 15 min interval by the thermostat. For the 1st value, it is timestamp - 10 mins, for the 2nd value it is timestamp - 5 mins. Consider day boundaries being straddled when using these values.
	String runtimeDate; // The UTC date of the last runtime reading. Format: YYYY-MM-DD
	Integer runtimeInterval; // The last 5 minute interval which was updated by the thermostat telemetry update. Subtract 2 from this interval to obtain the beginning interval for the last 3 readings. Multiply by 5 mins to obtain the minutes of the day. Range: 0-287
	Integer[] actualTemperature; // The last three 5 minute actual temperature readings
	Integer[] actualHumidity; // The last three 5 minute actual humidity readings.
	Integer[] desiredHeat; // The last three 5 minute desired heat temperature readings.
	Integer[] desiredCool; // The last three 5 minute desired cool temperature readings.
	Integer[] desiredHumidity; // The last three 5 minute desired humidity readings.
	Integer[] desiredDehumidity; // The last three 5 minute desired de-humidification readings.
	Integer[] dmOffset; // The last three 5 minute desired Demand Management temeprature offsets. This value is Demand Management adjustment value which was applied by the thermostat. If the thermostat decided not to honour the adjustment, it will send 0 for the interval. Compare these values with the values sent in the DM message to determine whether the thermostat applied the adjustment.
	String[] hvacMode; // The last three 5 minute HVAC Mode reading. These values indicate which stage was energized in the 5 minute interval. Values: heatStage10n, heatStage20n, heatStage30n, heatOff, compressorCoolStage10n, compressorCoolStage20n, compressorCoolOff, compressorHeatStage10n, compressorHeatStage20n, compressorHeatOff, economyCycle.
	Integer[] heatPump1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat pump stage 1 runtime.
	Integer[] heatPump2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat pump stage 2 runtime.
	Integer[] auxHeat1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the auxiliary heat stage 1. If the thermostat does not have a heat pump, this is heat stage 1.
	Integer[] auxHeat2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the auxiliary heat stage 2. If the thermostat does not have a heat pump, this is heat stage 2.
	Integer[] auxHeat3; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat stage 3 if the thermostat does not have a heat pump. Auxiliary stage 3 is not supported.
	Integer[] cool1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the cooling stage 1.
	Integer[] cool2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the cooling stage 2.
	Integer[] fan; // The last three 5 minute fan Runtime values in seconds (0-300 seconds) per interval.
	Integer[] humidifier; // The last three 5 minute humidifier Runtime values in seconds (0-300 seconds) per interval.
	Integer[] dehumidifier; // The last three 5 minute de-humidifier Runtime values in seconds (0-300 seconds) per interval.
	Integer[] economizer; // The last three 5 minute economizer Runtime values in seconds (0-300 seconds) per interval.
	Integer[] ventilator; // The last three 5 minute ventilator Runtime values in seconds (0-300 seconds) per interval.
	Integer currentElectricityBill; // The latest value of the current electricity bill as interpolated from the thermostat's readings from a paired electricity meter.
	Integer projectedElectricityBill; // The latest estimate of the projected electricity bill as interpolated from the thermostat's readings from a paired electricity meter.

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
