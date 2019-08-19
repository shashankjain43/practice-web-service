/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 21, 2010
 *  @author rahul
 */
package com.snapdeal.ims.otp.sms;

public interface ISMSSender {

	public void send(String mobile, String message);

}