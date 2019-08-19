package com.snapdeal.opspanel.userpanel.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.entity.FileActionHistoryRow;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.request.DownloadActionBulkFileRequest;
import com.snapdeal.opspanel.userpanel.service.FileManagementService;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.opspanel.userpanel.utils.InfoPanelS3Utils;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("FileManagementService")
@Slf4j
public class FileManagementServiceImpl implements FileManagementService {

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	private TokenService tokenService;

	@Autowired
	InfoPanelS3Utils amazonUtils;

	private final String EMAIL_ID = "emailId";
	private final String IS_OUTPUT_FILE = "isoutputfile";
	private final String USER_NAME = "username";
	private final String FALSE = "false";
	private final String TRUE = "true";
	private final String TIME_PROCESSED = "timeprocessed";
	private final String ACTION = "action";
	private final String USER_ID_TYPE = "useridtype";
	private final String REASON = "reason";
	private final String OTHER_REASON = "otherreason";

	@Override
	public void pushActionBulkInputFile(ActionBulkRequest actionBulkRequest, Date timeProcessed, String emailId ) throws InfoPanelException {

		//String emailId = getEmailIdFromRms();

		File file = new File(actionBulkRequest.getUploadFilePath());

		Map<String, String> fileMetaData = new HashMap<String, String>();
		fileMetaData.put(IS_OUTPUT_FILE, FALSE);
		fileMetaData.put(USER_NAME, emailId);
		fileMetaData.put(TIME_PROCESSED, "" + timeProcessed.getTime() );
		fileMetaData.put(ACTION, actionBulkRequest.getAction().name() );
		fileMetaData.put(USER_ID_TYPE, actionBulkRequest.getIdType().name() );
		fileMetaData.put(REASON, actionBulkRequest.getReason());
		fileMetaData.put( OTHER_REASON, actionBulkRequest.getOtherReason());
		try {
			amazonUtils.putObject(actionBulkRequest.getUploadFilePath(), createAmazonS3FilePath(emailId, Long.toString( timeProcessed.getTime() ), file.getName()), fileMetaData);
		} catch (Exception e) {
			log.info("Exception while uploading bulk action input file to amazon S3 " + ExceptionUtils.getFullStackTrace(e));
			throw new InfoPanelException("MT-5802", "Exception while uploading file to Amazon S3","AmazonS3");
		}
	}

	@Override
	public void pushActionBulkOutputFile(ActionBulkRequest actionBulkRequest, Date timeProcessed, String emailId ) throws InfoPanelException {

		//String emailId = getEmailIdFromRms();
		String outputFilePath = GenericUtils.getOutputFilePathForCSV(actionBulkRequest.getUploadFilePath());

		File file = new File(outputFilePath);

		Map<String, String> fileMetaData = new HashMap<String, String>();
		fileMetaData.put(IS_OUTPUT_FILE, TRUE);
		fileMetaData.put(USER_NAME, emailId);
		fileMetaData.put(TIME_PROCESSED, timeProcessed.getTime() + "");
		fileMetaData.put(ACTION, actionBulkRequest.getAction().name() );
		fileMetaData.put(USER_ID_TYPE, actionBulkRequest.getIdType().name() );
		fileMetaData.put(REASON, actionBulkRequest.getReason());
		fileMetaData.put( OTHER_REASON, actionBulkRequest.getOtherReason());

		String amazonFilePath = createAmazonS3FilePath(emailId, Long.toString( timeProcessed.getTime() ), file.getName());

		try {

			amazonUtils.putObject(outputFilePath, amazonFilePath, fileMetaData);
			//return amazonUtils.generatePresignedUrl(amazonFilePath);

		} catch (Exception e) {
			log.info("Exception while uploading bulk action input file to amazon S3 " + ExceptionUtils.getFullStackTrace(e));
			throw new InfoPanelException("MT-5802", "Exception while uploading file to Amazon S3","AmazonS3");
		}
	}

	@Override
	public List<FileActionHistoryRow> getFileHistoryForSuperUser() throws InfoPanelException {
		return getFileHistoryWithPrefix( "" );
	}

