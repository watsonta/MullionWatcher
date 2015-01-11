package net.watsonplace.ecobee;

public class Dewpoint {
	
	public static int calculate(int temperatureF, int relativeHumidityPct) {
		float t = (temperatureF - 32) * -5f / 9;
	    float rh = relativeHumidityPct / 100f;
	    double eln = Math.log(rh * Math.exp(-17.67 * t / (243.5 - t)));
	    return (int)Math.round(32 + (-243.5 * eln / (eln - 17.67)) * 9 / 5);
	}
	
}
