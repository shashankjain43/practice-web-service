package com.snapdeal.opspanel.Amazons3.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.request.DownloadRefundFileRequest;
import com.snapdeal.opspanel.Amazons3.request.UploadServiceRequest;
import com.snapdeal.opspanel.Amazons3.response.FileListingResponse;

public interface BulkRefundService {

	public String uploadRefundFile(UploadServiceRequest request) throws PaymentsCommonException;

	public List<FileListingResponse> getFileListings(String email) throws PaymentsCommonException;

	public URL downloadRefundFile(DownloadRefundFileRequest request) throws PaymentsCommonException;

	public Map<String, String> getFileMetaData(String email, String fileName) throws PaymentsCommonException;

	public List<FileListingResponse> getAllFileListings() throws PaymentsCommonException;

	public boolean localdownloadFile(DownloadRefundFileRequest request) throws PaymentsCommonException;

	public boolean checkRefundKey(String key) throws PaymentsCommonException;
}
