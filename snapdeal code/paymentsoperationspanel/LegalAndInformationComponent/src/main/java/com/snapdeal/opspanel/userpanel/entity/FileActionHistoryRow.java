package com.snapdeal.opspanel.userpanel.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FileActionHistoryRow implements Comparable<FileActionHistoryRow>{

	private String userName;
	private String fileName;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z", timezone="IST" )
	private Date fileUploadDate;

	private long fileUploadTime;
	private String Action;
	private String userIdType;
	private String reason;
	private String otherReason;

	private boolean hasOutputFile;

	@Override
	public int compareTo( FileActionHistoryRow fileActionHistoryRow ) {
		if( fileUploadDate != null ) {
			if( fileActionHistoryRow.getFileUploadDate() != null) {
				return fileActionHistoryRow.getFileUploadDate().compareTo(fileUploadDate);
			}
			return -1;
		}
		return 1;
	}
}
