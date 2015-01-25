package net.watsonplace.ecobee.api;

public class Weather {
	String timestamp; // The time stamp in UTC of the weather forecast.
	String weatherStation; // The weather station identifier.
	WeatherForecast[] forecasts; // The list of latest weather station forecasts.
}
