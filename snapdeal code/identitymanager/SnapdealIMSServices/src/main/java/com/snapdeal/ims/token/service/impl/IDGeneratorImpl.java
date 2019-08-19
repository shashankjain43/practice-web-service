package com.snapdeal.ims.token.service.impl;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.token.service.IIDGenerator;

import org.apache.commons.codec.binary.Base64;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * IdGenerator implementation.<br/>
 * TODO: Need to define proper logic for ID generation.
 */
@Component
public class IDGeneratorImpl implements IIDGenerator {

   @Override
   public String generateGlobalTokenId() {
      String uuid = UUID.randomUUID().toString();
      boolean newGTokenGeneration = Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.ENABLE_NEW_G_TOKEN_GENERATION));
      if (newGTokenGeneration) {
         return uuid + System.currentTimeMillis();
      }
      return uuid;
   }

   @Override
   public String generateUserId() {
      String uuid = UUID.randomUUID().toString();
      uuid = ServiceCommonConstants.VersionOne + "#" + uuid;
      return new String(Base64.encodeBase64URLSafeString(uuid.getBytes()));
   }
}
