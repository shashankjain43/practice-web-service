package com.snapdeal.opspanel.userpanel.utils;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.io.FilenameUtils;

@Configuration
@Slf4j
public class GenericUtils {

   @Value("${file.size}")
   private Integer filesize;

   @Value("${bulk.localDir}")
   private String localDir;

   public static GenericResponse getGenericResponse(Object walletResponse) {
      GenericResponse genericResponse = new GenericResponse();
      genericResponse.setError(null);
      genericResponse.setData(walletResponse);
      return genericResponse;
   }

   public static String getOutputFilePathForCSV( String inputFilePath ) {
	   return inputFilePath.substring(0, inputFilePath.indexOf(".csv")) + "_output.csv";
   }

   public String uploadFile( MultipartFile file ) throws InfoPanelException {
      String fileName = file.getOriginalFilename();
      try {
         String ext = FilenameUtils.getExtension(fileName);

         if(!ext.equals("csv")){
            throw new InfoPanelException( "MT-5037", "Sorry The file is not an csv file");               
         }

         if(file.getSize() > filesize ){
            throw new InfoPanelException( "MT-5038", "Sorry The file size is greater than limit " + ( ( double ) filesize ) / 1000000 + " MB");
         }

         byte[] bytes = file.getBytes();
         BufferedOutputStream buffStream = new BufferedOutputStream(
               new FileOutputStream(new File(localDir + fileName)));
         buffStream.write(bytes);
         buffStream.close();
         return localDir + fileName;

      } catch( InfoPanelException ipe ) {
         log.info( "Invalid csv format or size: " + ipe );
         throw ipe;
      }catch (Exception e) {
         log.info( "Exception while uploading file " + e );
         throw new InfoPanelException("MT-5025",  "Upload failed!");
      }

   }

}
