package com.snapdeal.opspanel.restclient;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ClientConfig {

	int apiTimeout;
	String baseUrl;
	Map<String,String> commonHeaders;

}
