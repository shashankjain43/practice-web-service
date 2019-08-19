/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpResponse;
import com.snapdeal.cps.common.mongo.LastRunInfo;
import com.snapdeal.cps.common.service.GoogleProductInfoService;
import com.snapdeal.cps.common.service.ProcessUtilityService;
import com.snapdeal.cps.common.service.SellerService;
import com.snapdeal.cps.common.utils.GmailReader;
import com.snapdeal.cps.google.api.GoogleContentAPI;

/**
 *  @version   1.0, Jul 21, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service("deleteSuspendedSellerTask")
public class DeleteSuspendedSellerTask {

    private static final Logger log = LoggerFactory.getLogger("tasks.logger");
    
    // Define process name
    private static final String PROCESS_NAME = "deleteSuspendedSellerTask";
    
    // Define property constants
    private static final String SRC_EMAIL_ID = "gmail.id";
    private static final String SRC_EMAIL_PASS = "gmail.pass";
    private static final String SRC_EMAIL_FOLDER = "gmail.folder";
    private static final String SENDER_EMAIL = "gmc.email";
    private static final String SUBJECT_STRING = "gmc.email.suspension.subject";
    
    @Autowired
    private GoogleProductInfoService googleProductService;
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private ProcessUtilityService processUtilityService;
    
    public void deleteSuspendedSellers(){
        
        List<String> subaccounts = new ArrayList<String>();
        try{
            LastRunInfo publisherInfo = processUtilityService.getLastRunInfoByProcessName(PROCESS_NAME);
            if (publisherInfo == null) {
                throw new Exception("Unable to get publisher info from DB");
            }
            Date lastRunTs = publisherInfo.getLastRunTs();
            log.info("Last Run Ts: " + lastRunTs);
            
            GmailReader gmailReader = new GmailReader();
            
            // Fetch properties
            String emailId = processUtilityService.getProcessProperty(PROCESS_NAME, SRC_EMAIL_ID);
            String password = processUtilityService.getProcessProperty(PROCESS_NAME, SRC_EMAIL_PASS);
            String folderName = processUtilityService.getProcessProperty(PROCESS_NAME, SRC_EMAIL_FOLDER);
            String senderEmail = processUtilityService.getProcessProperty(PROCESS_NAME, SENDER_EMAIL);
            String subjectString = processUtilityService.getProcessProperty(PROCESS_NAME, SUBJECT_STRING);
            
            log.info("Connecting to Gmail ..");
            
            gmailReader.openConnection(emailId, password);
            gmailReader.openFolder(folderName, Folder.READ_ONLY);
            
            Message[] mails = gmailReader.readMail(senderEmail, null, lastRunTs, null);
            
            log.info("Number of mails obtained: " + mails.length);
            
            int matchingSubjectMailCount = 0;
            for(Message mail: mails){
                if(mail.getSubject().startsWith(subjectString)){
                    matchingSubjectMailCount = matchingSubjectMailCount + 1;
                    if(mail.isMimeType("multipart/alternative")){
                        String mailContent = null;
                        String subaccountID = null;
                        Multipart multiPartContent = (Multipart) mail.getContent();
                        BodyPart bodyPart = multiPartContent.getBodyPart(0);
                        if(bodyPart.isMimeType("text/*")){
                            mailContent = (String) bodyPart.getContent();
                        }else{
                            log.error("Unable to read mail");
                            continue;
                        }
                        Pattern p = Pattern.compile("[0-9]+");
                        Matcher m = p.matcher(mailContent);
                        if(m.find()){
                            subaccountID = m.group(0);
                            subaccounts.add(subaccountID);
                        }
                    }else{
                        log.error("Unable to read mail");
                    }
                }
            }

            log.info("Number of matched mails: " + matchingSubjectMailCount);
            log.info("Number of suspended subaccounts: " + subaccounts.size());
            
            gmailReader.closeFolder();
            gmailReader.closeConnection();
            
            log.info("Closed connection with Gmail");
            
            GoogleContentAPI gmcApi = new GoogleContentAPI();
            for(String gsId: subaccounts){
                try{
                    log.info("Deleting subaccount: " + gsId);
                    
                    // Delete subaccount from GMC
                    HttpResponse response = gmcApi.deleteSubAccount(gsId);
                    if (response.parseAsString().contains("<error>")) {
                        throw new Exception(response.parseAsString());
                    }
                    
                    // Delete subaccount from mongo
                    googleProductService.deleteSubaccountById(gsId);
                    
                }catch(Exception ex){
                    log.error("Error while removing subaccount");
                    log.error(ExceptionUtils.getFullStackTrace(ex));
                }
            }
            
            // Update last run ts with current ts
            processUtilityService.updateLastRunTs(PROCESS_NAME, Calendar.getInstance().getTime());
            
            log.info("Updated last run ts. Exiting with success");
            
        }catch(MessagingException mex){
            log.error("Exception while reading mail");
            log.error(ExceptionUtils.getFullStackTrace(mex));
        }catch(Exception ex){
            log.error(ExceptionUtils.getFullStackTrace(ex));
        }
        
    }
}
