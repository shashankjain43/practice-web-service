package com.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errCode;
    private String errMsg;

    public ServiceException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public ServiceException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
