package com.snapdeal.bulkprocess.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.factory.BulkRegistrationFactory;
import com.snapdeal.bulkprocess.model.BulkError;
import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;
import com.snapdeal.bulkprocess.model.GenericRequestResponseValues;
import com.snapdeal.bulkprocess.model.UploadRequest;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.bulkprocess.service.AmazonServices;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.entity.TaskExecution;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Component("genericBulkExecuter")
public class BulkMainExecutor implements TaskExecutor<GenericBulkTaskRequest> {

	@Autowired
	TaskScheduler taskScheduler;

	@Autowired
	AmazonServices amazonServices;

	@Autowired
	BulkRowAsyncTask bulkRowAsyncTask;

	@Autowired
	BulkRegistrationFactory bulkRegistrationFactory;


	private static final String BULK_MAIN_EXECUTOR_STEP = "\nBULK_FRAMEWORK_STEP:";

	public ExecutorResponse execute(GenericBulkTaskRequest request) {
		log.info("\npicked up file with filename: "+request.getFileName()+"\n");
		ExecutorResponse executorResponse = new ExecutorResponse();
		IBulkFileRegistration registration = null;
		File downloadedFile=null;

		/*
		 * STEP 1 : Get Registration from activity id
		 * As this is a generic bulk task Activity Id is used to determine which registration
		 * is used in Runtime
		 */

		registration = bulkRegistrationFactory.getIBulkFileRegistrationInfo(request.getActivityId());
		log.info( BULK_MAIN_EXECUTOR_STEP + " getting registration.");

		if (registration == null) {
			executorResponse.setStatus(Status.FAILURE);
			executorResponse.setCompletionLog(BulkProcessorUtils.REGISTRATION_NOT_FOUND);
			log.info( BULK_MAIN_EXECUTOR_STEP + " registration not found.");
			return executorResponse;
		}

		IRowProcessor processor = registration.getProcessor();
		
		if(processor == null){
			log.info("Processor for bulk execution is NULL ... \n \n");
			executorResponse.setStatus(Status.FAILURE);
			executorResponse.setCompletionLog(BulkProcessorUtils.PROCESSOR_NOT_FOUND);
			log.info( BULK_MAIN_EXECUTOR_STEP + " Processor not found.");
			return executorResponse;
		}
		
		ThreadPoolTaskExecutor executor = registration.getExecutor();
		
		if(executor == null){
			log.info("ThreadPoolExecutor for bulk execution is NULL ... \n \n");
			executorResponse.setStatus(Status.FAILURE);
			executorResponse.setCompletionLog(BulkProcessorUtils.EXECUTER_NOT_FOUND);
			log.info( BULK_MAIN_EXECUTOR_STEP + " ThreadPoolExecutor not found.");
			return executorResponse;
		}
		
		// name of the output file on local server disk
		String  OUTFILE_NAME=	registration.getLocalDir() + registration.getOutputFileNameForInput(request.getFileName());
		try {

			

			FileInputStream file = null;
			downloadedFile = new File(registration.getLocalDir() + request.getFileName());
			Boolean fileOpenedSuccessFully = false;
			/*
			 * STEP 2 : open the uploaded file 
			 * check if the uploaded file exists in local server if not
			 * download from amazon s3
			 * if both of this fails return retry, finally fail
			 */

			if (downloadedFile.exists()) {
				log.info( BULK_MAIN_EXECUTOR_STEP + " file exists.");
				try {
					file = new FileInputStream(downloadedFile);
					fileOpenedSuccessFully = true;
				} catch (FileNotFoundException e) {
					log.info( BULK_MAIN_EXECUTOR_STEP + " FileNotFoundException occurred: " + ExceptionUtils.getFullStackTrace( e ) );
					fileOpenedSuccessFully = false;
				}
			}

			if (fileOpenedSuccessFully == Boolean.FALSE) {

				try {
					log.info( BULK_MAIN_EXECUTOR_STEP + " downloading file from amazon.");
					downloadedFile = amazonServices.localDownloadFile(request.getS3Path(), request.getFileName(),request.getActivityId());
					log.info( BULK_MAIN_EXECUTOR_STEP + " successfully downloaded file from amazon.");
					file = new FileInputStream(downloadedFile);
					fileOpenedSuccessFully = Boolean.TRUE;
				} catch (BulkProcessorException | PaymentsCommonException e) {
					log.info( BULK_MAIN_EXECUTOR_STEP + " BulkProcessorExcecption | PaymentsException: " + ExceptionUtils.getFullStackTrace( e ) );
					try {
						log.info( BULK_MAIN_EXECUTOR_STEP + " retrying amazon download file.");
						downloadedFile = amazonServices.localDownloadFile(request.getS3Path(), request.getFileName(),request.getActivityId());
						log.info( BULK_MAIN_EXECUTOR_STEP + " downloaded successfully this time.");
						file = new FileInputStream(downloadedFile);
						fileOpenedSuccessFully = Boolean.TRUE;
					} catch (BulkProcessorException | PaymentsCommonException e1) {
						log.info( BULK_MAIN_EXECUTOR_STEP + " BulkProcessorExcecption | PaymentsException: " + ExceptionUtils.getFullStackTrace( e ) );
						fileOpenedSuccessFully = Boolean.FALSE;
					}

				}
			}

			if (fileOpenedSuccessFully == Boolean.FALSE) {
				log.info( BULK_MAIN_EXECUTOR_STEP + " could not find file in local, amazon download also failed" );

				executorResponse.setCompletionLog("Could not find file in local, amazon download also failed");
				executorResponse.setStatus(Status.RETRY);
				if(isLastRetryDone(request, registration)) {
					log.info( BULK_MAIN_EXECUTOR_STEP + " last retry done. ");
					log.info("Last retry to download input file...\n\n\n");
					try {
						log.info(BULK_MAIN_EXECUTOR_STEP + "Calling onFinish...\n\n\n");
						registration.getProcessor().onFinish(request.getFileMeta(), registration.getSharedObject(), BulkFileStatus.FAILURE,OUTFILE_NAME);
					} catch (Exception e) {
						log.error( BULK_MAIN_EXECUTOR_STEP + "Exception in OnFinish Method, Kindly handle it! \n");
					}
				}
				return executorResponse;
			}

			log.info( BULK_MAIN_EXECUTOR_STEP + " calling onStart method" );
			registration.onStart();
			/*
			 * STEP 3: check if output file exists in local from some previous  execution
			 * if it does than count number of processed rows and seek input file in equal
			 * amount
			 
			 * startrow determines the row from which processing starts, if some rows were 
			 *already processsed in a previous execution startrow=numberof rows processed 
			 */
			long startRow = 0;
			File outFile = new File(OUTFILE_NAME);

			log.info("\n"+outFile.getParentFile().list().toString()+"\n");

			log.info( BULK_MAIN_EXECUTOR_STEP + " opening absolute output file "+" outFile.exists()  :"+ outFile.exists() +"\n");
			if (outFile.exists()) {
				log.info( BULK_MAIN_EXECUTOR_STEP + " outfile exists " );
				FileInputStream outStream = new FileInputStream(outFile);

				BufferedReader writebuf = new BufferedReader(new InputStreamReader(outStream, "UTF-8"));
				String line = null;
				while ((line = writebuf.readLine()) != null && !line.trim().equalsIgnoreCase("")) {

					startRow++;
				}
				log.info( BULK_MAIN_EXECUTOR_STEP + " startRow : " + startRow +"\n" );
				writebuf.close();
			}

			BufferedReader br = null;

			log.info( BULK_MAIN_EXECUTOR_STEP + "Reading input file. " );
			try {
				br = new BufferedReader(new InputStreamReader(file, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				log.info( BULK_MAIN_EXECUTOR_STEP + " UnsupportedEncodingException " + ExceptionUtils.getFullStackTrace( e1 ) );
				e1.printStackTrace();
			}


			String outputFilePath = registration.getLocalDir() + registration.getOutputFileNameForInput(request.getFileName());

			String firstLine = br.readLine();
			
			//get the headers
			log.info( BULK_MAIN_EXECUTOR_STEP + " reading first line "+ firstLine );
			long rowNum = 1;
			String[] headers = firstLine.split(BulkProcessorUtils.DELIMITER, -1);
			
			int headerSize = headers.length;
			
			for(int i=0; i<headerSize; i++){
				headers[i] = headers[i].trim();
			}
			String line = null;
			// seek input file row to start processing to continue from previous execution
			for (long i = 1; i < startRow; i++) {
				br.readLine();
				rowNum++;
			}
			List<Future<Object>> responseList = new ArrayList<Future<Object>>();
			Object sharedObject = registration.getSharedObject();
			
			processor.onStart(request.getFileMeta(),
					sharedObject, request.getHeaderValues());
			
			FileWriter fileWriter = new FileWriter(outputFilePath, true);
			Set<String> ignoreColumnSet = registration.getProcessor().columnsToIgnore();
			if(ignoreColumnSet == null){
				ignoreColumnSet = new HashSet<String>();
			}
			
			/* STEP 4: loop over the remaining rows 
			*   first loop to go till end of file
			*   second loop to ready only a chunk of data in memory at a time
			*	
			*/
			while ((line = br.readLine()) != null && !line.trim().equals("")) {
				rowNum++;
				responseList.clear();
				long chunkNumber = 0;
				while (line != null && chunkNumber++ < registration.getChunkSize() && !line.trim().equals("")) {

					String[] values = line.split(BulkProcessorUtils.DELIMITER, -1);
					int valueSize = values.length;
					
					for(int i=0; i<valueSize; i++){
						values[i] = values[i].trim();
					}
					// submit callable task in the executer provided during registration
					responseList.add(bulkRowAsyncTask.execute(headers, values, request.getFileMeta(), rowNum,
							sharedObject, request.getActivityId(),processor,executor,request.getHeaderValues()));
					if (chunkNumber != registration.getChunkSize()) {
						line = br.readLine();
						rowNum++;
					}

				}
				
				/*
				 * STEP 5 : write header and Rows to output file
				 * Callable task is returned in a Future object
				 * so get on each row waits for the row thread to conclude
				 * thus we keep on writing rows in output file as the result is concluded
				 * 
				 */
				
				if (startRow == 0) {
					// write header in output file
					writeHeaderInOutputFile(headers, responseList, fileWriter, ignoreColumnSet, registration);
					startRow++;
				}

				log.info("\n  "+ request.getFileName()+ "  now will wait on get from custom executor \n");
				for (Future<Object> obj : responseList) {

					//write row
					log.info("Attempting to write rown number " + (startRow+1));
					writeRow(registration, fileWriter, ignoreColumnSet, obj);

					fileWriter.flush();
					
					startRow++;
				}
				log.info("\n  "+ request.getFileName()+ "  wait ended \n");
			}
			
			/*
			 * STEP 6 : file is completed now close the file and upload output file to  s3
			 * 
			 */
			
			fileWriter.close();
			uploadOutputFileToS3(executorResponse, outputFilePath,request, registration);

			//IOException | InterruptedException | ExecutionException ioe
		} catch (InterruptedException | ExecutionException e){

			log.info( "InterruptedException | ExecutedException " + ExceptionUtils.getFullStackTrace( e ) );
			executorResponse.setStatus(Status.RETRY);
			executorResponse.setCompletionLog("Exception received...");
			if(isLastRetryDone(request, registration)) {
				log.info("Last retry concluded for this row...\n\n\n");
				try {
					log.info("Calling onFinish...\n\n\n");
					registration.getProcessor().onFinish(request.getFileMeta(), registration.getSharedObject(), BulkFileStatus.FAILURE,OUTFILE_NAME);
				} catch (Exception onFinishException) {
					log.error("Exception in OnFinish Method, Kindly handle it! \n"+ExceptionUtils.getFullStackTrace( onFinishException ));
				}
			}

			return executorResponse;
		} catch(Exception ioe) {
			log.info(BulkProcessorUtils.NON_RECOVERABLE_EXCEPTION + ExceptionUtils.getFullStackTrace(ioe) );
			executorResponse.setStatus(Status.FAILURE);
			executorResponse.setCompletionLog("Exception received!");

			try {
				log.info("Calling onFinish...\n\n\n");
				registration.getProcessor().onFinish(request.getFileMeta(), registration.getSharedObject(), BulkFileStatus.FAILURE,OUTFILE_NAME);
			} catch (Exception e) {
				log.error("Exception in OnFinish Method, Kindly handle it! \n" + ExceptionUtils.getFullStackTrace( e ) );
			}

			return executorResponse;
		} 
		//retry case
		if(executorResponse.getStatus()==Status.RETRY){

			if(isLastRetryDone(request, registration)) {
				log.info("Last retry concluded for file ...\n\n\n");
				try {
					log.info("Calling onFinish...\n\n\n");
					registration.getProcessor().onFinish(request.getFileMeta(), registration.getSharedObject(), BulkFileStatus.FAILURE,OUTFILE_NAME);
				} catch (Exception e) {
					log.error("Exception in OnFinish Method, Kindly handle it! \n" + ExceptionUtils.getFullStackTrace( e ) );
					executorResponse.setCompletionLog("Failed on finish, retrying");
				}
			}

			return executorResponse;
		}
		// success 
		try {
			log.info("Calling onFinish...\n\n\n");
			registration.getProcessor().onFinish(request.getFileMeta(), registration.getSharedObject(), BulkFileStatus.SUCCESS, OUTFILE_NAME );
		} catch (Exception e) {
			//again retry 
			log.error("Exception in OnFinish Method, Kindly handle it! \n" + ExceptionUtils.getFullStackTrace( e ) ) ;
			executorResponse.setStatus(Status.RETRY);
			executorResponse.setCompletionLog("Failed on on finish, retrying");
			return executorResponse;
		}

		executorResponse.setStatus(Status.SUCCESS);
		executorResponse.setCompletionLog("executed successfully");

		//delete file 
		File outputLocalFile = new File(OUTFILE_NAME);
		try{
			if(!outputLocalFile.delete() || !downloadedFile.delete()){
				log.info(BulkProcessorUtils.LOCAL_FILE_NOT_DELETED);
			}
		} catch (Exception e)  {
			log.info(BulkProcessorUtils.LOCAL_FILE_NOT_DELETED);
		}

		return executorResponse;
	}

	private void writeRow(IBulkFileRegistration registration, FileWriter fileWriter, Set<String> ignoreColumnSet,
			Future<Object> obj)
					throws InterruptedException, ExecutionException, IOException {

		GenericRequestResponseValues rowData = (GenericRequestResponseValues) obj.get();

		if(rowData.getResponseValues() == null) {
			rowData.setResponseValues(new BulkError(BulkProcessorUtils.NULL_RESPONSE_MSG));
		}

		Field[] fields = rowData.getResponseValues().getClass().getDeclaredFields();
		StringBuilder strBuilder = new StringBuilder();

		String[] values = rowData.getRequestValues();
		for (int i = 0; values != null && i < values.length; i++) {
			strBuilder.append(values[i]);

			if (i < values.length - 1) {
				strBuilder.append(BulkProcessorUtils.DELIMITER);
			}
		}
		if (rowData.getRequestValues().length != 0) {
			strBuilder.append(BulkProcessorUtils.DELIMITER);
		}
		Boolean isFirstField = true;
		for (int i = 0; i < fields.length; i++) {

			if (ignoreColumnSet == null
					|| !ignoreColumnSet.contains(fields[i].getName())) {
				Field field=null;
				String respnseFieldValue=null;
				if (!isFirstField) {

					strBuilder.append(BulkProcessorUtils.DELIMITER);
				}
				isFirstField = false;

				try {
					field = rowData.getResponseValues().getClass().getDeclaredField(fields[i].getName());
				} catch (NoSuchFieldException | SecurityException e1) {
					respnseFieldValue="Cound not fetch field";
				}

				respnseFieldValue = " ";
				try {
					if(field != null) {
						Object cellObj= PropertyUtils.getProperty(rowData.getResponseValues(), field.getName());

						if(cellObj!=null) {

							respnseFieldValue =cellObj.toString();
						}
					}
				} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
					log.info("Getter not found for field name "+ field.getName());

				}

				Pattern p = Pattern.compile(BulkProcessorUtils.REGEX);

				Matcher m = p.matcher(respnseFieldValue);

				strBuilder.append(m.replaceAll(" "));
			}

		}

		fileWriter.append(strBuilder + BulkProcessorUtils.NEW_LINE);
	}

	private void uploadOutputFileToS3(ExecutorResponse executorResponse, String outputFilePath, GenericBulkTaskRequest request,
			IBulkFileRegistration registration) {
		UploadRequest uploadOutputRequest = new UploadRequest();
		uploadOutputRequest.setUserId(request.getUserId());
		uploadOutputRequest.setFileName( registration.getOutputFileNameForInput(request.getFileName()) );
		uploadOutputRequest.setFileSource(outputFilePath);
		uploadOutputRequest.setInputFile(false);
		uploadOutputRequest.setUploadTime(new Date());
		uploadOutputRequest.setActivityId(registration.getBulkActivityId());

		String s3Path = null;
		try {
			s3Path = amazonServices.uploadToS3(uploadOutputRequest);
			log.info("Output fileName : " + uploadOutputRequest.getFileName() + ", uploaded successfully to s3 at Path : " + s3Path);
			executorResponse.setStatus(Status.SUCCESS);
		} catch (PaymentsCommonException|BulkProcessorException e) {
			try {
				s3Path = amazonServices.uploadToS3(uploadOutputRequest);
				log.info("Output fileName : " + uploadOutputRequest.getFileName() + ", uploaded successfully to s3 at Path : " + s3Path);
				executorResponse.setStatus(Status.SUCCESS);
			} catch (PaymentsCommonException|BulkProcessorException e1) {
				executorResponse.setStatus(Status.RETRY);
				executorResponse.setCompletionLog("Tried two times to upload output file to s3, but failed, will exector retry  it now");
			}
		} 
	}

	private void writeHeaderInOutputFile(String[] headers, List<Future<Object>> responseList, FileWriter fileWriter,
			Set<String> ignoreColumnSet, IBulkFileRegistration registration)
					throws InterruptedException, ExecutionException, IOException {

		GenericRequestResponseValues headerValues = (GenericRequestResponseValues) responseList.get(0).get();
		if(headerValues.getResponseValues() == null){
			headerValues.setResponseValues(new BulkError(BulkProcessorUtils.NULL_RESPONSE_MSG));
		}

		Field[] headerFields = headerValues.getResponseValues().getClass().getDeclaredFields();
		StringBuilder headerStrBuilder = new StringBuilder();
		for (int i = 0; i < headers.length; i++) {
			headerStrBuilder.append(headers[i]);

			if (i < headers.length - 1) {
				headerStrBuilder.append(BulkProcessorUtils.DELIMITER);
			}
		}
		if (headers.length != 0) {
			headerStrBuilder.append(BulkProcessorUtils.DELIMITER);
		}
		Boolean isFirstField = true;
		for (int i = 0; (i < headerFields.length); i++) {
			String responseFieldValue = null;
			try {
				if(headerFields[i] != null) {
					Object propertyValue = PropertyUtils.getProperty(headerValues.getResponseValues(), headerFields[i].getName());
					if(propertyValue != null){
						responseFieldValue = propertyValue.toString();
					} else {
						responseFieldValue = "NULL";
					}
				}
			} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				ignoreColumnSet.add(headerFields[i].getName());
			}

			if (ignoreColumnSet == null
					|| !ignoreColumnSet.contains(headerFields[i].getName())) {
				if (!isFirstField) {

					headerStrBuilder.append(BulkProcessorUtils.DELIMITER);
				}
				isFirstField = false;
				headerStrBuilder.append(headerFields[i].getName());

			}
		}
		fileWriter.append(headerStrBuilder + BulkProcessorUtils.NEW_LINE);
		fileWriter.flush();
	}  


	Long getRunNumber(GenericBulkTaskRequest request) {
		List<TaskExecution> taskExecutionsList = taskScheduler.getExecutionInfo(BulkProcessorUtils.GENERIC_TASK_TYPE, request.getTaskId());
		Long runNumber = Long.MIN_VALUE;
		for(TaskExecution taskExecution : taskExecutionsList){
			if(taskExecution.getRunNo() > runNumber){
				runNumber = taskExecution.getRunNo();
			}
		}
		return runNumber;

	}

	boolean isLastRetryDone(GenericBulkTaskRequest request, IBulkFileRegistration registration){
		Long runNumber = this.getRunNumber(request);
		if( runNumber >= BulkProcessorUtils.RETRY_COUNT) {
			return true;
		}
		return false;		
	}

	public static boolean checkExists(File f) { 
		try {
			byte[] buffer = new byte[4];
			InputStream is = new FileInputStream(f); 
			if (is.read(buffer) != buffer.length) { 

			} 
			is.close(); 
			return true; 
		} catch (java.io.IOException fnfe) {

		} return false; 
	}
}


