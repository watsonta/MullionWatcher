package net.watsonplace.ecobee.api;

import java.util.HashMap;

public class WeatherForecast {
	HashMap<Integer, String> symbolMappings = new HashMap<Integer, String>();
	
	public WeatherForecast() {
		symbolMappings.put(-2, "no_symbol");
		symbolMappings.put(0, "sunny");
		symbolMappings.put(1, "few_clouds");
		symbolMappings.put(2, "partly_cloudy");
		symbolMappings.put(3, "mostly_cloudy");
		symbolMappings.put(4, "overcast");
		symbolMappings.put(5, "drizzle");
		symbolMappings.put(6, "rain");
		symbolMappings.put(7, "freezing_rain");
		symbolMappings.put(8, "showers");
		symbolMappings.put(9, "hail");
		symbolMappings.put(10, "snow");
		symbolMappings.put(11, "flurries");
		symbolMappings.put(12, "freezing_snow");
		symbolMappings.put(13, "blizzard");
		symbolMappings.put(14, "pellets");
		symbolMappings.put(15, "thunderstorm");
		symbolMappings.put(16, "windy");
		symbolMappings.put(17, "tornado");
		symbolMappings.put(18, "fog");
		symbolMappings.put(19, "haze");
		symbolMappings.put(20, "smoke");
		symbolMappings.put(21, "dust");
	}

	Integer weatherSymbol; // The Integer value used to map to a weatherSymbol. See list of mappings above.
	String dateTime; // The time stamp of the weather forecast.
	String condition; // A text value reprsenting the current weather condition.
	Integer temperature; // The current temperature.
	Integer pressure; // The current barometric pressure.
	Integer relativeHumidity; // The current humidity.
	Integer dewpoint; // The dewpoint.
	Integer visibility; // The visibility.
	Integer windSpeed; // The wind speed as an integer in mph * 1000.
	Integer windGust; // The wind gust speed.
	String windDirection; // The wind direction.
	Integer windBearing; // The wind bearing.
	Integer pop; // Probability of precipitation.
	Integer tempHigh; // The predicted high temperature for the day.
	Integer tempLow; // The predicted low temperature for the day.
	Integer sky; // The cloud cover condition.
}
