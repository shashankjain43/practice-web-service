package com.snapdeal.cps.google.oauth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * This class authenticates OAuth calls to Google Merchant Center (GMC) for the
 * Catalog Publishing System (CPS) module. It assumes that OAuth credentials
 * have already been setup.
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */

public class GoogleOAuth {

	private String userId;
	private String oAuthScope;

	private JsonFactory jsonFactory;
	private GoogleClientSecrets clientSecrets;

	private HttpTransport httpTransport;

	private FileDataStoreFactory dataStoreFactory;

	private GoogleAuthorizationCodeFlow flow;
	private Credential credentials;
	
	/**
	 * This method initialises the OAuth and loads the credentials
	 * 
	 * @param prop: A Properties object which contain the following key fields - 
	 * 			SNAPDEAL_UID = the UID with which the credential is stored. 
	 * 			OAUTH_SCOPE = the scope of the OAuth request. 
	 * 			DATASTORE_DIR = Path to the Credential datastore directory
	 */
	private void initialize(Properties prop) {
		try {
			userId = prop.getProperty("SNAPDEAL_UID");
			oAuthScope = prop.getProperty("OAUTH_SCOPE");

			jsonFactory = JacksonFactory.getDefaultInstance();
			clientSecrets = GoogleClientSecrets.load(jsonFactory,new InputStreamReader(this.getClass().getResourceAsStream("/client_secrets.json")));
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			dataStoreFactory = new FileDataStoreFactory(new java.io.File(prop.getProperty("DATASTORE_DIR")));

			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,jsonFactory, clientSecrets,
					Collections.singleton(oAuthScope)).setAccessType("offline")
					.setDataStoreFactory(dataStoreFactory).build();

			credentials = flow.loadCredential(userId);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * This constructor loads properties file and
	 * calls the <i>initialize</i> method
	 * 
	 */
	public GoogleOAuth() {
		Properties props = new Properties();
		try {
			props.load(GoogleOAuth.class.getClassLoader().getResourceAsStream("oauth.properties"));
			initialize(props);
		} catch (FileNotFoundException e) {
			System.out.println("Initialization Faliure: Unable to find properties file");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Initialization Faliure: Unable to read properties file");
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Unknown Exception while initializing OAuth");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public String getUserId() {
		return this.userId;
	}

	public String getOAuthScope() {
		return this.oAuthScope;
	}

	public FileDataStoreFactory getCredentialDataStoreFactory() {
		return this.dataStoreFactory;
	}

	public JsonFactory getJsonFactory() {
		return this.jsonFactory;
	}

	public HttpTransport getHttpTransport() {
		return this.httpTransport;
	}

	public GoogleClientSecrets getClientSecrets() {
		return this.clientSecrets;
	}

	public GoogleAuthorizationCodeFlow getAuthorizationFlow() {
		return this.flow;
	}

	public Credential getCredentials() {
		return this.credentials;
	}

	/**
	 * This method is called by the external class to get the OAuth access token
	 * required to make an authenticated request to Google. It checks if the
	 * access token has expired, if yes then it uses the stored refresh token to
	 * get a new access token.
	 * 
	 * @return access token
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
		Credential credential = this.getCredentials();
		if (credential.getExpiresInSeconds() <= 0) {
			boolean tokenRefreshed = credential.refreshToken();
			System.out.println("Token Refreshed = " + tokenRefreshed);
		}
		return credential.getAccessToken();
	}

}
