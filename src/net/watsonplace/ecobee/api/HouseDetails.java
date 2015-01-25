package net.watsonplace.ecobee.api;

public class HouseDetails {
	private String style; // The style of house. Values: other, apartment, condominium, detached, loft, multiPlex, rowHouse, semiDetached, townhouse, and 0 for unknown.
	private Integer size; // The size of the house in square feet.
	private Integer numberOfFloors; // The number of floors or levels in the house.
	private Integer numberOfRooms; // The number of rooms in the house.
	private Integer numberOfOccupants; // The number of occupants living in the house.
	private Integer age; // The age of house in years.
	private Integer windowEfficiency; // This field defines the window efficiency of the house. Valid values are in the range 1 - 7. Changing the value of this field alters the settings the thermostat uses for the humidifier when in 'frost Control' mode.
}
