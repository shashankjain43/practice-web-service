package com.snapdeal.opspanel.csv.service;

import com.snapdeal.opspanel.csv.request.LIstToCSVRequest;

public interface CSVServices {
	
	public StringBuffer getListToCSV(LIstToCSVRequest request, String delim);

}
