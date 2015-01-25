package net.watsonplace.ecobee.api.oauth2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class OAuthUtils {
	private static final Logger logger = Logger.getLogger(OAuthUtils.class.getName());
	
	private OAuthDetails oauthDetails;
	private OAuthToken token;
	private long tokenExpiration = 0;

	public OAuthUtils() throws Exception {
		oauthDetails = OAuthDetails.getInstance();
		token = OAuthToken.getInstance();
		tokenExpiration = System.currentTimeMillis()+token.getExpires_in()*60000; // token expires_in is minutes
	}

	public String getProtectedResource(String url, String jsonBody) throws Exception {
		// Encode
		StringBuilder request = new StringBuilder("?format=json&body=");
		request.append(URLEncoder.encode(jsonBody, "UTF-8"));

		// Refresh token, if necessary
		if (System.currentTimeMillis() > tokenExpiration) {
			token.refresh(oauthDetails);
			tokenExpiration = token.getExpiresInAsFromNow();
		}
		
		// Create request
		HttpGet get = new HttpGet(oauthDetails.getResourceServerUrl()+url+request.toString());
		get.addHeader(OAuthConstants.AUTHORIZATION, OAuthConstants.BEARER + " " + token.getAccess_token());
		
		// Post it and get response
		String payload = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(get);
			int code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			payload = IOUtils.toString(content);
			if (code >= 400) {
				logger.error("HTTP status: "+code+", Response was: "+payload);
				throw new Exception("Ecobee server responded with HTTP status "+code);
			}
		} catch (ClientProtocolException e) {
			logger.error("Client protocol error", e);
			throw e;
		} catch (IOException e) {
			logger.error("IO Exception", e);
		} finally {
			get.releaseConnection();
		}
		return payload;
	}
	
}
