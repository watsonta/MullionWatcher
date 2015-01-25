package net.watsonplace.ecobee.api;

public class Status {
	private int code; // The status code for this status.
	private String message; // The detailed message for this status.
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
