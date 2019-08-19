package com.snapdeal.merchant.request.coverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source)  {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Date date = null;
		try {
			date = dateFormatter.parse(source);
		} catch (ParseException e) {
			log.error("date format error {} {}",e.getMessage(),e);
		}
		return date;
	}
	
}
