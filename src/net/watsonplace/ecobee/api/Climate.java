package net.watsonplace.ecobee.api;

public class Climate {
	private String name; //  	no 	yes 	The unique climate name. The name may be changed without affecting the program integrity so long as uniqueness is maintained.
	private String climateRef; //  	yes 	no 	The unique climate identifier. Changing the identifier is not possible and it is generated on the server for each climate. If this value is not supplied a new climate will be created. For the default climates and existing user created climates the climateRef should be supplied - see note above.
	private Boolean isOccupied; //  	no 	no 	A flag indicating whether the property is occupied by persons during this climate
	private Boolean isOptimized; //  	no 	no 	A flag indicating whether ecobee optimized climate settings are used by this climate.
	private String coolFan; //  	no 	no 	The cooling fan mode. Default: on. Values: auto, on.
	private String heatFan; //  	no 	no 	The heating fan mode. Default: on. Values: auto, on.
	private String vent; //  	no 	no 	The ventilator mode. Default: off. Values: auto, minontime, on, off.
	private Integer ventilatorMinOnTime; //  	no 	no 	The minimum time, in minutes, to run the ventilator each hour.
	private String owner; //  	no 	no 	The climate owner. Default: system. Values: adHoc, demandResponse, quickSave, sensorAction, switchOccupancy, system, template, user.
	private String type; //  	no 	no 	The type of climate. Default: program. Values: calendarEvent, program.
	private Integer colour; //  	no 	no 	The integer conversion of the HEX color value used to display this climate on the thermostat and on the web portal.
	private Integer coolTemp; //  	no 	no 	The cool temperature for this climate.
	private Integer heatTemp; //  	no 	no 	The heat temperature for this climate.
}
