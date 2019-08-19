package com.snapdeal.ims.enums;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;

public enum SocialSource {

   FACEBOOK("facebook", "fb"), GOOGLE("google");
	
   private LinkedHashSet<String> value = null;

   private SocialSource(String... value) {
      this.value = new LinkedHashSet<String>(Arrays.asList(value));
   }
	
	public String getValue() {
      return this.value.iterator().next();
	}
	
	public static SocialSource forName(String value) {
      if (StringUtils.isNotBlank(value)) {
         value = value.toLowerCase();
         for (SocialSource eachSrc : values()) {
            if (eachSrc.value.contains(value)) {
               return eachSrc;
            }
         }
      }
      return null;
	}
}