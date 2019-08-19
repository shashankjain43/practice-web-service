/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 23-Aug-2012
 *  @author fanendra
 */
package com.snapdeal.web.security.exception;

/**
 * @author fanendra
 */
public class InvalidSessionSecurityTokenException extends Exception {  
    /**
     * 
     */
    private static final long serialVersionUID = 3912042080965673746L;
   /**
    * Creates a new {@link InvalidSessionSecurityTokenException} object with 
    * passed message string.
    * @param message
    */
    public InvalidSessionSecurityTokenException(String message) {
        super(message);
    }
}
