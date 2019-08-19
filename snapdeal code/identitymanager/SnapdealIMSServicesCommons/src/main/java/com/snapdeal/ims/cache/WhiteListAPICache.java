package com.snapdeal.ims.cache;


@Cache(name = "WhiteListAPICache")
public class WhiteListAPICache extends AbstractCache<String, Boolean> {

   public void put(String clientId, String apiUri, String apiHTTPMethod, Boolean value) {
      if (clientId != null && apiUri != null && apiHTTPMethod != null) {
         String key = clientId + "_" + apiHTTPMethod + "_" + apiUri;
         super.put(key, value);
      }
   }

   public Boolean get(String clientId, String apiUri, String apiHTTPMethod) {
      if (clientId != null && apiUri != null && apiHTTPMethod != null) {
         String key = clientId + "_" + apiHTTPMethod + "_" + apiUri;
         Boolean value = super.get(key);
            return value;
      }
      return null;
   }

}
