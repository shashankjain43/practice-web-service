package com.snapdeal.bulkprocess.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class RowTrackEntity {

	public String fieldId;
	public String rowId;
	public String status;
	public Date time;
	public String userId;
	
	
}
