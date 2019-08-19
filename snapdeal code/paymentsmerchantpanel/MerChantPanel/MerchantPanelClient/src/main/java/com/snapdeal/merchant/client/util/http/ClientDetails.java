package com.snapdeal.merchant.client.util.http;

import com.snapdeal.merchant.client.util.RestURIConstants;


public class ClientDetails {

   public static final ClientDetails instance = new ClientDetails();
   private String port;
   private String IP;
   private String url;
   private Integer apiTimeOut;
   
   private ClientDetails() {
   }

   public static ClientDetails getInstance() {
      return instance;
   }

   /**
    * Used to initially configure the ip , port  for each
    * client before calling rest API.
    */
   public static void init(String IP,String port, Integer  apiTimeOut) 
      throws Exception {
      
      if (IP == null || port == null) {
         throw new Exception("All parameters are mandatory not null.");
      }
      
      instance.IP = IP;
      instance.port = port;
      instance.apiTimeOut = apiTimeOut;
      
      final HttpUtil util = HttpUtil.getInstance();
      
      if (util.getEnvironment().equals(EnvironmentEnum.DEVELOPMENT)
               || util.getEnvironment().equals(EnvironmentEnum.PRODUCTION)
               || (util.getEnvironment().equals(EnvironmentEnum.TESTING) && util.getIsSecure())) {
         
         instance.url = "https://" + ClientDetails.getInstance().getIP() + ":"
                  + ClientDetails.getInstance().getPort() + RestURIConstants.BASE_URI;
      } else {
         instance.url = "http://" + ClientDetails.getInstance().getIP() + ":"
                  + ClientDetails.getInstance().getPort() + RestURIConstants.BASE_URI;
      }
   }

   public Integer getApiTimeOut() {
	return apiTimeOut;
}

public String getPort() throws Exception {
      validate(port);
      return port;
   }

   public String getUrl() {
      return url;
   }

   public String getIP() throws Exception {
      validate(IP);
      return IP;
   }

   private void validate(String param) throws Exception {
      if (param == null)
         throw new Exception("Client details are not initialized. Please initialize client before calling any API.");
   }

}