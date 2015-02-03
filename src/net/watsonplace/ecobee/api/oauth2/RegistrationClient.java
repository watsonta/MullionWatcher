package net.watsonplace.ecobee.api.oauth2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.watsonplace.ecobee.api.Status;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegistrationClient {
	private static final Logger logger = Logger.getLogger(RegistrationClient.class.getName());
	private static final String ECOBEE_API_URI = "https://api.ecobee.com/";
	
	private String APIKey = null;
	private Gson gson;
	
	private RegistrationClient(String APIKey) {
		this.APIKey = APIKey;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}
	
	public PIN getPIN() throws Exception {
		PIN pin = null;
		
		if (APIKey == null) {
			throw new Exception("APIKey is null");
		}
		HttpGet request = new HttpGet(ECOBEE_API_URI+"authorize?response_type=ecobeePin&client_id="+APIKey+"&scope=smartWrite");
		Object o = executeHttpRequest(request, PIN.class);
		if (o instanceof PIN) {
			pin = (PIN)o;
		} else if (o instanceof Status) {
			Status status = (Status)o;
			logger.error("Pin request failed: "+status.getMessage());
		}
		return pin;
	}
	
	public OAuthToken register(PIN pin) throws Exception {
		OAuthToken token = null;
		
		if (pin == null) {
			throw new Exception("pin is null");
		}
		HttpPost request = new HttpPost(ECOBEE_API_URI+"token?grant_type=ecobeePin&code="+pin.getCode()+"&client_id="+APIKey);
		Object o = executeHttpRequest(request, OAuthToken.class);
		if (o instanceof OAuthToken) {
			token = (OAuthToken)o;
		} else {
			logger.error("Registration failed!");
		}
		return token;
	}
	
	/**
	 * Process an HTTP request
	 * @param request - the request
	 * @param clazz - the desired return type
	 * @return - the return type specified in the arguments, or Status class otherwise
	 */
	private Object executeHttpRequest(HttpUriRequest request, java.lang.Class clazz) {
		HttpClient client = HttpClientBuilder.create().build();
		Object result = null;
		
		// Send the request
		HttpResponse response;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("Client protocol error", e);
			return null;
		} catch (IOException e) {
			logger.error("IO Exception", e);
			return null;
		}
		
		Reader reader = null;
		
		// Read the response
		InputStream content = null;
		try {
			HttpEntity entity = response.getEntity();
			content = entity.getContent();
			reader = new InputStreamReader(content);
		
			// Check the response status
			StatusLine statusLine = response.getStatusLine();
			logger.debug("Server responded with status code: " + statusLine.getStatusCode());
			if (statusLine.getStatusCode() == 200) {
				result = gson.fromJson(reader, clazz);
			} else {
				result = null;
			}
		} catch (IllegalStateException e) {
			logger.error("Illegal state exception", e);
			return null;
		} catch (IOException e) {
			logger.error("IO Exception", e);
		} finally {
			// Close the input stream
			try {
				reader.close();
				content.close();
			} catch (IOException e) {
				logger.error("Unable to close content InputStream", e);
			}
		}
		return result;
	}

	/**
	 * Execute this main with your Ecobee API Key as the only argument
	 * to get the access_token and refresh_token.
	 * @param args
	 */
	public static void main(String[] args) {
		final int WAIT_TIME_SEC = 20000; // sec
		
		PIN pin = null;
		OAuthToken token = null;
		
		if (args.length == 0 || args[0].length() == 0) {
			System.out.println("Missing API Key argument");
			System.exit(0);
		}
		
		RegistrationClient api = new RegistrationClient(args[0]);
		// Submit API Key and get PIN
		try {
			pin = api.getPIN();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Your MullionWatcher pin is: \""+pin.getEcobeePin()+"\"");
		System.out.println("Add the MullionWatcher app with this pin in the \"Apps\" widget on the Ecobee website.");
		System.out.println("Waiting "+WAIT_TIME_SEC/1000+"s ...");
		
		// Give user some time to enter on Ecobee website
		try {
			Thread.sleep(WAIT_TIME_SEC);
		} catch (InterruptedException e) {}
		
		// Fetch access and refresh tokens
		try {
			token = api.register(pin);
			if (token != null) {
				System.out.println("Your Ecobee access_token is: \""+token.getAccess_token()+"\"");
				System.out.println("Your Ecobee refresh_token is: \""+token.getRefresh_token()+"\"");
				token.save();
				System.out.println("Saved in file: "+token.getTokenFileLocation());
			} else {
				logger.error("No OAuth2 token was returned");
			}
		} catch (Exception e) {
			logger.error("Error while fetching OAuth2 token", e);
		}
		
	}

}
