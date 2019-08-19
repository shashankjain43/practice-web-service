package com.snapdeal.opspanel.userpanel.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.snapdeal.opspanel.userpanel.bulk.BulkCSVRow;
import com.snapdeal.opspanel.userpanel.bulk.BulkProcessorAdaptor;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.service.AsyncBulkService;
import com.snapdeal.opspanel.userpanel.service.BulkService;
import com.snapdeal.opspanel.userpanel.service.FileManagementService;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service( "BulkService" )
@Slf4j
public class BulkServiceImpl implements BulkService {

   @Autowired
   HttpServletRequest request;

//   @Autowired
//   BulkProcessorAdaptor bulkProcessor;

   @Autowired
   AsyncBulkService asyncBulkService;

   @Autowired
   FileManagementService fileManagementService;

   @Override
   @Async("actionBulkExecutor")
	public void executeBulkAction(ActionBulkRequest actionBulkRequest, Date currDate, String emailId) throws InfoPanelException {
	   // sending null id_type as ActionService doesn't need it
	   log.info( "BulkAction Step: Executing bulk operation");
	   executeBulkOperation(actionBulkRequest );
	   log.info( "BulkAction Step: Execution complete");

		try {
			log.info( "BulkAction Step: pushing bulk action output file to amazon");
			fileManagementService.pushActionBulkOutputFile(actionBulkRequest, currDate, emailId );
		} catch (Exception e) {
			log.info("Exception while uploading result file to amazon " + ExceptionUtils.getFullStackTrace( e ));
			try {
				fileManagementService.pushActionBulkOutputFile(actionBulkRequest, currDate, emailId );
			} catch( Exception ex ) {
				log.info( "Exception while retrying upload result file to amazon " + ExceptionUtils.getFullStackTrace( ex ) );
				throw new InfoPanelException("MT-5102", "Unable to upload file to amazon s3 server");
			}
		} finally {
			log.info( "BulkAction Step: deleting ");
			deleteFile( GenericUtils.getOutputFilePathForCSV( actionBulkRequest.getUploadFilePath() ));
			deleteFile( actionBulkRequest.getUploadFilePath());
		}

	}

   public void executeBulkOperation( ActionBulkRequest actionBulkRequest )
            throws InfoPanelException {

	  String filePath = actionBulkRequest.getUploadFilePath();
 
	  log.info( "Bulk Step: reading file ");
      List<BulkCSVRow> bulkCSVRowList = readWithCsvBeanReader(filePath);
	  log.info( "Bulk Step: file read successfully ");

	  log.info( "Bulk Step: executing file ");
      //List<BulkCSVRow> bulkOutput = bulkProcessor.executeOperation(bulkCSVRowList, actionBulkRequest );

      // Added new code for asynchronous execution
	  int rowNumber = 0;
	  List<Future<BulkCSVRow>> futureBulkCSVList  = new ArrayList<Future<BulkCSVRow>>();
      for( BulkCSVRow bulkCSVRow : bulkCSVRowList ) {
    	  rowNumber ++;
    	  futureBulkCSVList.add( asyncBulkService.executeRow( rowNumber, bulkCSVRow, actionBulkRequest ) );
      }
	  log.info( "Bulk Step: executed successfully ");

	  List<BulkCSVRow> bulkOutput = new ArrayList<BulkCSVRow>();
	  for( Future<BulkCSVRow> futureBulkCSVRow : futureBulkCSVList ) {
		  try {
			  bulkOutput.add( futureBulkCSVRow.get() );
		  } catch( InterruptedException | ExecutionException e ) {
			  log.info( "Bulk Step: Exception while execution of bulkCSV Row " + ExceptionUtils.getFullStackTrace( e ) );
			  bulkOutput.add( new BulkCSVRow( bulkCSVRowList.get( bulkOutput.size() ).getId(), "Execution failed." ) );
		  }
	  }
//      if( bulkOutput == null ) {
//    	  log.info( "Bulk Step: No processor found for given action"  );
//    	  throw new InfoPanelException( "MT-5801", "No processor found for given action. ");
//      }
  
	  log.info( "Bulk Step: writing output file ");
      String outputFilePath = GenericUtils.getOutputFilePathForCSV( filePath );
      writeWithCSVBeanWriter(outputFilePath, bulkOutput);
	  log.info( "Bulk Step: output file written successfully ");
   }

   private List<BulkCSVRow> readWithCsvBeanReader(String filePath) throws InfoPanelException {

      BulkCSVRow bulkCSVRow;
      List<BulkCSVRow> bulkCSVRowList = new ArrayList<BulkCSVRow>();
      ICsvBeanReader beanReader = null;

      try {
         beanReader = new CsvBeanReader(new InputStreamReader( new FileInputStream( filePath ) , StandardCharsets.UTF_8),
                  CsvPreference.STANDARD_PREFERENCE);

         // the header elements are used to map the values to the bean (names
         // must match)
         final String[] header = beanReader.getHeader(true);

         final CellProcessor[] processors = new CellProcessor[] { new NotNull() };

         while ((bulkCSVRow = beanReader.read(BulkCSVRow.class, header, processors)) != null) {
            bulkCSVRowList.add(bulkCSVRow);
         }
      } catch (Exception e) {
         log.info("Exception while reading CSV row " + e);
         throw new InfoPanelException( "MT-5102", "Could not read CSV File. Please check format of the file and retry.");
      }finally {
         if (beanReader != null) {
            try {
               beanReader.close();
            } catch (IOException ioe) {
               log.info(" Exception while closing CSV bean reader " + ioe );
            }
         }
      }
      return bulkCSVRowList;
   }

   private void writeWithCSVBeanWriter(String filePath, List<BulkCSVRow> csvBulkRowList)
            throws InfoPanelException {

      ICsvBeanWriter beanWriter = null;
      try {
         beanWriter = new CsvBeanWriter(new FileWriter(filePath),
        		 new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE).ignoreEmptyLines(false).build());

         // the header elements are used to map the bean values to each column
         // (names must match)
         final String[] header = new String[] { "id", "status" };
         final CellProcessor[] processors = new CellProcessor[] { new NotNull(), new NotNull() };

         // write the header
         beanWriter.writeHeader(header);

         // write the beans
         for (final BulkCSVRow bulkCSVRow : csvBulkRowList) {
            beanWriter.write(bulkCSVRow, header, processors);
         }

      } catch (IOException ioe) {
         log.info( "Exception while writing csv " + ioe );
         throw new InfoPanelException("MT-5074", "Could not write CSV File");
      } finally {
         if (beanWriter != null) {
            try {
               beanWriter.close();
            } catch (IOException ioe) {
               log.info( "Exception while closing bean writer " + ioe);
            }
         }
      }
   }

	private void deleteFile(String filePath) {
		if (filePath == null)
			return;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

}