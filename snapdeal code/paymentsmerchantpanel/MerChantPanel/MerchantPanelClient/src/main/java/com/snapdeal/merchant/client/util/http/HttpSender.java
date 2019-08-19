package com.snapdeal.merchant.client.util.http;

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
/*import org.json.JSONException;
import org.json.JSONObject;*/


import com.google.gson.Gson;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.ClientRequestHeaders;

/**
 * This class provides Implementation of Http Methods namely GET, POST, PUT,
 * DELETE
 */

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
                                  Map<String, String> headers)
      throws HttpTransportException {

      HttpGet httpGet = new HttpGet(createURL(url, params));
      setHeaders(httpGet, headers);

      try {
         HttpClient httpClient = getHttpClient();
         
         HttpResponse response = httpClient.execute(httpGet);
         return response;
      } catch (Exception e) {
         throw new HttpTransportException("Unable to execute http get", e);
      }
   }

   public HttpResponse executePost(String url,
                                   Map<String, String> params,
                                   Map<String, String> headers)
      throws HttpTransportException {

      HttpPost httpPost = new HttpPost(url);
      setHeaders(httpPost, headers);
      
      try {
	     if (params != null) {
	         httpPost.setEntity(createStringEntity(params));
	      } 
         HttpClient httpClient = getHttpClient();
         HttpResponse response = httpClient.execute(httpPost);
         return response;
      } catch (Exception e) {
         throw new HttpTransportException("Unable to execute http post", e);
      }
   }

   public HttpResponse executePut(String url,
                                  Map<String, String> params,
                                  Map<String, String> headers)
      throws HttpTransportException {

      HttpPut httpPut = new HttpPut(url);
      setHeaders(httpPut, headers);

      try {
    	 if (params != null) {
	         httpPut.setEntity(createStringEntity(params));
	      } 
         HttpClient httpClient = getHttpClient();
         HttpResponse response = httpClient.execute(httpPut);
         return response;
      } catch (Exception e) {
         throw new HttpTransportException("Unable to execute http put", e);
      }
   }

   public HttpResponse executeDelete(String url,
                                     Map<String, String> params,
                                     Map<String, String> headers)
      throws HttpTransportException {
      
      HttpDelete httpDelete = new HttpDelete(createURL(url, params));
      setHeaders(httpDelete, headers);

      try {
         HttpClient httpClient = getHttpClient();
         HttpResponse response = httpClient.execute(httpDelete);
         return response;
      } catch (Exception e) {
         throw new HttpTransportException("Unable to execute http delete", e);
      }
   }

   private HttpEntity createStringEntity(Map<String, String> params) throws UnsupportedEncodingException {

      StringEntity input = null;
      Gson gson = new Gson();
      String json = gson.toJson(params);
      input = new StringEntity(json);
      return input;
   }

   private String createURL(String url, Map<String, String> params) {

      if (params == null) {
         return url;
      }
         
      StringBuilder builder = new StringBuilder();
      builder.append(url);

      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      for (Map.Entry<String, String> pEntry : params.entrySet()) {
    	  if(!ClientRequestHeaders.token.equals(pEntry.getKey()) ||
    		  !ClientRequestHeaders.merchantId.equals(pEntry.getKey())
    			  ){
    		  nvps.add(new BasicNameValuePair(pEntry.getKey().toString(), pEntry.getValue().toString()));
    	  }
         
      }

      builder.append('?').append(URLEncodedUtils.format(nvps, CONTENT_ENCODING_UTF_8));
      return builder.toString();
   }

   private void setHeaders(HttpRequestBase httpObject, Map<String, String> headers) {
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
      throws KeyStoreException,
             NoSuchAlgorithmException,
             CertificateException,
             FileNotFoundException,
             IOException,
             UnrecoverableKeyException,
             KeyManagementException {
      
      final SSLContextBuilder builder = new SSLContextBuilder();
      if (httpConfig.getMutualAuthenticationRequired()) {
         
         KeyStore clientKeyStore = KeyStore.getInstance(httpConfig.getKeyStoreType());
         
         clientKeyStore.load(new FileInputStream(httpConfig.getClientKeystoreFile()),
                                                 httpConfig.getClientKeystorePassword().toCharArray());
         
         builder.loadKeyMaterial(clientKeyStore,
                                 httpConfig.getClientKeystorePassword().toCharArray());
      }
      final SSLConnectionSocketFactory sslsf = 
         new SSLConnectionSocketFactory(builder.build(),
                                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      sslsf.createSocket(null);

      return sslsf;
   }

   /**
    * Creates a Pooled connection Manager to be used by HTTP Client during
    * connection establishment.You can also set the number of max connection
    * per route here though connPoolControl.
    */
   public PoolingHttpClientConnectionManager getConnectionManager(SSLConnectionSocketFactory sslsf) {
      
      final PoolingHttpClientConnectionManager poolingmgr = 
         new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory> create()
                                                   .register("http", PlainConnectionSocketFactory.getSocketFactory())
                                                   .register("https", sslsf)
                                                   .build());
      
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
         
         public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            return httpConfig.getKeepAlive();
         }
      };
      return myStrategy;
   }

   private HttpClient createHttpClient(int apiTimeout) 
      throws UnrecoverableKeyException,
             KeyManagementException,
             KeyStoreException,
             NoSuchAlgorithmException,
             CertificateException,
             FileNotFoundException,
             IOException {
      
      HttpClientBuilder builder;
      RequestConfig config = RequestConfig.custom()
              .setConnectTimeout(apiTimeout)
              .setConnectionRequestTimeout(apiTimeout)
              .setSocketTimeout(apiTimeout).build();
      if (ClientDetails.getInstance().getUrl().startsWith(ClientConstants.HTTPS)) {
         builder = HttpClientBuilder.create().useSystemProperties()
                  .setKeepAliveStrategy(getKeepAliveStartegy())
                  .setConnectionManager(getConnectionManager(getSSLConnectionSocketFactory()));
      } else {
         builder = HttpClientBuilder.create();
      }
      return builder.setDefaultRequestConfig(config).build();
   }

   public HttpClient getHttpClient() 
      throws UnrecoverableKeyException,
             KeyManagementException,
             KeyStoreException,
             NoSuchAlgorithmException,
             CertificateException,
             FileNotFoundException,
             IOException {
      
      if (httpClient == null) {
         synchronized (this) {
            if (httpClient == null)
               httpClient = createHttpClient(ClientDetails.instance.getApiTimeOut());
         }
      }
      return httpClient;
   }

}
