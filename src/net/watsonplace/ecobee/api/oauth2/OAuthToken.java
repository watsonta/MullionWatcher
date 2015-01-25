package net.watsonplace.ecobee.api.oauth2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents an Ecobee OAuth2 authorization token
 * @author tom
 *
 */
public class OAuthToken {
	private static final Logger logger = Logger.getLogger(Logger.class.getName());
	private static final String TOKEN_FILE = "conf/ecobee_token.properties";
	
	private static OAuthToken singleton = null;
	public static OAuthToken getInstance() throws Exception {
		if (singleton == null) {
			singleton = new OAuthToken();
		}
		return singleton;
	}
	
	private String access_token;
	private String token_type;
	private long expires_in;
	private String refresh_token;
	private String scope;

	private OAuthToken() throws Exception {
		load();
	}
	
	public OAuthToken(String access_token, String refresh_token) {
		this.access_token = access_token;
		this.token_type = "Bearer";
		this.refresh_token = refresh_token;
		this.expires_in = 0;
		this.scope = "SmartWrite";
	}
	
    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token'" + refresh_token + '\'' +
                ", scope='" + scope +
                '}';
    }

	public String getAccess_token() {
		return access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public long getExpires_in() {
		return expires_in;
	}
	
	public long getExpiresInAsFromNow() {
		return System.currentTimeMillis()+60000*expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public String getScope() {
		return scope;
	}
	
	public String getTokenFileLocation() {
		return TOKEN_FILE;
	}
	
	public void refresh(OAuthDetails oauthDetails) throws Exception {
		// Create request
		HttpPost request = new HttpPost(oauthDetails.getAuthenticationServerUrl()
			+"?grant_type=refresh_token&code="+refresh_token
			+"&client_id="+oauthDetails.getApiKey());
		HttpClient client = HttpClientBuilder.create().build();
			
		// Send the request
		HttpResponse response;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("Client protocol error", e);
			throw e;
		} catch (IOException e) {
			logger.error("IO Exception", e);
			throw e;
		}
		
		Reader reader = null;
		
		// Read response
		InputStream content = null;
		try {
			// Check response status
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
		        // Get inputstream (payload)
				HttpEntity entity = response.getEntity();
				content = entity.getContent();
				reader = new InputStreamReader(content);

				// Parse response payload as Token
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				OAuthToken newToken = gson.fromJson(reader, OAuthToken.class);
				access_token = newToken.getAccess_token();
				token_type = newToken.getToken_type();
				refresh_token = newToken.getRefresh_token();
				expires_in = newToken.getExpires_in();
				scope = newToken.getScope();
				
				// Persist
				save();
				logger.debug("Successfully refreshed token");
			} else {
				logger.error("Unable to refresh token. Status code: "+statusCode);
				throw new Exception("Unable to refresh token");
			}
		} catch (IllegalStateException e) {
			logger.error("Illegal state exception", e);
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
	}
	
	public void save() throws Exception {
		Properties tokenProps = new Properties();
		tokenProps.put("access_token", access_token);
		tokenProps.put("token_type", token_type);
		tokenProps.put("refresh_token", refresh_token);
		tokenProps.put("scope", scope);
		try {
			File file = new File(TOKEN_FILE);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream out = new FileOutputStream(TOKEN_FILE);
			tokenProps.store(out, "-- Ecobee API token --");
			out.close();
		} catch (IOException e) {
			logger.error("Unable to persist token", e);
			
		}
	}
	
	private void load() throws Exception {
		Properties tokenProps = new Properties();
		try {
			FileInputStream in = new FileInputStream(TOKEN_FILE);
			tokenProps.load(in);
			in.close();
			access_token = tokenProps.getProperty("access_token");
			token_type = tokenProps.getProperty("token_type");
			expires_in = 0;
			refresh_token = tokenProps.getProperty("refresh_token");
			scope = tokenProps.getProperty("scope");
		} catch (IOException e) {
			logger.error("Unable to load token", e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		OAuthToken t = new OAuthToken("ThisIsTheAccessToken", "ThisIsTheRefreshToken");
		try {
			t.save();
			logger.info("Token saved");
			System.out.println(t.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			t.load();
			logger.info("Token loaded");
			System.out.println(t.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
