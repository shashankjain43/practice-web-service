package com.exception;

public class InvalidInputException extends ServiceException {

    public InvalidInputException(String errMsg) {
        super(errMsg);
    }

    public InvalidInputException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
