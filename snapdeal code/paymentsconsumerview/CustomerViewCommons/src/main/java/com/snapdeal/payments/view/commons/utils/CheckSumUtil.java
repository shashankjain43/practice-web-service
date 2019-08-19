package com.snapdeal.payments.view.commons.utils;

import java.security.MessageDigest;

import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;

public class CheckSumUtil {
   
   public static String generateChecksum(Object request, String key) throws PaymentsViewServiceException {

      String text = JsonUtils.serialize(request);
      if(null == text){
    	  return null;
      }
      String plainText = text.concat(key);
      MessageDigest md;

      try {
         md = MessageDigest.getInstance(PaymentsViewConstants.ALGO);
      } catch (Exception e) {
         throw new PaymentsViewServiceException(e.getMessage(),
        		 PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
      }

      try {
         md.update(plainText.getBytes(PaymentsViewConstants.ENCODING));
      } catch (Exception e) {
         throw new PaymentsViewServiceException(e.getMessage(),
        		 PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
      }

      byte[] mdbytes = md.digest();

      // convert the byte to hex format method 1
      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < mdbytes.length; i++) {
         sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString();
   }

   
   public static boolean isValidChecksum(String checksum, Object response, String key) throws PaymentsViewServiceException {
      return checksum.equals(generateChecksum(response, key));
   }

}
