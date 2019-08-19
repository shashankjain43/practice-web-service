package com.snapdeal.merchant.data.handler.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.snapdeal.merchant.dto.MPTransactionDTO;
import com.snapdeal.merchant.file.handler.IFileDecorator;
import com.snapdeal.merchant.file.handler.exception.FileHandlingException;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.merchant.util.ExcelConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class XLSTxnFileDecorator implements IFileDecorator<List<MPTransactionDTO>,Workbook> {

	@Override
	public Workbook decorate(List<MPTransactionDTO> mpTransactions) throws FileHandlingException {

		
		int rNum = 0, cNum = 0, j = 0;

		
		Workbook wb = new HSSFWorkbook();
		try {

			Sheet sheet = null;

			sheet = wb.createSheet(ExcelConstants.sheetName);

			// setting name for column
			Row row = sheet.createRow(rNum++);

			HSSFFont font = (HSSFFont) wb.createFont();
			HSSFCellStyle boldCellStyle = (HSSFCellStyle) wb.createCellStyle();

			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setBold(true);
			boldCellStyle.setFont(font);

			DataFormat fmt = wb.createDataFormat();
			CellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat(ExcelConstants.testStyle));
		

			CellStyle numberStyle = wb.createCellStyle();
			numberStyle.setDataFormat(fmt.getFormat(ExcelConstants.numberStyle));
			
			CellStyle dateStyle = wb.createCellStyle();
			dateStyle.setAlignment(CellStyle.ALIGN_LEFT);
			dateStyle.setDataFormat(fmt.getFormat(ExcelConstants.dateStyle));
			
			row.createCell(cNum++).setCellValue(ExcelConstants.txnDate);
			row.createCell(cNum++).setCellValue(ExcelConstants.orderId);
			row.createCell(cNum++).setCellValue(ExcelConstants.fcTxnId);
			row.createCell(cNum++).setCellValue(ExcelConstants.merchantId);
			row.createCell(cNum++).setCellValue(ExcelConstants.merchantName);
			row.createCell(cNum++).setCellValue(ExcelConstants.txnType);
			row.createCell(cNum++).setCellValue(ExcelConstants.totalTxnAmount);
			row.createCell(cNum++).setCellValue(ExcelConstants.merchantFee);
			row.createCell(cNum++).setCellValue(ExcelConstants.serviceTax);
			row.createCell(cNum++).setCellValue(ExcelConstants.swachBharatCess);
			row.createCell(cNum++).setCellValue(ExcelConstants.krishiKalyanCess);
			row.createCell(cNum++).setCellValue(ExcelConstants.netDeduction);
			row.createCell(cNum++).setCellValue(ExcelConstants.payableAmount);
			row.createCell(cNum++).setCellValue(ExcelConstants.terminalId);
			row.createCell(cNum++).setCellValue(ExcelConstants.storeId);
			row.createCell(cNum++).setCellValue(ExcelConstants.storeName);
			row.createCell(cNum++).setCellValue(ExcelConstants.productId);
			row.createCell(cNum++).setCellValue(ExcelConstants.custId);
			row.createCell(cNum++).setCellValue(ExcelConstants.custName);
			row.createCell(cNum++).setCellValue(ExcelConstants.txnStatus);
			row.createCell(cNum++).setCellValue(ExcelConstants.location);
			row.createCell(cNum++).setCellValue(ExcelConstants.shippingCity);
			row.createCell(cNum++).setCellValue(ExcelConstants.emailId);
			row.createCell(cNum++).setCellValue(ExcelConstants.mobile);
			

			for (j = 0; j < cNum; j++) {
				row.getCell(j).setCellStyle(boldCellStyle);

			}

			for (MPTransactionDTO mpTransactionDTO : mpTransactions) {

				cNum = 0;
				j = 0;
				row = sheet.createRow(rNum++);
						
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getTxnDate());
				row.getCell(cNum-1).setCellStyle(dateStyle);
				
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getOrderId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getFcTxnId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getMerchantId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getMerchantName());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getTxnType().getTxnTypeValue());
				
				// setting Format of cell to Text
				for (j = 1; j < cNum; j++) {
					row.getCell(j).setCellStyle(textStyle);
				}
				
				
				if(mpTransactionDTO.getTotalTxnAmount() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getTotalTxnAmount().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getMerchantFee() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getMerchantFee().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getServiceTax() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getServiceTax().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getSwachBharatCess() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getSwachBharatCess().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getKrishiKalyanCess() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getKrishiKalyanCess().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getNetDeduction() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getNetDeduction().doubleValue());
				else
					row.createCell(cNum++);
				
				if(mpTransactionDTO.getPayableAmount() != null)
					row.createCell(cNum++).setCellValue(mpTransactionDTO.getPayableAmount().doubleValue());
				else
					row.createCell(cNum++);
				
				// setting Format of cell to General Number
				for (; j < cNum; j++) {
					row.getCell(j).setCellStyle(numberStyle);
				}
				
				
				
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getTerminalId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getStoreId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getStoreName());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getProductId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getCustId());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getCustName());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getTxnStatus().getTxnStatusValue());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getLocation());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getShippingCity());
				
			
				
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getEmail());
				row.createCell(cNum++).setCellValue(mpTransactionDTO.getMobile());
				
				// setting Format of cell to Text
				for (; j < cNum; j++) {
					row.getCell(j).setCellStyle(textStyle);
				}

				
			} // for loop end
		} catch (Exception e) {
			log.error("Getting Exception in Exporting Txns to Excel : {}", e);
			throw new FileHandlingException(e.getMessage());

		}
		
		return (HSSFWorkbook) wb;
	
	}

	public void save(Workbook wb, String fileName) throws FileHandlingException {
		
		FileOutputStream fileOut;
		try {			
			fileOut = new FileOutputStream(new File(fileName));

			wb.write(fileOut);
			fileOut.close();
			//wb.close();
		} catch (FileNotFoundException e) {

			log.error("File Not Found : {} ",e);
			throw new FileHandlingException(e.getMessage());
			
		} catch (IOException e) {
			log.error("Could not  Save file: { }",e);
			throw new FileHandlingException(e.getMessage());
		}
	}

	@Override
	public void delete( String filePath){
		
		try {

			File file = new File(filePath);
			boolean bool = file.delete();
			
			if (bool) {
				log.info(file.getName() + " is deleted!");
			} 
			
		} catch (Exception e) {
           
			log.error("unable to delete file : {} ", e);
		}
		
	}



}
