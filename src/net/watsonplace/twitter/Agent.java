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

import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Agent {
	private static final Logger logger = Logger.getLogger(Agent.class.getName());
	
	// @BPMullions
	private static String CONSUMER_KEY = "PqHFEjJnItyUEFB88TsKfkgQD";
	private static String CONSUMER_SECRET = "uEhznUdiVKVvkFepjRiqJONt8r4XfCa5M9hlb8cuS4LAE1dGLV";
	private static String TOKEN = "2964796963-di54MTj9Oq1YkMsqBfEno6cObZftaDu93gefWJZ";
	private static String TOKEN_SECRET = "ADmSqSWPQl9QHmjcjNN5ryt9SYgO6trnnu5AVgxcTFAPw";

	private static Agent singleton = null;
	
	public static Agent getInstance() {
		if (singleton == null) {
			singleton = new Agent();
		}
		return singleton;
	}
	
	private Twitter twitter = null;

	private Agent() {
		twitter = TwitterFactory.getSingleton();
	}

	public synchronized boolean updateStatus(String tweetBody, boolean appendTimestamp) {
		StringBuffer tweet = new StringBuffer("#MullionWatcher "+tweetBody);
		if (appendTimestamp) {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			tweet.append(" ("+df.format(new Date(System.currentTimeMillis())+")"));
		}
		
		logger.info("Tweeting: #MullionWatcher "+tweetBody);
		try {
		    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		    AccessToken accessToken = new AccessToken(TOKEN, TOKEN_SECRET);
		    twitter.setOAuthAccessToken(accessToken);
			twitter.updateStatus(tweet.toString());
		} catch (TwitterException e) {
			logger.error("Couldn't tweet!");
			return false;
		}
		return true;
	}
	
}
