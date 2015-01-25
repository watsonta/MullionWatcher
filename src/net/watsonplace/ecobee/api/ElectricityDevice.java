package net.watsonplace.ecobee.api;

public class ElectricityDevice {
	private String name; // The name of the device
	private ElectricityTier[] tiers; // The list of Electricity Tiers containing the break down of daily electricity consumption of the device for the day, broken down per pricing tier.
	private String lastUpdate; // The last date/time the reading was updated in UTC time.
	private String[] cost; // The last three daily electricity cost reads from the device in cents with a three decimal place precision.
	private String[] consumption; // The last three daily electricity consumption reads from the device in KWh with a three decimal place precision.
}
