package com.snapdeal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

public class GoogleOAuthSetup {
	
	
	/**
	 * Global instance of the {@link DataStoreFactory}. The best practice is to
	 * make it a single globally shared instance across your application.
	 */
	private static FileDataStoreFactory dataStoreFactory;
	
	/** Directory to store user credentials. */
	  private static final java.io.File DATA_STORE_DIR =
	      new java.io.File("./appdata/credentials/gOAuth");
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	 /** Global instance of the JSON factory. */
	 static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	 
	 static final String OAUTH_SCOPE = "https://www.googleapis.com/auth/structuredcontent";
	
	 private static GoogleAuthorizationCodeFlow getAuthorizationFlow() throws Exception{
		 // Load Client Secrets JSON
		 GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, 
				 		new InputStreamReader(GoogleOAuthSetup.class.getResourceAsStream("/client_secrets.json")));
		
		 // Setup authorization code flow
		 return new GoogleAuthorizationCodeFlow.Builder(
				 		httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(OAUTH_SCOPE))
		 			.setAccessType("offline")
				 	.setDataStoreFactory(dataStoreFactory).build();
	 }
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			
			GoogleAuthorizationCodeFlow flow = getAuthorizationFlow();
			GoogleAuthorizationCodeRequestUrl requestURL = flow.newAuthorizationUrl().setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI);
			System.out.println("Enter the below url in your browser and paste the received authorization code:");
			System.out.println(requestURL);
			
			System.out.println("Paste Authorization code here:");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String AUTH_CODE = in.readLine();

			// Get Access Token
			GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(AUTH_CODE);
			GoogleTokenResponse tokenResponse = tokenRequest.setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI).execute();
			
			// Store the credentials
			Credential gc = flow.createAndStoreCredential(tokenResponse, "CPS_ONLINE_MARKETING@SNAPDEAL");
			System.out.println("Credentials created for user. You can now proceed.");
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		

	}

}
