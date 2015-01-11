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

	// @watsonta
//	private static String CONSUMER_KEY = "UkJPmqr2hyAADC7V6zcQZNZg9";
//	private static String CONSUMER_SECRET = "YNB26mD3EvJejl22h4EkAt9x93m8vvrNAaWxyiVjlWVti6etnY";
//	private static String TOKEN = "14904211-FyQ3LV1AaZ9HZauGHUavaYRzWypuxhBvDSf8ivCdH";
//	private static String TOKEN_SECRET = "m1RLDBBHAltwW8yr6nD4iZuzB743R7JXZ8dxfqMZOyaUT";
	
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
