package com.controller;

import com.exception.InvalidInputException;
import com.exception.ServiceException;
import com.request.BaseRequest;
import com.response.BaseResponse;
import com.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T extends BaseResponse> ServiceResponse<T> handleException(MethodArgumentNotValidException exception, HttpServletResponse http) {
        ServiceResponse<T> response = new ServiceResponse<T>();
        //log.error("Error message is: " + exception.getMessage());
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String errorMsg = "Input Validation failure: ";
        for (ObjectError error : allErrors) {
            errorMsg = errorMsg + error.getDefaultMessage();
        }
        InvalidInputException ex = new InvalidInputException(errorMsg);
        response.setException(ex);
        http.setStatus(HttpStatus.BAD_REQUEST.value());
        return response;
    }

    @ExceptionHandler(ServiceException.class)
    public <T extends BaseResponse> ServiceResponse<T> handleException(ServiceException exception) {
        ServiceResponse<T> response = new ServiceResponse<T>();
        //log.error("Error message is: " + exception.getMessage());
        response.setException(exception);
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    public <T extends BaseResponse> ServiceResponse<T> handleException(RuntimeException exception) {
        ServiceResponse<T> response = new ServiceResponse<T>();
        ServiceException genericException = new ServiceException(exception.getMessage());
        //log.error("Error message is: " + exception.getMessage());
        response.setException(genericException);
        return response;
    }


}
