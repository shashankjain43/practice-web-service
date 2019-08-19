package com.snapdeal.opspanel.Amazons3.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FileListingResponse implements Comparable<FileListingResponse> {

	private String email;
	private String merchantId;
	private String merchantName;
	private String fileName;
	private String refundKey;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z", timezone = "IST")
	private Date uploadTime;
	private boolean hasOutput;

	@Override
	public int compareTo(FileListingResponse fileListingResponse) {
		if (uploadTime != null) {
			if (fileListingResponse.getUploadTime() != null) {
				return fileListingResponse.getUploadTime().compareTo(uploadTime);
			}
			return -1;
		}
		return 1;

	}

}
