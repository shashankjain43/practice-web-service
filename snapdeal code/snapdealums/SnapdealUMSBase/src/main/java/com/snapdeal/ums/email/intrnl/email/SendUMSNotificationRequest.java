/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.snapdeal.ums.email.intrnl.email;

import com.snapdeal.base.model.common.ServiceRequest;

/**
 *  
 *  @version     1.0, 12-Jan-2015
 *  @author shashank
 */
public class SendUMSNotificationRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = 7550356044827394904L;
    
    private String msg;
    private String subject;

    public SendUMSNotificationRequest() {
        super();
    }
    
    public SendUMSNotificationRequest(String msg) {
		super();
		this.msg = msg;
	}

	public SendUMSNotificationRequest(String msg, String subject) {
		super();
		this.msg = msg;
		this.subject = subject;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
    
    
}
