/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.common.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.sun.mail.gimap.GmailFolder;
import com.sun.mail.gimap.GmailStore;

/**
 *  @version   1.0, Jul 21, 2014
 *  @author 	Shashank Jain <jain.shashank@snapdeal.com>
 */

public class GmailReader {

    private String emailId;
    private String password;
    private GmailFolder folder;
    private GmailStore store;
    
    public GmailReader() throws MessagingException{
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "gimap");
        Session session = Session.getDefaultInstance(props, null);
        store = (GmailStore) session.getStore("gimap");
    }

    public String getEmailId() {
        return emailId;
    }
    
    public GmailFolder getFolder() {
        return folder;
    }
    
    public void openConnection(String emailId, String password) throws MessagingException{
        this.emailId = emailId;
        this.password = password;
        store.connect("imap.gmail.com", emailId, password);
    }
    
    public void openFolder(String folderName, int mode) throws MessagingException {
        folder = (GmailFolder)store.getFolder(folderName);
        folder.open(mode);
    }
    
    public void closeFolder() throws MessagingException {
        folder.close(false);
    }
    
    public void closeConnection() throws MessagingException{
        store.close();
    }
    
    public Message[] readMail(String emailFrom, String subject, Date fromDate, Date tillDate) throws MessagingException{
        Message[] mails = null;
        
        if(emailFrom == null || emailFrom.isEmpty()){
            return null;
        }
        
        SearchTerm searchTerm = new FromStringTerm(emailFrom);
        if(subject != null && !subject.isEmpty()){
            searchTerm = new AndTerm(searchTerm, new SubjectTerm(subject));
        }
        if(fromDate != null){
            searchTerm = new AndTerm(searchTerm,new ReceivedDateTerm(ComparisonTerm.GE,fromDate));
        }
        if(tillDate != null){
            searchTerm = new AndTerm(searchTerm,new ReceivedDateTerm(ComparisonTerm.LE,tillDate));
        }
        mails = folder.search(searchTerm);
        return mails;
    }
    
}
