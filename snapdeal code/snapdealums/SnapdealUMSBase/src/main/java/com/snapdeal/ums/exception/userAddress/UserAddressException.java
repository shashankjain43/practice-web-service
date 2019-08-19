/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.exception.userAddress;

import com.snapdeal.base.services.exception.ServiceException;

public class UserAddressException extends ServiceException{

    /**
     * 
     */
    private static final long serialVersionUID = -2156136077128774635L;
    
    private UserAddressErrorCode errorCode;

    public enum UserAddressErrorCode {
        USER_ADDRESS_ALREADY_EXIST(501,"User Address already present"),
        USER_ADDRESS_COUNT_LIMIT(502,"User Address count exceed limit"),
        USER_MISSING_FOR_USER_ADDRESS(503,"User param missing"),
        REQUEST_PARAM_MISSING(504,"Request Parameter is/are missng"),
        REQUEST_AUTHORIZATION_VIOLATION(505,"Incorrect Authorization Key"),
        UMS_USERADDRESS_INTERNALSERVER_ERROR(506,"Internal server error"),
        SET_DEFAULT_FAILURE(507,"SetDefault Failure for requested userId,useraddressId pair"),
        USER_ADDRESS_NOT_EXIST_OR_DELETED(508,"Requested userId,useraddressId pair does not exist/already deleted!"),
        USER_ADDRESS_MAPPING_NOT_EXIST(509,"Requested userId,useraddressId pair does not exist");
    
        private int    code;
        private String description;
    
        private UserAddressErrorCode(int code, String description) {
            this.code = code;
            this.description = description;
        }
    
        public int code() {
            return this.code;
        }
    
        public String description() {
            return this.description;
        }
    }

    public UserAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAddressException(UserAddressErrorCode errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UserAddressException(UserAddressErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public UserAddressErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(UserAddressErrorCode errorCode) {
        this.errorCode = errorCode;
    }


}