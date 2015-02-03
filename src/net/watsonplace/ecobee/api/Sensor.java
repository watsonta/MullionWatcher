package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Sensor extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "sensor" };
	public static Type APIObjectType = new TypeToken<Sensor>(){}.getType();
	
	String name; // The sensor name
	String manufacturer; // The sensor manufacturer
	String model; // The sensor model
	Integer zone; // The thermostat zone the sensor is associated with
	Integer sensorId; // The unique sensor identifier
	String type; // The type of sensor. Values: adc, co2, dryCOntact, humidity, temperature, unknown.
	String usage; // The sensor usage type. Values: dischargeAir, indoor, monitor, outdoor.
	Integer numberOfBits; // The number of bits the adc has been configured to use.
	Integer bconstant; // Value of the bconstant value used in temperature sensors
	Integer thermistorSize; // The sensor thermistor value, ie. 10K thermistor=10000, 2.5K thermistor=2500
	Integer tempCorrection; // The user adjustable temperature compensation applied to the temperature reading.
	Integer gain; // The sensor thermistor gain value.
	Integer maxVoltage; // The sensor thermistor max voltage in Volts, 5=5V, 10=10V.
	Integer multiplier; // The multiplier value used in sensors (1000x value).
	State[] states; // A list of SensorState objects
	
	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
