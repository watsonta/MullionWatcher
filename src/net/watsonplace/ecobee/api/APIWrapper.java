package net.watsonplace.ecobee.api;

import java.util.Set;

import net.watsonplace.ecobee.api.oauth2.OAuthUtils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIWrapper implements API {
	private static final Logger logger = Logger.getLogger(APIWrapper.class.getName());
	private static APIWrapper singleton = null;
	
	public static APIWrapper getInstance() throws Exception {
		if (singleton == null) {
			singleton = new APIWrapper();
		}
		return singleton;
	}
	
	private OAuthUtils oauthUtils = null;
	private Gson gson;
	
	private APIWrapper() throws Exception {
		oauthUtils = new OAuthUtils();
		gson = new GsonBuilder().create();
	}
	
	@Override
	public Set<Thermostat> getThermostats() throws Exception {
		Selection selection = new Selection();
		String request = gson.toJson(selection);
		String url = "/thermostat";
		logger.debug("url="+url+", request="+request);
		EcobeeResponse<Thermostat> response = new EcobeeResponse<Thermostat>(oauthUtils.getProtectedResource(url, request));
		Status status = response.getStatus();
		if (status.getCode() > 0) {
			throw new Exception("Request failed: Ecobee status code="+status.getCode()+", message="+status.getMessage());
		}
		Set<Thermostat> thermostats = response.getResponse();
		return thermostats;
	}
	
}
