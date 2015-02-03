package net.watsonplace.ecobee.api.oauth2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class OAuthDetails {
	private static final Logger logger = Logger.getLogger(OAuthDetails.class.getName());
	
	private static final String OAUTH_PROPERTIES_FILE = "conf/ecobee.properties";
	private static final String AUTH_SERVER = "https://api.ecobee.com/token";
	private static final String RESOURCE_SERVER = "https://api.ecobee.com/1";
	
	private static OAuthDetails singleton = null;
	public static OAuthDetails getInstance() throws Exception {
		if (singleton == null) {
			singleton = new OAuthDetails();
		}
		return singleton;
	}

	private String apikEY;
	
	private OAuthDetails() throws Exception {
		Properties oauthProps = new Properties();
		try {
			File file = new File(OAUTH_PROPERTIES_FILE);
			if (!file.exists()) {
				throw new Exception(OAUTH_PROPERTIES_FILE+"file does not exist!");
			}
			FileInputStream in = new FileInputStream(file);
			oauthProps.load(in);
			in.close();
			apikEY = oauthProps.getProperty("api_key");
		} catch (IOException e) {
			logger.error("Unable to load token", e);
			throw e;
		}
	}
	
	public String getApiKey() {
		return apikEY;
	}

	public void setApiKey(String clientSecret) {
		this.apikEY = clientSecret;
	}
	
	public String getAuthenticationServerUrl() {
		return AUTH_SERVER;
	}

	public String getResourceServerUrl() {
		return RESOURCE_SERVER;
	}

}
