package com.snapdeal.ims.service.provider;

import com.freecharge.umsclient.AbstractUmsConfig;
import com.freecharge.umsclient.Ums;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FCUmsProvider {

	private Ums ums;
	
	@Value("${fc.ums.url}")
	private String url;
	
	@Value("${freecharge.ums.apiTimeOut}")
	private Integer apiTimeOut;
	
	@Value("${freecharge.ums.socketTimeout}")
	private Integer socketTimeout;
	
	@Value("${freecharge.ums.connectionRequestTimeout}")
	private Integer connectionRequestTimeout;
	
	public Ums getUms() {
		if (ums == null) {
			synchronized (this) {
				if (ums == null) {
				   AbstractUmsConfig config = new AbstractUmsConfig() {
                  
				      private String umsHost;
                  @Override
                  public void setUmsHost(String umsHost) {
                     this.umsHost = umsHost;
                  }
                  
                  @Override
                  public String getUmsHost() {
                     // TODO Auto-generated method stub
                     return umsHost;
                  }
               };
					config.setUmsHost(url);
					config.setConnectionRequestTimeout(connectionRequestTimeout);
					config.setConnectTimeout(apiTimeOut);
					config.setSocketTimeout(socketTimeout);
					ums = new Ums();
					ums.init(config);
               log.debug("UMS client Initialized: " + ums);
				}
			}
		}
		return ums;
	}
}
