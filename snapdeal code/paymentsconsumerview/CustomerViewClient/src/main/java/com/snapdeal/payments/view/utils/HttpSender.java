package com.snapdeal.payments.view.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;

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
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.snapdeal.payments.view.commons.exception.client.HttpTransportException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;
import com.snapdeal.payments.view.commons.request.ClientConfig;

/**
 * This class provides Implementation of Http Methods namely GET, POST, PUT,
 * DELETE
 */
@Slf4j
public class HttpSender {

	private final String CONTENT_ENCODING_UTF_8 = "UTF-8";

	private HttpClientConfig httpConfig = HttpClientConfig.getInstance();

	HttpClient httpClient;

	private static HttpSender instance = new HttpSender();

	private HttpSender() {
	}

	public static HttpSender getInstance() {
		return instance;
	}

	public HttpResponse executeGet(String url,
								   Map<String, String> params,
								   Map<String, String> headers, 
								   ClientConfig config) throws HttpTransportException {

		HttpGet httpGet = new HttpGet(createURL(url, params));
		setHeaders(httpGet, headers);

		try {

			HttpClient httpClient = getHttpClient(getApiTimeoutFromRequestConfig(config));

			HttpResponse response = httpClient.execute(httpGet);
			return response;
		} catch (Exception e) {
		   log.error("Error in executing http get: " + e.getMessage());
			throw new HttpTransportException("Unable to execute http get",
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
	}

	public HttpResponse executePost(String url, Map<String, String> params,
			Map<String, String> headers, ClientConfig config) throws HttpTransportException {

		HttpPost httpPost = new HttpPost(url);
		setHeaders(httpPost, headers);
		try {
			if (params != null) {
				httpPost.setEntity(createStringEntity(params));
			}
			HttpClient httpClient = getHttpClient(getApiTimeoutFromRequestConfig(config));
			HttpResponse response = httpClient.execute(httpPost);
			return response;
		} catch (Exception e) {
	       log.error("Error in executing http post: " + e.getMessage());
			throw new HttpTransportException("Unable to execute http post",
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
	}

	public HttpResponse executePut(String url, Map<String, String> params,
			Map<String, String> headers, ClientConfig config) throws HttpTransportException {

		HttpPut httpPut = new HttpPut(url);
		setHeaders(httpPut, headers);

		try {
			if (params != null) {
				httpPut.setEntity(createStringEntity(params));
			}
			HttpClient httpClient = getHttpClient(getApiTimeoutFromRequestConfig(config));
			HttpResponse response = httpClient.execute(httpPut);
			return response;
		} catch (Exception e) {
	       log.error("Error in executing http put: " + e.getMessage());
			throw new HttpTransportException("Unable to execute http put",
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
	}

	public HttpResponse executeDelete(String url, Map<String, String> params,
			Map<String, String> headers, ClientConfig config) throws HttpTransportException {

		HttpDelete httpDelete = new HttpDelete(createURL(url, params));
		setHeaders(httpDelete, headers);

		try {
			HttpClient httpClient = getHttpClient(getApiTimeoutFromRequestConfig(config));
			HttpResponse response = httpClient.execute(httpDelete);
			return response;
		} catch (Exception e) {
	       log.error("Error in executing http delete: " + e.getMessage());
			throw new HttpTransportException("Unable to execute http delete",
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
	}

	private HttpEntity createStringEntity(Map<String, String> params)
			throws JSONException, UnsupportedEncodingException {

		JSONObject keyArg = new JSONObject();
		for (Map.Entry<String, String> pEntry : params.entrySet()) {
			keyArg.put(pEntry.getKey(), pEntry.getValue());
		}
		StringEntity input = null;
		input = new StringEntity(keyArg.toString());
		return input;
	}

	private String createURL(String url, Map<String, String> params) {

		if (params == null) {
			return url;
		}

		StringBuilder builder = new StringBuilder();
		builder.append(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Map.Entry<String,String> pEntry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(pEntry.getKey(),String.valueOf(pEntry.getValue()) ));
		}

		builder.append('?').append(
				URLEncodedUtils.format(nvps, CONTENT_ENCODING_UTF_8));
		return builder.toString();
	}

	private void setHeaders(HttpRequestBase httpObject,
			Map<String, String> headers) {
		if (headers != null) {
			for (Map.Entry<String, String> hEntry : headers.entrySet()) {
				httpObject.addHeader(hEntry.getKey(), hEntry.getValue());
			}
		}
	}
	/**
	 * Set the SSLContext on the basis of mutualAuthentication.If mutual
	 * authentication is required,it will set the respective client keystore and
	 * trust store to be sent to server during SSL Handshaking.
	 */
	public SSLConnectionSocketFactory getSSLConnectionSocketFactory()
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException,
			UnrecoverableKeyException, KeyManagementException {

		final SSLContextBuilder builder = new SSLContextBuilder();
		if (httpConfig.getMutualAuthenticationRequired()) {
			KeyStore clientKeyStore = KeyStore.getInstance(httpConfig
					.getKeyStoreType());
			clientKeyStore.load(
					new FileInputStream(httpConfig.getClientKeystoreFile()),
					httpConfig.getClientKeystorePassword().toCharArray());
			builder.loadKeyMaterial(clientKeyStore, httpConfig
					.getClientKeystorePassword().toCharArray());
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

		poolingmgr.setMaxTotal(httpConfig.getMaxConnTotal());
		poolingmgr.setDefaultMaxPerRoute(httpConfig.getMaxConnPerRoute());

		return poolingmgr;
	}

	/**
	 * This will create a keep alive strategy used to persist a established
	 * connection. Default : 5 second
	 */
	public ConnectionKeepAliveStrategy getKeepAliveStartegy() {

		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			public long getKeepAliveDuration(HttpResponse response,
					HttpContext context) {
				return httpConfig.getKeepAlive();
			}
		};
		return myStrategy;
	}


	private HttpClient createHttpClient(int apiTimeout) throws UnrecoverableKeyException,
			KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException {

		HttpClientBuilder builder;

		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(apiTimeout)
				.setConnectionRequestTimeout(apiTimeout)
				.setSocketTimeout(apiTimeout).build();
		
		if (ClientDetails.getInstance().getUrl()
				.startsWith(ClientConstants.HTTPS)) {
			builder = HttpClientBuilder
					.create()
					.useSystemProperties()
					.setKeepAliveStrategy(getKeepAliveStartegy())
					.setConnectionManager(
							getConnectionManager(getSSLConnectionSocketFactory()));
		} else {
			builder = HttpClientBuilder.create();
		}
		return builder.setDefaultRequestConfig(config).build();
	}
	public HttpClient getHttpClient(int apiTimeout) throws UnrecoverableKeyException,
			KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException {

		if (httpClient == null) {
			synchronized (this) {
				if (httpClient == null)
					httpClient = createHttpClient(apiTimeout);
			}
		}
		return httpClient;
	}
	
	private int getApiTimeoutFromRequestConfig(ClientConfig config) {

		if (config == null || config.getApiTimeOut() == 0) {

			return ClientDetails.getInstance().getApiTimeOut();
		}
		return config.getApiTimeOut();
	}
}