package com.root.ExcelService.exception;

import com.root.commondependencies.constants.ExceptionConstants;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ValidationExceptionMapper{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVO> handleException(
            Exception ex, WebRequest request) {
        ErrorVO error = new ErrorVO();
        if(ex instanceof ValidationException){
            ValidationException validationException = (ValidationException) ex;
            error.setErrorCode(String.valueOf(validationException.getErrorCode()));
            error.setErrorMsg(validationException.getErrorMessage());
            error.setUrl(validationException.getUrl());
            return new ResponseEntity<ErrorVO>(error, HttpStatus.BAD_REQUEST);
        }
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        error.setErrorMsg(ExceptionConstants.INTERNAL_ERROR);
        return new ResponseEntity<ErrorVO>(error, HttpStatus.BAD_REQUEST);
    }

}
