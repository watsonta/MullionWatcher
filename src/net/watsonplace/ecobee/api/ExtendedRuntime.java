package net.watsonplace.ecobee.api;

public class ExtendedRuntime {
	private String lastReadingTimestamp; // The UTC timestamp of the last value read. This timestamp is updated at a 15 min interval by the thermostat. For the 1st value, it is timestamp - 10 mins, for the 2nd value it is timestamp - 5 mins. Consider day boundaries being straddled when using these values.
	private String runtimeDate; // The UTC date of the last runtime reading. Format: YYYY-MM-DD
	private Integer runtimeInterval; // The last 5 minute interval which was updated by the thermostat telemetry update. Subtract 2 from this interval to obtain the beginning interval for the last 3 readings. Multiply by 5 mins to obtain the minutes of the day. Range: 0-287
	private Integer[] actualTemperature; // The last three 5 minute actual temperature readings
	private Integer[] actualHumidity; // The last three 5 minute actual humidity readings.
	private Integer[] desiredHeat; // The last three 5 minute desired heat temperature readings.
	private Integer[] desiredCool; // The last three 5 minute desired cool temperature readings.
	private Integer[] desiredHumidity; // The last three 5 minute desired humidity readings.
	private Integer[] desiredDehumidity; // The last three 5 minute desired de-humidification readings.
	private Integer[] dmOffset; // The last three 5 minute desired Demand Management temeprature offsets. This value is Demand Management adjustment value which was applied by the thermostat. If the thermostat decided not to honour the adjustment, it will send 0 for the interval. Compare these values with the values sent in the DM message to determine whether the thermostat applied the adjustment.
	private String[] hvacMode; // The last three 5 minute HVAC Mode reading. These values indicate which stage was energized in the 5 minute interval. Values: heatStage10n, heatStage20n, heatStage30n, heatOff, compressorCoolStage10n, compressorCoolStage20n, compressorCoolOff, compressorHeatStage10n, compressorHeatStage20n, compressorHeatOff, economyCycle.
	private Integer[] heatPump1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat pump stage 1 runtime.
	private Integer[] heatPump2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat pump stage 2 runtime.
	private Integer[] auxHeat1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the auxiliary heat stage 1. If the thermostat does not have a heat pump, this is heat stage 1.
	private Integer[] auxHeat2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the auxiliary heat stage 2. If the thermostat does not have a heat pump, this is heat stage 2.
	private Integer[] auxHeat3; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the heat stage 3 if the thermostat does not have a heat pump. Auxiliary stage 3 is not supported.
	private Integer[] cool1; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the cooling stage 1.
	private Integer[] cool2; // The last three 5 minute HVAC Runtime values in seconds (0-300 seconds) per interval. This value corresponds to the cooling stage 2.
	private Integer[] fan; // The last three 5 minute fan Runtime values in seconds (0-300 seconds) per interval.
	private Integer[] humidifier; // The last three 5 minute humidifier Runtime values in seconds (0-300 seconds) per interval.
	private Integer[] dehumidifier; // The last three 5 minute de-humidifier Runtime values in seconds (0-300 seconds) per interval.
	private Integer[] economizer; // The last three 5 minute economizer Runtime values in seconds (0-300 seconds) per interval.
	private Integer[] ventilator; // The last three 5 minute ventilator Runtime values in seconds (0-300 seconds) per interval.
	private Integer currentElectricityBill; // The latest value of the current electricity bill as interpolated from the thermostat's readings from a paired electricity meter.
	private Integer projectedElectricityBill; // The latest estimate of the projected electricity bill as interpolated from the thermostat's readings from a paired electricity meter.
}
