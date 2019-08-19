package com.snapdeal.ims.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.snapdeal.ims.entity.UserSearchEnteredEntity;

public class UserSearchUtil {

	
	public static UserSearchEnteredEntity userSearchFilter(String encodeduserId,String encodedname,String encodedemail,String encodedmobile,String encodedfromDate,String encodedtoDate) throws ParseException{
		UserSearchEnteredEntity userEnteredValue = new UserSearchEnteredEntity();
		String userId = new String(
				Base64.decodeBase64(encodeduserId.getBytes()));
		String name = new String(Base64.decodeBase64(encodedname.getBytes()));
		String email = new String(Base64.decodeBase64(encodedemail.getBytes()));
		String mobile = new String(
				Base64.decodeBase64(encodedmobile.getBytes()));
		String fromDate = new String(Base64.decodeBase64(encodedfromDate
				.getBytes()));
		String toDate = new String(
				Base64.decodeBase64(encodedtoDate.getBytes()));
		if(userId.isEmpty()){
			userId=null;
			userEnteredValue.setSdfc_user_id(-1);
		}
		else{
			if(isNumeric(userId)){
				userEnteredValue.setSdfc_user_id(Integer.parseInt(userId));
				userEnteredValue.setUserId(null);
			}
			else{
				userEnteredValue.setUserId(userId);
				userEnteredValue.setSdfc_user_id(-1);
			}
		}
		if(email.isEmpty()){
			email=null;
		}
		if(name.isEmpty()){
			name=null;
		}
		if(mobile.isEmpty()){
			mobile=null;
		}

		if (!fromDate.isEmpty() && !toDate.isEmpty() &&  fromDate.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && toDate.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			fromDate = fromDate + " 00:00:00";
			toDate = toDate + " 23:59:59";
			Date fDate = formatter.parse(fromDate);
			Date tDate = formatter.parse(toDate);
			if(fDate.before(tDate) && tDate.before(new Date()) && fDate.before(new Date())){
				userEnteredValue.setFromDate(fDate);
				userEnteredValue.setToDate(tDate);
			}
		}else{
			fromDate=null;
			toDate=null;
		}
		
		userEnteredValue.setName(name);
		userEnteredValue.setMobile(mobile);
		userEnteredValue.setEmail(email);

		return userEnteredValue;
		
	}
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Integer d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
