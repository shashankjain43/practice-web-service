package com.snapdeal.opspanel.restclient;

import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.amazonaws.HttpMethod;
import com.amazonaws.util.json.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.internal.LinkedTreeMap;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
public class RestClient {

	private final String CONTENT_ENCODING_UTF_8 = "UTF-8";
	private final String HTTPS = "https";
	private final long keepAlive = -1L;
	private final boolean mutualAuthenticationRequired = false;
	private final String keyStoreType = "jks";
	private final String keyStorePassword = "snapdeal";
	private final String keyStoreFile = "/opt/configuration/clientkeystore.jks";
	private final Integer maxConnTotal = 50;
	private final Integer maxConnPerRoute = 50;

	int apiTimeout;
	HttpClient httpClient;

	public RestClient( ClientConfig clientConfig ) {
		this.apiTimeout = clientConfig.apiTimeout;
	}

	public Map<String, Object> executeRestCall( String url, Map<String, Object> parameters, Map<String, String> headers, HttpMethod method )
		throws Exception{

		Map<String, Object> result = null;
		url = parseUrl( url, parameters );
		switch (method) {
		case GET:
			result = executeGet( url, parameters, headers );
			break;
		case PUT:
			result = executePut( url, parameters, headers );
			break;
		case POST:
			result = executePost( url, parameters, headers );
			break;
		case DELETE:
			result = executeDelete(url, parameters, headers );
			break;
		default:
			throw new OpsPanelException( "RC-0001", "Request method not supported" );
		}
		return result;
	}

