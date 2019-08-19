package com.snapdeal.opspanel.csv.request;

import java.util.List;

import lombok.Data;
@Data
public class LIstToCSVRequest {
	
	private Class<?> className;
	
	private List<Object> objects;

}
