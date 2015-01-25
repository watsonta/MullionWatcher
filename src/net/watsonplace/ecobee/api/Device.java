package net.watsonplace.ecobee.api;

public class Device {
	DeviceAttributes device = new DeviceAttributes();
	
	public class DeviceAttributes {
		Integer deviceId; // A unique ID for the device
		String name; // The user supplied device name
		Sensor[] sensors; // The list of Sensor Objects associated with the device.
		Output[] outputs; // The list of Output Objects associated with the device 
	}
}
