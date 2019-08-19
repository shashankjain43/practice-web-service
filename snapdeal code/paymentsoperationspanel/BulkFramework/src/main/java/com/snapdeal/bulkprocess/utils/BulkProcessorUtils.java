package com.snapdeal.bulkprocess.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.bulkprocess.factory.BulkRegistrationFactory;

import lombok.Data;

@Data
public class BulkProcessorUtils {

	@Autowired
	private BulkRegistrationFactory bulkRegistrationFactory;

	public	static final String ASYNC_ERROR_MSG=" Exception recieved from processor, Kindly handle it!";
	
	public	static final String EXECUTER_REJECTED="Executer Rejected Rejected Task";
	
	public	static final String NULL_RESPONSE_MSG = "Null Response recieved, please do handle it!";
	
	public static final String DELIMITER = ",";
	
	public static final String NEW_LINE = "\n";
	
	public static final String REGEX = ",|\n|\r";
	
	public static final String ACTIVITY_ID_NOT_FOUND = "activityid not found in form data, please  provide activityid";
	
	public static final String NEW_FILE_RECIEVED = "\n\n\n\n NEW FILE RECIEVED :";
	
	public static final String INPUT = "input";
	
	public static final String OUTPUT = "output";
	
	public static final String FILE_NAME = "filename";
	
	public static final String UPLOADED_BY = "uploadedby";
	
	public static final String UPLOADED_TIME = "uploadedtime";
	
	public static final String ACTIVITY_ID = "activityid";
	
	public static final String EXECUTOR_NAME="bulkFrameworkExecutor";
	
	public static final String LOCAL_FILE_NOT_DELETED="Alert!! : Local files not deleted ! \n ";

	public static final String NON_RECOVERABLE_EXCEPTION="non recoverable exception in main executor";
	
	public static final String NOT_ABLE_TO_ACCESS="Not able to access this field, All fields of the processor response must be public";

	public static final String REGISTRATION_NOT_FOUND="Cound not get registation for this bulk operations";
	
	public static final String SUCCESSFUL_UPLOAD="You have successfully uploaded ";
	
	public static final String S3_FAIL_MSG="Error while uploading the input file to Amazon s3 : ";

	public static final String FILE_NOT_CSV_MSG="Sorry The file is not a CSV file";
	
	public static final String FILE_SIZE_LIMIT_MSG="Sorry The file size is greater than limit ";
	
	public static final String ERROR_OCCURED="Error Occured ";
	
	public static final String GENERIC_TASK_TYPE = "GENERIC_BULK_TASK";
	
	public static final String EXECUTER_NOT_FOUND = "Unable to get Thread Pool Executer from registration ";
	
	public static final String PROCESSOR_NOT_FOUND = "Unable to get Processor from registration ";
	
	public static final String PRE_START_CHECKS_FAILED = "PreStart Checks failed for this activityId";
	
	public static final String AUTHORIZATION_FAILED = "User doesn't have permission for this action";
	
	public static final String  REJECTED_EXECUTION = "Executor rejected the task";
	
	public static final String UPLOAD = "UPLOAD";
	
	public static final String LIST_PAGE = "LIST_PAGE";
	
	public static final String LIST_PAGE_FOR_SUPERUSER = "LIST_PAGE_FOR_SUPERUSER";
	
	public static final String DOWNLOAD_FILE = "DOWNLOAD_FILE";
	
	
	
	
	public static final int RETRY_COUNT = 3;
}
