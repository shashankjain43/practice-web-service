package com.snapdeal.opspanel.promotion.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.promotion.constant.BulkPromotionConstants;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.request.FileRow;
import com.snapdeal.opspanel.promotion.service.CSVService;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetCorpAccountBalanceResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("csvService")
public class CSVServiceImpl implements CSVService {

   String delimiter = ",";

   @Autowired
   SDMoneyClient sdMoneyClient;

   @Override
   public FileRow readRow(BufferedReader br, Integer rowCount) throws WalletServiceException{

      String line;
      FileRow fileRow = new FileRow();

      try {
         if ((line = br.readLine()) != null) {
               String[] tokens = line.split(delimiter);
               fileRow.setUserId(tokens[0].trim());
               fileRow.setAmount(new BigDecimal(tokens[1].trim()));
               fileRow.setEventContex(tokens[2].trim());
               fileRow.setRowNum( rowCount );
               rowCount ++;
               return fileRow;
         } else {
            return null;
         }
      } catch( IOException ioe) {
         log.info( "Exception while reading csv row " + ioe );
         throw new WalletServiceException( "MT-5011", "Cannot read csv file");
      }
   }

   public void checkCorpAccountBalance( String accountId, BigDecimal totalBalance ) throws WalletServiceException {
      GetCorpAccountBalanceRequest getCorpAccountBalanceRequest = new GetCorpAccountBalanceRequest();
      getCorpAccountBalanceRequest.setAccountId( accountId );
      try {
         GetCorpAccountBalanceResponse getCorpAccountBalanceResponse = sdMoneyClient.getCorpAccountBalance( getCorpAccountBalanceRequest );
         BigDecimal corpAccountBalance = getCorpAccountBalanceResponse.getBalance();
         if( corpAccountBalance.compareTo( totalBalance ) < 0 )
            throw new WalletServiceException( "MT-5039", "Corp Account Balance is less than requested in file" );
      } catch( SDMoneyException e ) {
         log.info( "Exception while checking corp account balance " + e );
         throw new WalletServiceException( "MT-5040", "Could not check corp account balance." );
      }
   }

   @Override
   public void validateFile(String filePath, String id_type, FileMetaEntity fileEntity) throws WalletServiceException {
      BufferedReader br = null;
      Long numOfRows = 0L;
      BigDecimal totalMoney = new BigDecimal( 0 );
      try {
         InputStream inputStream = new FileInputStream(filePath);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
         br = new BufferedReader( inputStreamReader);
         String line;
         line = br.readLine();
         String[] tokens = line.split( delimiter );
         if( ! tokens[ 1 ].trim().equalsIgnoreCase("Amount") ) {
        	 throw new WalletServiceException( "MT-5013", "Invalid header. Please use sample file to create input file." );
         }
         while( ( line = br.readLine() ) != null ) {
            numOfRows ++;
            tokens = line.split(delimiter);
            if( numOfRows > BulkPromotionConstants.maxCSVRow ) {
        		throw new WalletServiceException( "MT-5013", "File cannot be larger than " + BulkPromotionConstants.maxCSVRow + " Rows");
            }
            if(tokens.length==1) {
            	throw new WalletServiceException( "MT-5013", " File can not have a blank row or a row not containing column separators" );
            } else if( tokens.length != 3 ) {
               throw new WalletServiceException( "MT-5012", " Invalid CSV format. Inappropriate number of commas on line number " + numOfRows + ". Make sure, file does not contain comma in content.");
            }

			switch (id_type) {
				case "EMAIL_ID":
					Pattern pattern1 = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");//^(.+)@(.+)$");
										if (pattern1.matcher(tokens[0].trim()).matches()) {
					} else {
						throw new WalletServiceException("MT-5018", "Sorry! File does not contain valid email id at line number " + numOfRows );
					}

					break;
				case "MOBILE_ID":
					Pattern pattern2 = Pattern.compile("\\d{10}");

					if (pattern2.matcher(tokens[0].trim()).matches()) {
					} else {
						throw new WalletServiceException("MT-5019", "Sorry! File does not contain valid mobile number at line number " + numOfRows );
					}

					break;
				case "IMS_ID":
					Pattern pattern3 = Pattern.compile("[a-zA-Z0-9]*[a-zA-Z]+[a-zA-Z0-9]*");

					if (pattern3.matcher(tokens[0].trim()).matches()) {
					} else {
						throw new WalletServiceException("MT-5019", "Sorry! File does not contain valid ims id at line number " + numOfRows );
					}

				break;
			}
			BigDecimalValidator validator = new BigDecimalValidator();
            BigDecimal b = validator.validate( tokens[ 1 ].trim() );
            if( b == null ) {
               throw new WalletServiceException( "MT-5013", " Invalid CSV format. Invalid amount format at line number " + numOfRows );
            }
            totalMoney = totalMoney.add(b);
         }
         fileEntity.setTotalRows(numOfRows);
         fileEntity.setTotalMoney(totalMoney);
      } catch( FileNotFoundException fnfe ){
         log.info("CSV file not found " + fnfe );
         throw new WalletServiceException( "MT-5014", "File not found." );
      } catch( IOException ioe ){
         log.info( "IOException while reading csv file " + ioe);
         throw new WalletServiceException( "MT-5015", "Error reading file " );
      } finally {
         try {
            if( br != null ){
               br.close();
            }
         } catch( IOException ioe ) {
            throw new WalletServiceException( "MT-5016", "Error closing file" );
         }
      }
   }
//
//   @Override
//   public void writeRow(BufferedWriter bw, OutputResponse response) throws WalletServiceException {
//
//	   String uploadTimeStamp,transactionTimeStamp;
//	   
//	   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//	   uploadTimeStamp = dateFormat.format(response.getUploadTimeStamp());
//	   transactionTimeStamp=dateFormat.format(response.getTransactionTimeStamp());
//	   
//	   
//      try {
//         bw.write( response.getUserId() + "," + response.getAmount() + "," + 
//            response.getRemarks() + "," + uploadTimeStamp + "," + 
//            transactionTimeStamp + "," + response.getResponseCode() + "," +(response.getResponseMessage()==null?" ":(response.getResponseMessage().replaceAll(",", " ")))+","+
//            response.getResponseStatus() + "\n" );
//      } catch( IOException ie ) {
//         log.info( "Exception while writing csv row " + ie );
//         throw new WalletServiceException( "MT-5017", "Error Writing file" );
//      }

//   }
}
