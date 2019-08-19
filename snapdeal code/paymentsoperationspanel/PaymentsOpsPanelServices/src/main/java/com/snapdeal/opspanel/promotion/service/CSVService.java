package com.snapdeal.opspanel.promotion.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.math.BigDecimal;

import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.model.OutputResponse;
import com.snapdeal.opspanel.promotion.request.FileRow;


public interface CSVService {
   public FileRow readRow( BufferedReader br, Integer rowCount ) throws WalletServiceException;
   public void validateFile( String filePath, String id_type, FileMetaEntity fileEntity ) throws WalletServiceException;
   //public void writeRow( BufferedWriter bw, OutputResponse respone ) throws WalletServiceException;
   public void checkCorpAccountBalance( String accountId, BigDecimal totalBalance ) throws WalletServiceException;
}
