package com.snapdeal.ims.test.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;

public class OtpDashboardTest {

	public static void main(String[] args) {
		System.out.println("Enter the value which you wish to encode here :");

		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String value = bufferRead.readLine();
			String encodedValue=Base64.encodeBase64String(value.getBytes());
			System.out.println("Encoded Value is "+encodedValue);
			String decodedValue = new String(Base64.decodeBase64(encodedValue.getBytes()));
			System.out.println("decoded Value is "+decodedValue);

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}



	}

}