	@Override
	public List<FileActionHistoryRow> getFileHistoryForUser() throws OpsPanelException, InfoPanelException {

		String emailId = getEmailIdFromRms();

		return getFileHistoryWithPrefix( getUserPrefix( emailId ) );

	}

	@Override
	public URL getDownloadUrl( DownloadActionBulkFileRequest request ) throws InfoPanelException {

		try {
			if( ! request.isOutputFile() ) {
				return amazonUtils.generatePresignedUrl( createAmazonS3FilePath(request.getUserName(), request.getTime(), request.getFileName()) );
			} else {
				return amazonUtils.generatePresignedUrl( createAmazonS3FilePath(request.getUserName(), request.getTime(), GenericUtils.getOutputFilePathForCSV(request.getFileName()) ) );
			}
		} catch( Exception e ) {
			log.info( "Exception while generating presigned url from amazon for request " + request + ExceptionUtils.getFullStackTrace(e) );
			throw new InfoPanelException( "MT-5805", "Could not generate file url from amazon","AmazonS3");
		}

	}

	private String getUserPrefix( String emailId ) {
		return emailId.substring(0, emailId.indexOf("@")) + "/";
	}

	private String createAmazonS3FilePath(String emailId, String timeProcessed, String fileName) {
		return getUserPrefix( emailId ) + timeProcessed + "/" + fileName;
	}

	private List<FileActionHistoryRow> getFileHistoryWithPrefix(String prefix) throws InfoPanelException {

		List<String> fileList = null;
		try {
			fileList = amazonUtils.listFiles( prefix );
		} catch (Exception e) {
			log.info("Exception while getting list of files from amazon for super user " + ExceptionUtils.getFullStackTrace(e));
			throw new InfoPanelException("MT-5803", "Exception while listing files from amazon","AmazonS3");
		}

		List<FileActionHistoryRow> fileActionHistoryList = new ArrayList<FileActionHistoryRow>();

		for (String file : fileList ) {

			FileActionHistoryRow fileActionHistoryRow = new FileActionHistoryRow();
			fileActionHistoryRow.setFileName( file.substring( file.lastIndexOf("/") + 1));

			Map<String, String> fileMetaData = null;
			try {
				fileMetaData = amazonUtils.getFileMetaData( prefix + file );
			} catch (Exception e) {
				log.info(
						"Exception while getting file meta data from amazon for " + file + ExceptionUtils.getFullStackTrace(e));
				throw new InfoPanelException("MT-5804", "Exception while getting file meta data from amazon","AmazonS3");
			}

			fileActionHistoryRow.setUserName(fileMetaData.get(USER_NAME));
			try {
				long fileUploadTime = Long.parseLong( fileMetaData.get(TIME_PROCESSED) );
				fileActionHistoryRow.setFileUploadDate( new Date ( fileUploadTime ) );
				fileActionHistoryRow.setFileUploadTime( fileUploadTime );
			} catch( Exception e ) {
				log.info( "Excception while parsing date from amazon s3 file " + fileMetaData + " " + ExceptionUtils.getFullStackTrace( e ));
			}

			fileActionHistoryRow.setAction( fileMetaData.get(ACTION) );
			fileActionHistoryRow.setUserIdType(fileMetaData.get(USER_ID_TYPE ));
			fileActionHistoryRow.setReason(fileMetaData.get(REASON ));
			fileActionHistoryRow.setOtherReason(fileMetaData.get( OTHER_REASON ));

			if( fileMetaData.get(IS_OUTPUT_FILE) != null && fileMetaData.get(IS_OUTPUT_FILE).equalsIgnoreCase(FALSE)) {
				if( fileList.contains(GenericUtils.getOutputFilePathForCSV(file)) ) {
					fileActionHistoryRow.setHasOutputFile( true );
				} else {
					fileActionHistoryRow.setHasOutputFile( false );
				}
				fileActionHistoryList.add(fileActionHistoryRow);
			}

		}

		Collections.sort( fileActionHistoryList );

		return fileActionHistoryList;

	}

	private String getEmailIdFromRms() throws OpsPanelException {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return emailId;
	}
}