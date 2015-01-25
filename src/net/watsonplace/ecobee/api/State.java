package net.watsonplace.ecobee.api;

public class State {
	Integer maxValue; // The maximum value the sensor can generate.
	Integer minValue; // The minimum value the sensor can generate.
	String type; // Values: coolHigh, coolLow, heatHigh, heatLow, high, low, transitionCount, normal.
	Action[] actions; // The list of StateAction objects associated with the sensor.
}
