package com.snapdeal.merchant.util;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.snapdeal.merchant.exception.MerchantException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class DateToTimeStampConverter {

public Long dateToTimestampConverter(String time) throws MerchantException {
		
		long epoch = 0  ;
		try {
			
			epoch = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time).getTime();
		} catch (ParseException e) {
			
			log.error("Exception while converting date to timestamp : {} {}", e.getMessage() , e );
			throw new MerchantException(e.getMessage());
		}
		
		return epoch;
	}
}
