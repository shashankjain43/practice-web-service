package com.snapdeal.bulkprocess.registration;

import java.util.Map;
import java.util.concurrent.Executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.snapdeal.opspanel.Amazons3.utils.GenericAmazonS3Utils;

import lombok.Data;

@Data
public abstract class IBulkFileRegistration {

	private GenericAmazonS3Utils amazonS3;

	protected final String ROOTPREFIX="/BulkFramework/";
	protected final String INPUTPREFIX="input/";
	protected final String OUTPUTPREFIX="output/";
	
	public void onStart() {
    
	}
	
	public long getChunkSize() {
		return 50000l;
	}

	public int getMAXFileSizeInMB() {
		return 200;
	}

	public abstract AmazonConfigStore getS3Config();

	public abstract String getBulkActivityId();

	public abstract String getUserId();

	public int getThreadPoolSize() {
		return 40;
	}

	public Object getSharedObject(){
		return null;
	}

	public abstract ThreadPoolTaskExecutor getExecutor();
	
	public abstract String getLocalDir();

	public abstract IRowProcessor getProcessor();

	public abstract IBulkValidator getValidator();

	public String getListingPathForUserAndActivity(String userId, String activityId, boolean isInputFile) {
		if(isInputFile){
			return ROOTPREFIX + INPUTPREFIX  + getBulkActivityId() + "/" + userId;
		} else {
			return  ROOTPREFIX + OUTPUTPREFIX  + getBulkActivityId() + "/" + userId;
		}

	}

	public String getDestinationS3PathForInputFile(String fileName,String userId){
		return  ROOTPREFIX + INPUTPREFIX  + getBulkActivityId() + "/" + userId + "/" + fileName;
	}

	public String getDestinationS3PathForOutputFile(String fileName,String userId){
		return  ROOTPREFIX + OUTPUTPREFIX  + getBulkActivityId() + "/" + userId + "/" + fileName;
	}

	public String getListingPathForSuperUser(String activityId, boolean isInputFile){
		if(isInputFile){
			return ROOTPREFIX + INPUTPREFIX  + activityId;
		} else {
			return  ROOTPREFIX + OUTPUTPREFIX  + activityId;
		}
	}
	
	public String getOutputFileNameForInput(String inputFileName){
		return "output_" + inputFileName;
	}
	
	public String getSampleFilePath(Map<String,String> uiData){
		return null;
	}

}
