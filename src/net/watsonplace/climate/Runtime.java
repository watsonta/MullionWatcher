package net.watsonplace.climate;

public class Runtime {
	private String runtimeDate; // The UTC date of the last runtime reading. Format: YYYY-MM-DD
	private Float actualTemperature; // The current temperature displayed on the thermostat.
	private Integer actualHumidity; // The current humidity % shown on the thermostat.
	private Float desiredHeat; // The desired heat temperature as per the current running program or active event.
	private Float desiredCool; // The desired cool temperature as per the current running program or active event.
	private Integer desiredHumidity; // The desired humidity set point.
	
	public String getRuntimeDate() {
		return runtimeDate;
	}

	public void setRuntimeDate(String runtimeDate) {
		this.runtimeDate = runtimeDate;
	}

	public Float getActualTemperature() {
		return actualTemperature;
	}

	public void setActualTemperature(Float actualTemperature) {
		this.actualTemperature = actualTemperature;
	}

	public Integer getActualHumidity() {
		return actualHumidity;
	}

	public void setActualHumidity(Integer actualHumidity) {
		this.actualHumidity = actualHumidity;
	}

	public Float getDesiredHeat() {
		return desiredHeat;
	}

	public void setDesiredHeat(Float desiredHeat) {
		this.desiredHeat = desiredHeat;
	}

	public Float getDesiredCool() {
		return desiredCool;
	}

	public void setDesiredCool(Float desiredCool) {
		this.desiredCool = desiredCool;
	}

	public Integer getDesiredHumidity() {
		return desiredHumidity;
	}

	public void setDesiredHumidity(Integer desiredHumidity) {
		this.desiredHumidity = desiredHumidity;
	}

}
