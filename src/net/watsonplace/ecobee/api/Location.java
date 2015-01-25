package net.watsonplace.ecobee.api;

public class Location {
	private Integer timeZoneOffsetMinutes; // The timezone offset in minutes from UTC.
	private String timeZone; // The Olson timezone the thermostat resides in (e.g America/Toronto).
	private Boolean isDaylightSaving; // Whether the thermostat should factor in daylight savings when displaying the date and time.
	private String streetAddress; // The thermostat location street address.
	private String city; // The thermostat location city.
	private String provinceState; // The thermostat location State or Province.
	private String country; // The thermostat location country.
	private String postalCode; // The thermostat location ZIP or Postal code.
	private String phoneNumber; // The thermostat owner's phone number
	private String mapCoordinates; // The lat/long geographic coordinates of the thermostat location.
}
