package com.snapdeal.ums.core.dto;

import java.io.Serializable;

/**
 * @version 1.0, 18-Apr-2015
 * @author Shashank Jain<jain.shashank@snapdeal.com>
 */
public class S3FileDTO implements Serializable{
	
	private static final long serialVersionUID = 1865796352690527136L;
	
	private String fileName;
	
	private byte[] content;

	public S3FileDTO() {
		super();
	}

	public S3FileDTO(String fileName, byte[] content) {
		super();
		this.fileName = fileName;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
