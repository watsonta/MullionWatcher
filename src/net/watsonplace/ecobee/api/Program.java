package net.watsonplace.ecobee.api;

public class Program {
	private String[][] schedule; // The Schedule object defining the program schedule.
	private Climate[] climates; // The list of Climate objects defining all the climates in the program schedule.
	private String currentClimateRef; // The currently active climate, identified by its ClimateRef.
}
