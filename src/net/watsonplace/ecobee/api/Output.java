package net.watsonplace.ecobee.api;

public class Output {
	private String name; // The name of the outpute
	private Integer zone; // The thermostat zone the output is associated with
	private Integer outputId; // The unique output identifier number.
	private String type; // The type of output. Values: compressor1, compressor2, dehumidifier, economizer, fan, heat1, heat2, heat3, heatPumpReversal, humidifer, none, occupancy, userDefined, ventilator, zoneCool, zoneFan, zoneHeat.
	private Boolean sendUpdate; // Whether to send an update message.
	private Boolean activeClosed; // If true, when this output is activated it will close the relay. Otherwise, activating the relay will open the relay.
	private Integer activationTime; // Time to activate relay - in seconds.
	private Integer deactivationTime; // Time to deactivate relay - in seconds.
}