	private Map<String, Object> executeGet( String url, Map<String, Object> parameters, Map<String, String> headers )
		throws OpsPanelException {

		if(parameters != null) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				url += "?" + entry.getKey() + "=" + entry.getValue() + "&";
			}
			url = url.substring( 0, url.length() - 1 );
		}

		HttpGet httpGet = new HttpGet( url );
		setHeaders( httpGet, headers );

		log.info( "OpsPanelRestClient: Making GET request URL: " + url + " Headers: " +  new JSONObject( headers ) );

		try {

			HttpClient httpClient = getHttpClient( url );
			
			HttpResponse response = httpClient.execute(httpGet);
			return httpResponseToHashMap( response );

		} catch (Exception e) {
		   log.error("Error in executing http get: " + ExceptionUtils.getFullStackTrace( e ) );
		   throw new OpsPanelException( "RC-0002", "Unable to execute HTTP Get: " + e.getMessage() );
		}

	}

	private Map<String, Object> executePut( String url, Map<String, Object> parameters, Map<String, String> headers )
		throws OpsPanelException {

		HttpPut httpPut = new HttpPut(url);
		setHeaders(httpPut, headers);

		try {
			if (parameters != null) {
				httpPut.setEntity( createStringEntity( parameters )  );
			}
			HttpClient httpClient = getHttpClient( url );

			log.info( "OpsPanelRestClient: Making PUT request URL: " + url + " Headers: " +  new JSONObject( headers ) + " Parameters: " + new JSONObject( parameters ) );

			HttpResponse response = httpClient.execute(httpPut);
			return httpResponseToHashMap( response );
		} catch (Exception e) {
	       log.error("Error in executing http put: " + ExceptionUtils.getFullStackTrace( e ) );
			throw new OpsPanelException("RC-0003", "Unable to execute HTTP Put: " + e.getMessage() );
		}
		
	}

	private Map<String, Object> executePost( String url, Map<String,Object> parameters, Map<String, String> headers )
		throws OpsPanelException {

		HttpPost httpPost = new HttpPost(url);
		setHeaders(httpPost, headers);
		try {
			if (parameters != null) {
				httpPost.setEntity(createStringEntity( parameters ));
			}
			HttpClient httpClient = getHttpClient( url );

			log.info( "OpsPanelRestClient: Making POST request URL: " + url + " Headers: " +  new JSONObject( headers ) + " Parameters: " + new JSONObject( parameters ) );

			HttpResponse response = httpClient.execute(httpPost);
			return httpResponseToHashMap( response );
		} catch (Exception e) {
	       log.error("Error in executing http post: " + ExceptionUtils.getFullStackTrace( e ) );
			throw new OpsPanelException( "RC-0004", ExceptionUtils.getFullStackTrace( e ) );
		}
	}

	private Map<String, Object> executeDelete( String url, Map<String, Object> parameters, Map<String, String> headers )
		throws OpsPanelException {
		HttpDelete httpDelete = new HttpDelete(createURL(url, parameters));
		setHeaders(httpDelete, headers);

		try {
			HttpClient httpClient = getHttpClient( url );

			log.info( "OpsPanelRestClient: Making DELETE request URL: " + url + " Headers: " +  new JSONObject( headers ) + " Parameters: " + new JSONObject( parameters ) );

			HttpResponse response = httpClient.execute(httpDelete);
			return httpResponseToHashMap( response );
		} catch (Exception e) {
			log.error("Error in executing http delete: " + ExceptionUtils.getFullStackTrace( e ) );
			throw new OpsPanelException( "RC-0005", ExceptionUtils.getFullStackTrace( e ) );
		}
	}

	public Map<String,Object> executeMultipartPost(String url, HttpEntity httpEntity,
			Map<String, String> headers ) throws Exception {

		HttpPost httpPost = new HttpPost(url);

		Map<String,String> requestHeaders = new HashMap<String, String>( headers );

		if(headers.containsKey("Accept")) requestHeaders.remove("Accept");
		if(headers.containsKey("Content-Type")) requestHeaders.remove("Content-Type");

		setHeaders(httpPost, requestHeaders);
		try {
			if (httpEntity != null) {
				httpPost.setEntity(httpEntity);
			}
			HttpClient httpClient = getHttpClient(url);

			log.info( "OpsPanelRestClient: Making MultipartPost request: " + url + " Headers: " +  new JSONObject( headers )  );

			HttpResponse response = httpClient.execute(httpPost);
			return httpResponseToHashMap( response );
		}catch (Exception e){
			log.error(" Exception in executing http multipart post : "+ ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException( "RC-0007", e.getMessage() );
		}
	}

	private void setHeaders(HttpRequestBase httpObject,
			Map<String, String> headers) {
		if (headers != null) {
			for (Map.Entry<String, String> hEntry : headers.entrySet()) {
				httpObject.addHeader(hEntry.getKey(), hEntry.getValue());
			}
		}
	}

	private Map<String, Object> httpResponseToHashMap( HttpResponse response ) throws Exception {
		String json = EntityUtils.toString( response.getEntity() );

		log.info( "OpsPanelRestClient: Response: " +  json );
 
		GsonBuilder builder = new GsonBuilder();  
		builder.registerTypeAdapter(new TypeToken<Map <String, Object>>(){}.getType(), new JsonDeserializer<Map<String,Object>>() {

			 @Override  @SuppressWarnings("unchecked")
			    public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			        return (Map<String, Object>) read(json);
			    }

			    public Object read(JsonElement in) {

			        if(in.isJsonArray()){
			            List<Object> list = new ArrayList<Object>();
			            JsonArray arr = in.getAsJsonArray();
			            for (JsonElement anArr : arr) {
			                list.add(read(anArr));
			            }
			            return list;
			        }else if(in.isJsonObject()){
			            Map<String, Object> map = new LinkedTreeMap<String, Object>();
			            JsonObject obj = in.getAsJsonObject();
			            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
			            for(Map.Entry<String, JsonElement> entry: entitySet){
			                map.put(entry.getKey(), read(entry.getValue()));
			            }
			            return map;
			        }else if( in.isJsonPrimitive()){
			            JsonPrimitive prim = in.getAsJsonPrimitive();
			            if(prim.isBoolean()){
			                return prim.getAsBoolean();
			            }else if(prim.isString()){
			                return prim.getAsString();
			            }else if(prim.isNumber()){
			                Number num = prim.getAsNumber();
			                if(Math.ceil(num.doubleValue())  == num.longValue())
			                   return num.longValue();
			                else{
			                    return num.doubleValue();
			                }
			            }
			        }
			        return null;
			    }
		    });

		Gson gson = builder.create();

		return gson.fromJson( json, new TypeToken<Map<String, Object>>() {}.getType());
	}

	private HttpClient getHttpClient( String url ) throws Exception {
		if (httpClient == null) {
			synchronized (this) {
				if (httpClient == null)
					httpClient = createHttpClient( url );
			}
		}
		return httpClient;
	}


	/**
	 * This will create a keep alive strategy used to persist a established
	 * connection. Default : 5 second
	 */
	public ConnectionKeepAliveStrategy getKeepAliveStartegy() {

		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response,
					HttpContext context) {
				return keepAlive;
			}
		};
		return myStrategy;
	}

	/**
	 * Set the SSLContext on the basis of mutualAuthentication.If mutual
	 * authentication is required,it will set the respective client keystore and
	 * trust store to be sent to server during SSL Handshaking.
	 */
	public SSLConnectionSocketFactory getSSLConnectionSocketFactory()
			throws Exception {

		final SSLContextBuilder builder = new SSLContextBuilder();
		if ( mutualAuthenticationRequired ) {
			KeyStore clientKeyStore = KeyStore.getInstance( keyStoreType );
			clientKeyStore.load(
					new FileInputStream( keyStoreFile ),
					keyStorePassword.toCharArray());
			builder.loadKeyMaterial(clientKeyStore, keyStorePassword.toCharArray());
		}
		SSLContext sslContext = builder.build();
		// Install the all-trusting trust manager
		sslContext.init(null, getTrustAllCertsManager(),
				new java.security.SecureRandom());
		final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		sslsf.createSocket(null);
		return sslsf;
	}

	private TrustManager[] getTrustAllCertsManager() {
		// creating trust all Certificate TM
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		return trustAllCerts;
	}

	/**
	 * Creates a Pooled connection Manager to be used by HTTP Client during
	 * connection establishment.You can also set the number of max connection
	 * per route here though connPoolControl.
	 */
	public PoolingHttpClientConnectionManager getConnectionManager(
			SSLConnectionSocketFactory sslsf) {

		final PoolingHttpClientConnectionManager poolingmgr = new PoolingHttpClientConnectionManager(
				RegistryBuilder
						.<ConnectionSocketFactory> create()
						.register("http",
								PlainConnectionSocketFactory.getSocketFactory())
						.register("https", sslsf).build());

		poolingmgr.setMaxTotal( maxConnTotal );
		poolingmgr.setDefaultMaxPerRoute( maxConnPerRoute );

		return poolingmgr;
	}

	private HttpClient createHttpClient( String url ) throws Exception {
		HttpClientBuilder builder;

		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(apiTimeout)
				.setConnectionRequestTimeout(apiTimeout)
				.setSocketTimeout(apiTimeout).build();
		
		if ( url.startsWith( HTTPS ) ) {
			builder = HttpClientBuilder
					.create()
					.useSystemProperties()
					.setKeepAliveStrategy(getKeepAliveStartegy())
					.setConnectionManager(
							getConnectionManager(getSSLConnectionSocketFactory())).setRedirectStrategy(new LaxRedirectStrategy());
		} else {
			builder = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy());
		}
		return builder.setDefaultRequestConfig(config).build();
	}

	private StringEntity createStringEntity( Map<String, Object> parameters ) throws Exception {
		JSONObject keyArg = new JSONObject( parameters );
		return new StringEntity(keyArg.toString());
	}

	private String createURL(String url, Map<String, Object> params) {

		if (params == null) {
			return url;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> pEntry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(pEntry.getKey(), pEntry.getValue().toString()));
		}

		builder.append('?').append(
				URLEncodedUtils.format(nvps, CONTENT_ENCODING_UTF_8));
		return builder.toString();
	}

	private String parseUrl( String url, Map<String, Object> params ) {
		Pattern pattern = Pattern.compile("\\{.*?\\}");
		Matcher matcher = pattern.matcher( url );
		while( matcher.find() ) {
			String pathVariable = matcher.group();
			pathVariable = pathVariable.substring(1, pathVariable.length()-1);
			url = url.replace( "{" + pathVariable + "}", ( String )params.get( pathVariable ) );
		}
		return url;
	}
}
