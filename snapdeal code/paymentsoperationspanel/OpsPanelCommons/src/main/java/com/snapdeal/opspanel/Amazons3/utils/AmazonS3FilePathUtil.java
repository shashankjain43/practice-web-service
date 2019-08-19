package com.snapdeal.opspanel.Amazons3.utils;

import org.springframework.stereotype.Service;

@Service
public class AmazonS3FilePathUtil {

	public static String createAmazonS3FilePath( String userId, String fileName, String destinationprefix ) {
	    return getAmazonS3UserPrefix( userId ,destinationprefix) + fileName;
	 }

	public static String getAmazonS3UserPrefix( String userId,String destinationprefix ) {
		return destinationprefix + userId + "/";
	 }
	
	
}





