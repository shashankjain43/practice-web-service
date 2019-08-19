package com.snapdeal.cps.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */
public class FTPUtils {

    private static final Logger log = LoggerFactory.getLogger(FTPUtils.class);
    
    public static boolean uploadFileToFtp(File file, String host,int port, String username, String password, String destinationDir) throws Exception{
        
        // Create FTP Client
        FTPClient ftpClient = new FTPClient();
        InputStream fis = null;
        boolean uploadedToFTP = false;
        
        try{
            ftpClient.setDefaultTimeout(120000);
            ftpClient.setDataTimeout(120000);
            
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            ftpClient.enterLocalPassiveMode();

            if(!destinationDir.equals("/")){
                ftpClient.changeWorkingDirectory(destinationDir);
            }
            
            fis = new FileInputStream(file);
            uploadedToFTP = ftpClient.storeFile(file.getName(), fis);
            
            log.info(ftpClient.getReplyString());
            
        }catch(Exception ex){
            throw(ex);
        }finally {
            if(fis != null) {
                fis.close();
            }
            ftpClient.logout();
            ftpClient.disconnect();
        }
        
        return uploadedToFTP;
    }
}
