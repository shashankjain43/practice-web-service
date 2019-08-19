package com.snapdeal.bulkprocess.registration;

import lombok.Data;

@Data
public class AmazonConfigStore {

	public AmazonConfigStore(String bucketName, String destinationPrefix,
			String accessKeyId, String secretAccessKey,
			long downloadUrlExpirationTime, String localDownloadPath) {
		super();
		this.bucketName = bucketName;
		this.destinationPrefix = destinationPrefix;
		this.accessKeyId = accessKeyId;
		this.secretAccessKey = secretAccessKey;
		this.downloadUrlExpirationTime = downloadUrlExpirationTime;
		this.localDownloadPath = localDownloadPath;
	}
	
	public AmazonConfigStore(String accessKeyId, String secretAccessKey) {
		super();
		this.accessKeyId = accessKeyId;
		this.secretAccessKey = secretAccessKey;
	}


	private String bucketName;

	private String destinationPrefix;

	private String accessKeyId;

	private String secretAccessKey;

	private long downloadUrlExpirationTime;

	private String localDownloadPath;

}
