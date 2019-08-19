package com.snapdeal.opspanel.csv.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.snapdeal.opspanel.csv.request.LIstToCSVRequest;
import com.snapdeal.opspanel.csv.service.CSVServices;

@Slf4j
@Service("csvServicesImpl")
public class CSVServicesImpl implements CSVServices{

	public String DELIMITER = ",";

	public static final String LINE_BREAKER = "\r\n";
	
	public static final String REGEX = ",|\n|\r";

	@Override
	public StringBuffer getListToCSV(LIstToCSVRequest request, String delim) {
		Class className = request.getClassName();
		
		if(delim != null && delim != ""){
			DELIMITER = delim;
		}
		Field [] fields = className.getDeclaredFields();
		int fieldSize = fields.length;
		List<String> headers = new ArrayList<String>();

		StringBuffer sb = new StringBuffer();

		for(int i=0; i<fieldSize; i++){
			if(fields[i] != null){
				headers.add(fields[i].getName());
			}
		}
		sb.append(Joiner.on(DELIMITER).join(headers));
		sb.append(LINE_BREAKER);

		List<Object> content = request.getObjects();
		int numberOfRows = content.size();
		if(numberOfRows != 0){
			if(!className.isInstance(content.get(0))){
				return sb;
			}
		}
		List<String> row = new ArrayList<String>();
		
		for(int i=0; i<numberOfRows; i++){
			if(content.get(i) != null){
				Field [] fieldz = content.get(i).getClass().getDeclaredFields();
				int fieldzSize = fieldz.length;
				for(int j=0; j<fieldzSize; j++){
					String response = "";
					Field field = fieldz[j];
					try {
						field = content.get(i).getClass().getDeclaredField(fields[j].getName());
					} catch (Exception e) {
						response = "Cound not fetch field";
					}
					if (field != null) {
						try {
							Object cell = PropertyUtils.getProperty(content.get(i), field.getName());
							if (cell != null) {
								response = cell.toString();
							} else {
								response = "";
							}
						} catch (Exception e) {
							
							log.info("Getter not found for field name "
									+ field.getName());
						}
					}
					Pattern p = Pattern.compile(REGEX);

					Matcher m = p.matcher(response);
					row.add(m.replaceAll(" "));
				}
				
			}
			sb.append(Joiner.on(DELIMITER).join(row));
			sb.append(LINE_BREAKER);
			row.clear();
		}
		return sb;
	}

}
