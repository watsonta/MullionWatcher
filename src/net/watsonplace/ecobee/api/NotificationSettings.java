package net.watsonplace.ecobee.api;

public class NotificationSettings {
	private String[] emailAddresses; // The list of email addresses alerts and reminders will be sent to. The full list of email addresses must be sent in any update request. If any are missing from that list they will be deleted. If an empty list is sent, any email addresses will be deleted.
	private Boolean emailNotificationsEnabled; // Boolean values representing whether or not alerts and reminders will be sent to the email addresses listed above when triggered.
	private EquipmentSetting[] equipment; // The list of equipment specific alert and reminder settings.
	private GeneralSetting[] general; // The list of general alert and reminder settings.
	private LimitSetting[] limit; // The list of limit specific alert and reminder settings. 
}
