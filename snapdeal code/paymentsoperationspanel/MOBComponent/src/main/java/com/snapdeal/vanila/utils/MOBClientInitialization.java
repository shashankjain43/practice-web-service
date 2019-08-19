package com.snapdeal.vanila.utils;

import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.impl.MOBServicesClient;
import com.snapdeal.mob.utils.ClientDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MOBClientInitialization<Context extends Enum<Context>> {

   ClientDetails clientDetails = null;
   @Value("${mob.clientName}")
   private String client;
   @Value("${mob.IP}")
   private String IP;
   @Value("${mob.Port}")
   private String port;

   /*
    * @Value("${mob.clientKey}")
    * private String clientKey;
    * 
    * @Value("${mob.clientName}")
    * private String clientName;
    */

   @Bean
   @Scope
   public IMerchantServices initMOB() throws Exception {
      ClientDetails.init(client, IP, port,12000);
      return new MOBServicesClient();
   }

}
