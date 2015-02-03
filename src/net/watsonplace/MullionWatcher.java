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
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import net.watsonplace.climate.ClimateControl;
import net.watsonplace.climate.Dewpoint;
import net.watsonplace.climate.ecobee.Control;
import net.watsonplace.ecobee.api.Thermostat;
import net.watsonplace.ecobee.api.WeatherForecast;
import net.watsonplace.mullion.MullionState;
import net.watsonplace.mullion.Receiver;
import net.watsonplace.mullion.Sender;
import net.watsonplace.mullion.TemperatureSample;
import net.watsonplace.twitter.Agent;

import org.apache.log4j.Logger;

public class MullionWatcher {
	private static final Logger logger = Logger.getLogger(MullionWatcher.class.getName());

	private static final String MULLIONWATCHER_PROPERTIES_FILE = "conf/MullionWatcher.properties";
	private static final long ONE_MINUTE = 60*1000;
	private static final long ONE_HOUR = 60*ONE_MINUTE;
	private static final int TWEET_SPREAD = 5;
	private static final int ALARM_SPREAD = 2;
	private static final int ACTION_SPREAD = 1;
	
	private static boolean shutdown = false;
	
	private ClimateControl climateControl;
	
	private MullionWatcher() {}

	/**
	 * Main control loop for the application
	 */
	public void run() {
		long lastReadingAlarm = System.currentTimeMillis();
		long lastAlarmTweet = 0;
		long lastHourlyLog = 0;
		long lastHourlyTweet = 0;
		long lastDailyLowTweet = System.currentTimeMillis();
		long lastAction = 0;
		
		TemperatureSample low = null;
		
		MullionState mullionState = MullionState.getInstance();
		Agent twitterAgent = Agent.getInstance();
		
		Calendar cal = Calendar.getInstance();
		while (!shutdown) {
			// Get current time
			long now = System.currentTimeMillis();
			cal.setTimeInMillis(now);
			
			try {
				Thread.sleep(ONE_MINUTE);
			} catch (InterruptedException e) {}
			
			// Get mullion state
			TemperatureSample state = mullionState.getLowestTemperature();
			
			// Warn if no readings
			if (state == null) {
				if (lastReadingAlarm < now-ONE_HOUR) {
					twitterAgent.updateStatus("WARN: No mullion readings", true);
					lastReadingAlarm = now;
				}
				continue;
			}

			// Update lowest temperature
			low = (low == null || state.getTemperature() < low.getTemperature()) ? state : low;

			// Get indoor dew point & calculate spread
			float dewPoint = Dewpoint.calculate(climateControl.getLowestTemperature(), climateControl.getHighestHumidity());
			float spread = state.getTemperature()-dewPoint;

			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMinimumFractionDigits(1);
			nf.setMaximumFractionDigits(1);
			
			// Get a set of all the thermostats
			Set<Thermostat> thermostats = climateControl.getThermostats();
			if (thermostats.size() < 1) {
				continue;
			}
			// Get the set points from the first thermostat (should all be the same)
			Thermostat firstThermostat = thermostats.iterator().next();
			float heatingSetPoint = firstThermostat.getRuntime().getDesiredHeat();
			float coolingSetPoint = firstThermostat.getRuntime().getDesiredCool();
			
			// Get indoor climate
			float indoorTemperature = firstThermostat.getRuntime().getTemperature();
			float indoorHumidity = firstThermostat.getRuntime().getHumidity();
			
			// Get mullion temperature
			float mullionTemperature = state.getTemperature();
			
			// Log climate hourly
			if (lastHourlyLog < now-ONE_HOUR && firstThermostat.getWeather() != null) {
				WeatherForecast[] forecasts = firstThermostat.getWeather().getForecasts();
				if (forecasts.length > 0) {
					logger.info("Outside: temperature="+Math.round(forecasts[0].getTemperature()/10f)
						+", humidity="+forecasts[0].getHumidity()+"%");
				}
				logger.info("Inside: temperature="+Math.round(indoorTemperature)
					+", humidity="+Math.round(indoorHumidity)+"%");
				logger.info("Mullions: temperature="+nf.format(mullionTemperature)
					+", dew point="+nf.format(dewPoint));
				logger.info("Thermostat: heating set point="+Math.round(heatingSetPoint));
				lastHourlyLog = now;
			}
			
			// At 8am, tweet daily low (with at least 5 hours of info)
			if (cal.get(Calendar.HOUR_OF_DAY) == 8 && lastDailyLowTweet < now-5*ONE_HOUR) {
				DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
				String message = "24hr low was "+nf.format(low.getTemperature())
					+" ("+low.getSensorName()+" @ "+df.format(new Date(low.getTimestamp()))
					+") of "+mullionState.getSensorCount()+" sensors";
				logger.info(message);
				twitterAgent.updateStatus(message, false);
				lastDailyLowTweet = now;
			}

			// Act when condensation is imminent
			if (spread < ACTION_SPREAD && lastAction < now-0.25*ONE_HOUR && indoorTemperature <= heatingSetPoint) {
				try {
					float desiredHeatTemp = heatingSetPoint+(spread-ALARM_SPREAD); // Accelerates as condition worsens
					desiredHeatTemp = desiredHeatTemp >= 65f ? desiredHeatTemp : 65f; // No lower than 65F
					if ((int)desiredHeatTemp < (int)heatingSetPoint) {
						lastAction = now;
						climateControl.setHold((int)desiredHeatTemp, Math.round(coolingSetPoint), 4); // 4 hour hold
						String message = "ACTION: Changed thermostat heat set point to "+desiredHeatTemp;
						logger.info(message);
						twitterAgent.updateStatus(message, true);
					}
				} catch (Exception e) {
					String message = "Unable to set thermostat heat set point";
					logger.error(message, e);
					twitterAgent.updateStatus("FAIL: "+message, true);
				}
			}
			
			// Alarm when nearing dew point
			// or tweet temp hourly when within watch spread
			if (spread < ALARM_SPREAD && lastAlarmTweet < now-ONE_HOUR) {
				String message = "CONDENSATION ALARM: Mullions at "+nf.format(state.getTemperature())
					+" (spread="+nf.format(spread)+")";
				logger.info(message);
				twitterAgent.updateStatus(message, true);
				lastAlarmTweet = now;
			} else if (spread <= TWEET_SPREAD && lastHourlyTweet < now-ONE_HOUR) {
				String message = "CONDENSATION WATCH: mullions at "+ nf.format(state.getTemperature())
					+" (spread="+nf.format(spread)+")";
				logger.info(message);
				twitterAgent.updateStatus(message, true);
				lastHourlyTweet = now;
			}
				
		}
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
			}
		} catch (Exception e) {
			logger.fatal("Unable to load MullionWatcher properties", e);
		}

		MullionWatcher mw = new MullionWatcher();
		
		// Start mullion Receiver thread
		logger.debug("Starting Receiver thread");
		Receiver receiver = null;
		try {
			receiver = new Receiver(receiverPort);
			receiver.setDaemon(true);
			receiver.start();
		} catch (SocketException e) {
			logger.error(e);
			System.exit(1);
		}
		
		// Start mullion Broadcaster thread
		logger.debug("Starting Broadcaster thread");
		Sender broadcaster = null;
		try {
			broadcaster = new Sender(receiverHost, receiverPort);
			broadcaster.setDaemon(true);
			broadcaster.start();
		} catch (Exception e) {
			logger.error(e);
			System.exit(1);
		}
		
		// Start Ecobee climate thread
		try {
			Control ecobeeControl = Control.getInstance();
			ecobeeControl.setDaemon(true);
			ecobeeControl.start();
			mw.climateControl = ecobeeControl;
		} catch (Exception e) {
			logger.fatal("Unable to start Ecobee climate control thread", e);
			return;
		}
		
		// Main loop
		mw.run();
		logger.info("Shutting down");
		System.exit(0);
	}
	
	public static void shutdown() {
		shutdown = true;
	}

}
