package net.watsonplace.ecobee.api;

public class Sensor {
	private String name; // The sensor name
	private String manufacturer; // The sensor manufacturer
	private String model; // The sensor model
	private Integer zone; // The thermostat zone the sensor is associated with
	private Integer sensorId; // The unique sensor identifier
	private String type; // The type of sensor. Values: adc, co2, dryCOntact, humidity, temperature, unknown.
	private String usage; // The sensor usage type. Values: dischargeAir, indoor, monitor, outdoor.
	private Integer numberOfBits; // The number of bits the adc has been configured to use.
	private Integer bconstant; // Value of the bconstant value used in temperature sensors
	private Integer thermistorSize; // The sensor thermistor value, ie. 10K thermistor=10000, 2.5K thermistor=2500
	private Integer tempCorrection; // The user adjustable temperature compensation applied to the temperature reading.
	private Integer gain; // The sensor thermistor gain value.
	private Integer maxVoltage; // The sensor thermistor max voltage in Volts, 5=5V, 10=10V.
	private Integer multiplier; // The multiplier value used in sensors (1000x value).
	private State[] states; // A list of SensorState objects
}
