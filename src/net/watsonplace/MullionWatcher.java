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

import java.net.SocketException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.watsonplace.climate.ClimateControl;
import net.watsonplace.climate.Dewpoint;
import net.watsonplace.climate.ecobee.Control;
import net.watsonplace.mullion.MullionState;
import net.watsonplace.mullion.Receiver;
import net.watsonplace.mullion.Sender;
import net.watsonplace.mullion.TemperatureSample;
import net.watsonplace.twitter.Agent;

import org.apache.log4j.Logger;

public class MullionWatcher {
	private static final Logger logger = Logger.getLogger(MullionWatcher.class.getName());
	private static boolean shutdown = false;
	private static final long ONE_MINUTE = 60*1000;
	private static final long ONE_HOUR = 60*ONE_MINUTE;
	private static final String RECEIVER_HOST = "localhost";
	private static final int RECEIVER_PORT = 9876;
	private static final float TWEET_SPREAD = 5.0f;
	private static final float ALARM_SPREAD = 2.0f;
	
	private ClimateControl climateControl;

	/*
	 * 
	 * Get mullion temperature (T)
	 * Get dewpoint (DP) from Ecobee
	 * Compare
	 * If T <= (DP+5), tweet warning
	 * If T <= (DP+2), tweet alarm
	 * 
	 */
	public void run() {
		long lastReadingAlarm = System.currentTimeMillis();
		long lastAlarmTweet = 0;
		long lastHourlyLog = 0;
		long lastHourlyTweet = 0;
		long lastDailyLowTweet = System.currentTimeMillis();
		
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

			// Get indoor dew point
			float dewPoint = Dewpoint.calculate(climateControl.getTemperature(), climateControl.getHumidity());

			// Log climate hourly
			if (lastHourlyLog < now-ONE_HOUR) {
				logger.info("Mullion temperature is "+state.getTemperature());
				logger.info("Dew point is "+dewPoint);
				lastHourlyLog = now;
				continue;
			}
			
			// Alarm when nearing dew point
			float spread = state.getTemperature()-dewPoint;
			if (spread < ALARM_SPREAD) {
				if (lastAlarmTweet < now-ONE_HOUR) {
					twitterAgent.updateStatus("CONDENSATION ALARM: mullions at "+state.getTemperature()
						+" (spread="+spread+")", true);
					lastAlarmTweet = now;
				}
				continue;
			}

			// Warn when spread < 5 degrees
			if (spread <= TWEET_SPREAD) {
				
				// Tweet temp hourly
				if (lastHourlyTweet < now-ONE_HOUR) {
					twitterAgent.updateStatus("CONDENSATION WATCH: mullions at "+state.getTemperature()
						+" (spread="+spread+")", true);
					lastHourlyTweet = now;
					continue;
				}
					
				// At 8am, tweet daily low (with at least 5 hours of info)
				if (cal.get(Calendar.HOUR_OF_DAY) == 8 && lastDailyLowTweet < now-5*ONE_HOUR) {
					DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
					twitterAgent.updateStatus("24hr low was "+low.getTemperature()
						+" ("+low.getSensorName()+" @ "+df.format(new Date(low.getTimestamp()))
						+") of "+mullionState.getSensorCount()+" sensors", false);
					lastDailyLowTweet = now;
					continue;
				}
			}
		}
	}

	public static void main(String[] args) {
		logger.info("Starting up");
		
		MullionWatcher mw = new MullionWatcher();
		
		// Start mullion Receiver thread
		logger.debug("Starting Receiver thread");
		Receiver receiver = null;
		try {
			receiver = new Receiver(RECEIVER_PORT);
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
			broadcaster = new Sender(RECEIVER_HOST, RECEIVER_PORT);
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
