package com.snapdeal.bulkprocess.entity;



import java.util.Date;

import lombok.Data;
@Data
public class FileTrackEntity {
	
public String fileId;
public String status;
public Date uploadTime;
public Long totalRows;
public Long completedRows;
public String userId;
public Date lastExecutedTime;

}
