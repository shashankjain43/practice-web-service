package com.snapdeal.merchant.dao.entity;

import java.util.Date;

import lombok.Data;

@Data
public class FilterEntity {
	
	private int id;
	
	private String filterHash;
	
	private String filterMetaData;
	
	private Date createdOn;
}
