package com.snapdeal.merchant.data.handler.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.snapdeal.merchant.data.handler.impl.CSVTxnFileDecorator;
import com.snapdeal.merchant.data.handler.impl.PDFTxnFileDecorator;
import com.snapdeal.merchant.data.handler.impl.XLSTxnFileDecorator;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.ReportType;
import com.snapdeal.merchant.file.handler.IFileDecorator;

@Configuration
public class FileHandlerConfig {
	
	@Bean(name="fileHandler")
	@Scope
	@SuppressWarnings("rawtypes")
	public Map<ReportType,Map<FileType,IFileDecorator>> actionMap(){
		Map<ReportType,Map<FileType,IFileDecorator>> fileHandler ;
		
		fileHandler = new HashMap<ReportType,Map<FileType,IFileDecorator>>();
		
		Map<FileType,IFileDecorator> txnFileMapping = new HashMap<FileType,IFileDecorator>();
		txnFileMapping.put(FileType.XLS, new XLSTxnFileDecorator());
		txnFileMapping.put(FileType.PDF, new PDFTxnFileDecorator());
		txnFileMapping.put(FileType.CSV, new CSVTxnFileDecorator());
		
		fileHandler.put(ReportType.TXN, txnFileMapping);
		
		return fileHandler;
		
		
	}
	

}
