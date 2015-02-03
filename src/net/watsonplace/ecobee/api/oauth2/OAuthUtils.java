package net.watsonplace.ecobee.api.oauth2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Set;

import net.watsonplace.ecobee.api.APIObject;
import net.watsonplace.ecobee.api.EcobeeResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
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

	/**
	 * Fetch a protected resource with a GET
	 * @param url
	 * @param apiObject
	 * @return
	 * @throws Exception
	 */
	public EcobeeResponse<APIObject> getProtectedResource(String url, Set<APIObject> apiObjects) throws Exception {
		// Create request
		String jsonRequest = createJsonRequest(apiObjects);
		String encodedJsonRequest = "?format=json&body="+URLEncoder.encode(jsonRequest, "UTF-8");
		HttpGet get = new HttpGet(oauthDetails.getResourceServerUrl()+url+encodedJsonRequest);
		// Send request and get response
		return retrieveProtectedResource(get);
	}

	/**
	 * Fetch a protected resource with a POST
	 * @param url
	 * @param apiObject
	 * @return
	 * @throws Exception
	 */
	public EcobeeResponse<APIObject> postProtectedResource(String url, Set<APIObject> apiObjects) throws Exception {
		String jsonRequest = createJsonRequest(apiObjects);
		return postProtectedResource(url, jsonRequest);
	}

	public EcobeeResponse<APIObject> postProtectedResource(String url, String jsonRequest) throws Exception {
		String encodedJsonRequest = "?format=json&body="+URLEncoder.encode(jsonRequest, "UTF-8");
		HttpPost post = new HttpPost(oauthDetails.getResourceServerUrl()+url+encodedJsonRequest);
		// Send request and get response
		return retrieveProtectedResource(post);
	}
	
	/**
	 * Encode an APIObject as a json request
	 * @param apiObject
	 * @return
	 * @throws Exception
	 */
	private String createJsonRequest(Set<APIObject> apiObjects) throws Exception {
		StringBuilder builder = new StringBuilder("{ ");
		int count = 0;
		for (APIObject apiObject : apiObjects) {
			builder.append(apiObject.toJson());
			if (++count < apiObjects.size()) {
				builder.append(", ");
			}
		}
		builder.append(" }");
		return builder.toString();
	}

	/**
	 * Authenticate and send the request
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private EcobeeResponse<APIObject> retrieveProtectedResource(HttpRequestBase request) throws Exception {
		// Refresh token, if necessary
		if (System.currentTimeMillis() > tokenExpiration) {
			token.refresh(oauthDetails);
			tokenExpiration = token.getExpiresInAsFromNow();
		}

		// Add BEARER header with OAuth access token
		request.addHeader(OAuthConstants.AUTHORIZATION, OAuthConstants.BEARER + " " + token.getAccess_token());
		
		// Send it and get response
		EcobeeResponse<APIObject> ecobeeResponse = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			String payload = IOUtils.toString(content);
			ecobeeResponse = new EcobeeResponse<APIObject>(payload);
			int code = response.getStatusLine().getStatusCode();
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
			request.releaseConnection();
		}
		
		return ecobeeResponse;
	}
	
//	public static void main(String[] args) {
//		try {
//			OAuthUtils oauthUtils = new OAuthUtils();
//			oauthUtils.postProtectedResource("", jsonBody)
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
