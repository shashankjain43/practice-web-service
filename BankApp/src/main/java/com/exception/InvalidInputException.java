package com.exception;

import lombok.Data;

@Data
public class InvalidInputException extends ServiceException {

    public InvalidInputException(String errMsg) {
        super("ER-400", errMsg);
    }

    public InvalidInputException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
