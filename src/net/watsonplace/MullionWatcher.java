/**
 * This file is part of MullionWatcher.
 *
 * MullionWatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * MullionWatcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with MullionWatcher.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.watsonplace;

import java.io.File;
import java.io.FileInputStream;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import net.watsonplace.climate.ClimateControl;
import net.watsonplace.climate.Thermostat;
import net.watsonplace.climate.Weather;
import net.watsonplace.climate.ecobee.Control;
import net.watsonplace.climate.util.Dewpoint;
import net.watsonplace.mullion.MullionState;
import net.watsonplace.mullion.Receiver;
import net.watsonplace.mullion.Sender;
import net.watsonplace.mullion.TemperatureSample;
import net.watsonplace.twitter.Agent;

import org.apache.log4j.Logger;

public class MullionWatcher {
	private static final Logger logger = Logger.getLogger(MullionWatcher.class.getName());

	private static enum ActionState { normal, watch, alarm, action, recovery }
	
	private static final String MULLIONWATCHER_PROPERTIES_FILE = "conf/MullionWatcher.properties";
	private static final long ONE_MINUTE = 60*1000;
	private static final long ONE_HOUR = 60*ONE_MINUTE;
	private static final int WATCH_SPREAD = 5;
	private static final int ALARM_SPREAD = 3;
	private static final int ACTION_SPREAD = 2;
	private static final int RECOVERY_SPREAD = 4;
	private static final int HOLD_HOURS = 4;

	private static boolean SENDER_ONLY = false;
	private static int MINIMUM_TEMPERATURE = 65;
	private static boolean SHUTDOWN = false;
	
	private long now;
	private long lastReadingAlarm = System.currentTimeMillis();
	private long lastWatchNotification = 0;
	private long lastAlarmNotification = 0;
	private long lastHourlyLog = 0;
	private long lastDailyLowTweet = System.currentTimeMillis();
	private long lastAction = 0;
	private boolean actionMessage;
	
	private ClimateControl climateControl;
	private ActionState actionState;
	private TemperatureSample mullionDailyLow;
	private MullionState mullionState;
	private Calendar cal;
	private String mullionTempMessage;
	private NumberFormat nf;
	
	private String[] identifiers = null;
	
	private MullionWatcher() {
		mullionState = MullionState.getInstance();
	}

	/*
	 * Main control loop for the application
	 */
	public void run() {
		actionState = ActionState.normal;
		actionMessage = false;
		mullionDailyLow = null;
		cal = Calendar.getInstance();
		
		// Set up *the* number formatter (we only need one)
		nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(1);
		
		while (!SHUTDOWN) {
			// Let's check things every five minutes
			try {
				Thread.sleep(5*ONE_MINUTE);
			} catch (InterruptedException e) {}

			// If we're just a sender, then short circuit
			if (SENDER_ONLY) continue;
			
			// Get current time
			now = System.currentTimeMillis();
			cal.setTimeInMillis(now);

			// Get current mullion temperature
			TemperatureSample mullionTemp = mullionState.getTemperature();
			if (mullionTemp == null) {
				if (lastReadingAlarm < now-ONE_HOUR) {
					tweet("WARN: No mullion temperature readings", true);
					lastReadingAlarm = now;
				}
				continue;
			}
			
			// Get indoor climate, calculate dew point & spread
			float indoorTemperature = climateControl.getLowestTemperature();
			int indoorHumidity = climateControl.getHighestHumidity();
			float dewPoint = Dewpoint.calculate(indoorTemperature, indoorHumidity);
			float spread = mullionTemp.getTemperature()-dewPoint;
			mullionTempMessage = "Mullions at "+ nf.format(mullionTemp.getTemperature())
				+" (spread="+nf.format(spread)+")";

			// Get all the thermostats
			Collection<Thermostat> thermostats = climateControl.getThermostats();
			if (!((thermostats != null) && (thermostats.size() >= 1))) {
				if (lastAlarmNotification < now-ONE_HOUR) {
					String message = "Climate control did not return any thermostats!";
					logger.error(message);
					tweet(message, true);
					lastAlarmNotification = now;
				}
				// Extract the thermostat identifiers
				identifiers = new String[thermostats.size()];
				int i=0;
				for (Thermostat thermostat: thermostats) {
					identifiers[i++] = thermostat.getIdentifier();
				}
				continue;
			}
			
			// Get the set points from the first thermostat (should all be the same)
			Thermostat firstThermostat = thermostats.iterator().next();
			float heatingSetPoint = firstThermostat.getRuntime().getDesiredHeat();
			float coolingSetPoint = firstThermostat.getRuntime().getDesiredCool();

			// Log details hourly
			if (lastHourlyLog < now-ONE_HOUR) {
				if (firstThermostat.getWeather() != null) {
					Weather weather = firstThermostat.getWeather();
					if (weather != null) {
						logger.info("Outside: temperature="+Math.round(weather.getTemperature())
							+", humidity="+weather.getRelativeHumidity()+"%");
					}
					logger.info("Inside: temperature="+Math.round(indoorTemperature)
						+", humidity="+Math.round(indoorHumidity)+"%");
					logger.info("Mullions: temperature="+nf.format(mullionTemp.getTemperature())
						+", dew point="+nf.format(dewPoint));
					logger.info("Thermostat: heating set point="+Math.round(heatingSetPoint));
				} else {
					logger.info("Climate control did not provide any weather information");
				}
				lastHourlyLog = now;
			}

			// Do periodic mullion temperature report(s)
			doReport(mullionTemp);
			
			// Return to normal?
			if (spread >= WATCH_SPREAD) {
				actionState = ActionState.normal;
			}
			
			// Progression of states
			switch (actionState) {
			case normal:
				if (spread < WATCH_SPREAD) {
					actionState = ActionState.watch;
					doWatch();
					continue;
				}
			case watch:
				if (spread < ALARM_SPREAD) {
					actionState = ActionState.alarm;
					doAlarm();
					continue;
				}
			case alarm:
				if (spread < ACTION_SPREAD) {
					actionState = ActionState.action;
					doAction(spread, heatingSetPoint, coolingSetPoint);
					continue;
				}
				break;
			case action:
				if (spread >= RECOVERY_SPREAD) {
					actionState = ActionState.recovery;
					doRecovery();
					continue;
				}
				break;
			case recovery:
				actionState = ActionState.normal;
				doNormal();
				continue;
			default:
				logger.warn("Impossible place - this is either Hell or the Faroe Islands");
				continue;
			}
			
		}
	}
	
	/*
	 * Periodic reporting of mullion temperature
	 */
	private void doReport(TemperatureSample mullionTemp) {
		// Update mullion daily low
		if (mullionDailyLow == null) {
			mullionDailyLow = mullionTemp;
		} else {
			float dailyLowTemp = mullionDailyLow.getTemperature();
			mullionDailyLow = mullionTemp.getTemperature() < dailyLowTemp ? mullionTemp : mullionDailyLow;
		}
		
		// At 8am, tweet the daily low (with at least 5 hours of info)
		if (cal.get(Calendar.HOUR_OF_DAY) == 8 && lastDailyLowTweet < now-5*ONE_HOUR) {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			String message = "24hr low was "+nf.format(mullionDailyLow.getTemperature())
				+" ("+mullionDailyLow.getSensorName()+" @ "+df.format(new Date(mullionDailyLow.getTimestamp()))
				+") of "+mullionState.getSensorCount()+" sensors";
			logger.info(message);
			tweet(message, false);
			mullionDailyLow = null; // Clear the daily low
			lastDailyLowTweet = now;
		}
	}
	
	/*
	 * Actions to perform when entering normal state
	 */
	private void doNormal() {
		actionMessage = false;
	}

	/*
	 * Actions to perform when entering watch state
	 */
	private void doWatch() {
		// Notify when within watch spread
		if (lastWatchNotification < now-ONE_HOUR) {
			String message = "CONDENSATION WATCH: "+mullionTempMessage;
			logger.info(message);
			tweet(message, true);
			lastWatchNotification = now;
		}
	}
	
	/*
	 * Actions to perform when entering alarm state
	 */
	private void doAlarm() {
		// Notify when nearing dew point
		if (lastAlarmNotification < now-ONE_HOUR) {
			String message = "CONDENSATION ALARM: "+mullionTempMessage;
			logger.info(message);
			tweet(message, true);
			lastAlarmNotification = now;
		}
	}
	
	/*
	 * Actions to perform when entering action state
	 */
	private void doAction(float spread, float heatingSetPoint, float coolingSetPoint) {
		// Act when condensation is imminent
		try {
			// Calculate desired heating set point
			// Algorithm accelerates as spread worsens
			float desiredHeatTemp = heatingSetPoint+(spread-(ALARM_SPREAD+1));
			// Go no lower than MINIMUM_TEMPERATURE
			desiredHeatTemp = desiredHeatTemp >= MINIMUM_TEMPERATURE ? desiredHeatTemp : MINIMUM_TEMPERATURE;

			// Change set point only downward
			boolean setHold = (int)desiredHeatTemp < (int)heatingSetPoint;
			// Refresh one minute before it expires
			setHold |= lastAction < now - (HOLD_HOURS * ONE_HOUR) + ONE_MINUTE;
			// Don't set more frequently than every 15 minutes
			setHold &= lastAction < now - 15 * ONE_MINUTE;

			// Set the hold
			// NOTE: At the end of each hold period, the heating set point can drift upward
			if (setHold & identifiers != null) {
				climateControl.setHold(identifiers, (int)desiredHeatTemp, Math.round(coolingSetPoint), HOLD_HOURS, actionMessage);
				lastAction = now;
				String message = "ACTION: Changed thermostat heat set point to "+(int)desiredHeatTemp;
				logger.info(message);
				tweet(message, true);
			}
			
			// only put one message on thermostat per action event
			actionMessage = true;
		} catch (Exception e) {
			String message = "Unable to set heating set point";
			logger.error(message, e);
		}
	}
	
	/*
	 * Actions to perform when entering recovery state
	 */
	private void doRecovery() {
		// Return to normal operation
		try {
			climateControl.releaseHold(identifiers);
			String message = "CONDENSATION RECOVERY: "+mullionTempMessage;
			logger.info(message);
			tweet(message, true);
		} catch (Exception e) {
			String message = "Unable to set heating set point";
			logger.error(message, e);
		}
	}
	
	/*
	 * Tweet
	 */
	private void tweet(String message, boolean timestamp) {
		Agent twitterAgent = Agent.getInstance();
		twitterAgent.updateStatus(message, true);
	}
	
	public static void main(String[] args) {
		String receiverHost = "localhost";
		int receiverPort = 9876;

		logger.info("Starting up");
		
		Properties props = new Properties();
		try {
			File file = new File(MULLIONWATCHER_PROPERTIES_FILE);
			if (!file.exists()) {
				logger.warn(MULLIONWATCHER_PROPERTIES_FILE+"file does not exist!");
			} else {
				props.load(new FileInputStream(file));
				receiverHost = props.getProperty("sender.receive_host");
				receiverPort = Integer.parseInt(props.getProperty("sender.receive_port"));
				MINIMUM_TEMPERATURE = Integer.parseInt(props.getProperty("control.minimum_temperature"));
			}
		} catch (Exception e) {
			logger.error("Unable to load MullionWatcher properties", e);
		}
		
		if (!receiverHost.startsWith("localhost")) {
			SENDER_ONLY = true;
		}

		MullionWatcher mw = new MullionWatcher();
		
		if (!SENDER_ONLY) {
			// Start mullion Receiver thread
			logger.info("Starting Receiver thread");
			Receiver receiver = null;
			try {
				receiver = new Receiver(receiverPort);
				receiver.setDaemon(true);
				receiver.start();
			} catch (SocketException e) {
				logger.error(e);
				System.exit(1);
			}
			
			// Start Ecobee climate thread
			// TODO Load the appropriate climate control based on MullionWatcher properties
			logger.info("Starting Ecobee Climate Control thread");
			try {
				Control ecobeeControl = Control.getInstance();
				ecobeeControl.setDaemon(true);
				ecobeeControl.start();
				mw.climateControl = ecobeeControl;
			} catch (Exception e) {
				logger.fatal("Unable to start Ecobee climate control thread", e);
				System.exit(1);
			}
		}
		
		// Start mullion Broadcaster thread
		logger.info("Starting Broadcaster thread");
		Sender broadcaster = null;
		try {
			broadcaster = new Sender(receiverHost, receiverPort);
			broadcaster.setDaemon(true);
			broadcaster.start();
		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
		
		// Main loop
		mw.run();
		logger.info("Shutting down");
		System.exit(0);
	}

	/*
	 * Shutdown this thread
	 */
	public static void shutdown() {
		SHUTDOWN = true;
	}

}
