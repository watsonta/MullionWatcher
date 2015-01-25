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
package net.watsonplace.twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Agent {
	private static final Logger logger = Logger.getLogger(Agent.class.getName());
	private static final String TWITTER_PROPERTIES_FILE = "conf/twitter.properties";
	private static Agent singleton = null;

	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String presharedKey;
	private Properties twitterProperties = new Properties();
	
	public static Agent getInstance() {
		if (singleton == null) {
			singleton = new Agent();
		}
		return singleton;
	}
	
	private Twitter twitter = null;

	private Agent() {
		// Load twitter authentication properties
		try {
			twitterProperties.load(new FileReader(new File(TWITTER_PROPERTIES_FILE)));
			consumerKey = twitterProperties.getProperty("twitter.consumer.key");
			consumerSecret = twitterProperties.getProperty("twitter.consumer.secret");
			token = twitterProperties.getProperty("twitter.access.token");
			presharedKey = twitterProperties.getProperty("twitter.access.token.secret");
		} catch (FileNotFoundException e) {
			logger.fatal(TWITTER_PROPERTIES_FILE+" file not found");
			System.exit(1);
		} catch (IOException e) {
			logger.fatal("Unable to read Twitter properties");
			System.exit(1);
		};
		
		// Authenticate with Twitter
		twitter = TwitterFactory.getSingleton();
		String user = null;
		try {
			user = authenticate();
		} catch (IllegalStateException | TwitterException e) {
			logger.fatal("Twitter authentication failed: "+e.getMessage());
			System.exit(1);
		}
	    logger.info("Authenticated to Twitter as "+user);
	}
	
	/*
	 * Authenticate
	 * @returns Twitter User ID
	 */
	public String authenticate() throws IllegalStateException, TwitterException {
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);
	    AccessToken accessToken = new AccessToken(token, presharedKey);
	    twitter.setOAuthAccessToken(accessToken);
	    return twitter.getScreenName();
	}

	/*
	 * Update Twitter status
	 * @param tweetBody - The text to be tweeted
	 * @param appendTimestamp - Whether to add a timestamp to the tweet
	 * @returns - Whether or not the tweet was successful
	 */
	public synchronized boolean updateStatus(String tweetBody, boolean appendTimestamp) {
		StringBuffer tweet = new StringBuffer("#MullionWatcher "+tweetBody);

		// Add a timestamp so that Twitter won't filter as identical tweet
		if (appendTimestamp) {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			tweet.append(" ("+df.format(new Date(System.currentTimeMillis()))+")");
		}

		// Tweet
		logger.info("Tweeting: #MullionWatcher "+tweetBody);
		try {
			twitter.updateStatus(tweet.toString());
		} catch (TwitterException e) {
			logger.error("Couldn't tweet: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Agent a = Agent.getInstance();
		a.updateStatus("Test tweet", true);
	}
	
}
