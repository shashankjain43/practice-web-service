package com.snapdeal.opspanel.Amazons3.response;

import java.util.List;

import lombok.Data;

@Data
public class ListObjectsWithMarker {
	
	private List<String> files;
	
	private String nextMarker;
	

}
