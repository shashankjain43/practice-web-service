package com.snapdeal.opspanel.userpanel.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.FraudOutputResponse;
import com.snapdeal.opspanel.userpanel.response.FraudTransactionDetails;
import com.snapdeal.opspanel.userpanel.response.TransactionSummaryRow;

import lombok.extern.slf4j.Slf4j;

@Service("csvWriter")
@Slf4j
public class SuperCSVWriter {

	private CellProcessor[] processors;

	private String[] header;

	private ICsvBeanWriter beanWriter;

	public SuperCSVWriter(CellProcessor[] processors, String[] header) {
		super();
		this.processors = processors;
		this.header = header;
	}

	public SuperCSVWriter() {
		super();
	}

	public byte[] createCSVForFraud(String[] userDetailsHeader,String[] transactionDetailsHeader,
			CellProcessor[] userDetailCellProcessor,CellProcessor[] transactionDetailCellProcessor,FraudOutputResponse fraudOutputResponse
			,List<FraudTransactionDetails> fraudTransactionDetailsList) throws InfoPanelException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
		byte [] output;
		
		try {
			beanWriter = new CsvBeanWriter(writer,
					CsvPreference.STANDARD_PREFERENCE);
			
			beanWriter.writeHeader(userDetailsHeader);
		
			beanWriter.write(fraudOutputResponse, userDetailsHeader, userDetailCellProcessor);
					
			String[] empty= {"",""};
			
			beanWriter.writeHeader(empty);
			
			if(transactionDetailsHeader.length!=0)
			{
				beanWriter.writeComment("\t\t Transaction details ");
	
				// writing header 
				beanWriter.writeHeader(transactionDetailsHeader);
				
				
				for(FraudTransactionDetails fraudTransactionDetails:fraudTransactionDetailsList){
					
					beanWriter.write(fraudTransactionDetails,transactionDetailsHeader,transactionDetailCellProcessor);
				}
			}
			
			
			
			beanWriter.flush();
		} catch (IOException ex) {
		   log.info( "Exception while writing in csv bean writer" + ex);
			throw new InfoPanelException( "MT-5071", "Error writing the CSV file: " + ex);
		} finally {
			output = baos.toByteArray();
			if (beanWriter != null) {
				try {
					beanWriter.close();
				} catch (IOException ex) {
               log.info( "Exception while writing in csv bean writer " + ex);
				}
			}
			if( writer != null ) {
				try {
					writer.close();
				} catch (IOException ex) {
               log.info( "Exception while writing in csv bean writer " + ex);
				}
			}
			if( baos != null ) {
				try {
					baos.close();
				} catch (IOException ex) {
               log.info( "Exception while writing in csv bean writer " + ex);
				}
			}
		}
		return output;
		
		
	}

	public byte[] createCSV(List<TransactionSummaryRow> listItems) throws InfoPanelException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
		byte[] output;
		try {
			beanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(header);

			for (TransactionSummaryRow obj : listItems) {
				beanWriter.write(obj, header, processors);
			}
			beanWriter.flush();
		} catch (IOException ex) {
			log.info("Exception while writing in csv bean writer" + ex);
			throw new InfoPanelException("MT-5071", "Error writing the CSV file: " + ex);
		} finally {
			output = baos.toByteArray();
			if (beanWriter != null) {
				try {
					beanWriter.close();
				} catch (IOException ex) {
					log.info("Exception while writing in csv bean writer " + ex);
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					log.info("Exception while writing in csv bean writer " + ex);
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ex) {
					log.info("Exception while writing in csv bean writer " + ex);
				}
			}
		}
		return output;
	}
}
