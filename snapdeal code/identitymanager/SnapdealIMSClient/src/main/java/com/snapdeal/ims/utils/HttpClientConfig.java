package com.snapdeal.ims.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration class for each client to maintains the connection configuration
 * required during building http connection. The values will be populated from
 * the properties files directly with the prefix defined in the @ConfigurationProperties
 * while creating mean for RestTemplateFactory.
 */
@Getter
@Setter
@ToString(exclude = { "clientKeystoreFile", "clientKeystorePassword" })
public class HttpClientConfig {

   static HttpClientConfig instance = new HttpClientConfig();

   private HttpClientConfig() {

   }

   public static HttpClientConfig getInstance() {
      return instance;
   }

   /**
    * Timeout(milliseconds) on waiting to read data. Specifically, if the
    * server fails to send a byte <timeout> seconds after the last byte, a read
    * timeout error will be raised
    */
   private Integer readTimeOut = 5000;

   /**
    * Timeout(milliseconds) in making the initial connection; i.e. completing
    * the TCP connection handshake.
    */
   private Integer connectTimeOut = 5000;

   /**
    * Configure the max connection per route.
    */
   private Integer maxConnPerRoute = 50;

   /**
    * Configure the max connection in total.
    */
   private Integer maxConnTotal = 50;

   /**
    * This property specify whether mutual authentication is required and
    * certificates on both side needs to be exchanged. Default is false.
    */
   private Boolean mutualAuthenticationRequired = false;
   /**
    * Specify the keystore type if client authentication is required.Default is
    * jks.
    */
   private String keyStoreType = "jks";

   /**
    * Configure the file location for client keyStore.Specify the full system
    * path as it doesnot understand in trms of classpath.
    */
   private String clientKeystoreFile = "/opt/configuration/clientkeystore.jks";

   /**
    * Configure the password for client Keystore.
    */
   private String clientKeystorePassword = "snapdeal";

   /**
    * Keep alive time requied in keep alive strategy. Default is -1 which mean
    * indefinite time.
    */
   private Long keepAlive = -1L;
}
