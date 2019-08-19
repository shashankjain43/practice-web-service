package com.snapdeal.ims.utils;

import lombok.extern.slf4j.Slf4j;

import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.enums.EnvironmentEnum;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.ServiceException;

@Slf4j
public class ClientDetails {

   public static final ClientDetails instance = new ClientDetails();
   private String port;
   private String clientKey;
   private String IP;
   private String url;
   private String clientId;
   private int apiTimeOut;

   public int getApiTimeOut() {
		return apiTimeOut;
   }

   public void setApiTimeOut(int apiTimeOut) {
		this.apiTimeOut = apiTimeOut;
   }

   private ClientDetails() {
   }

   public static ClientDetails getInstance() {
      return instance;
   }

   /**
	 * Used to initially configure the ip , port , clientKey and clientId for
	 * each client before calling rest API. Default value is provided in case
	 * don't want to override the timeout time
	 */
	public static void init(String IP, String port, String clientKey,
			String clientId) throws Exception {
		init(IP, port, clientKey, clientId, ClientConstants.TIMEOUT_TIME);
	}


   /**
    * Used to initially configure the ip , port , clientKey and clientId for each
    * client before calling rest API.
    */
   public static void init(String IP,
                           String port,
                           String clientKey,
                           String clientId,                          
                           int apiTimeOut) 
      throws Exception {
      
      if (IP == null || port == null || clientKey == null) {
         throw new Exception("All parameters are mandatory not null.");
      }
      
      instance.IP = IP;
      instance.port = port;
      instance.clientKey = clientKey;
      instance.clientId = clientId;
      instance.setApiTimeOut(apiTimeOut);
      
      final HttpUtil util = HttpUtil.getInstance();

		if (util.getEnvironment() == EnvironmentEnum.DEVELOPMENT
				|| util.getEnvironment() == EnvironmentEnum.PRODUCTION
				|| (util.getEnvironment() == EnvironmentEnum.TESTING 
				&& util.getIsSecure())) 
		{
			instance.url = "https://" + ClientDetails.getInstance().getIP()
					+ ":" + ClientDetails.getInstance().getPort()
					+ RestURIConstants.BASE_URI;
		} else {
			instance.url = "http://" + ClientDetails.getInstance().getIP()
					+ ":" + ClientDetails.getInstance().getPort()
					+ RestURIConstants.BASE_URI;
		}
		// Checking the log level as a part of optimization, not to be confused
		// with log4j properties.
		if (log.isTraceEnabled()) {
			log.trace("Client Initialized successful.");
			log.trace("Client Version:" + util.getVersion());
			log.trace("Client Environment:" + util.getEnvironment());
			log.trace("Client is secure environment: " + util.getIsSecure());
			log.trace("Client IP: " + instance.IP + ":" + instance.port);
		}
	}

	public String getPort() throws Exception {
		validate(port);
		return port;
	}

	public String getClientId() {
		return clientId;
	}

	public String getUrl() {
		return url;
	}

	public String getIP() throws Exception {
		validate(IP);
		return IP;
	}

	public String getClientKey() throws ServiceException {
		validate(clientKey);
		return clientKey;
	}

	private void validate(String param) throws ServiceException {
		if (param == null)
			throw new ServiceException(
					"Client details are not initialized. Please initialize client before calling any API.",
					IMSDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
	}
}
