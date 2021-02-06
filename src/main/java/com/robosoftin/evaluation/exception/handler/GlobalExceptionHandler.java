package com.robosoftin.evaluation.exception.handler;

import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.dto.GenericServerResponse;
import com.robosoftin.evaluation.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String DEFAULT_EXCEPTION_CAUSE_FORMAT = "Exception : {} , Cause : {}";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericServerResponse> generalException(final HttpServletResponse response, Exception e) {

        if (null == e.getMessage()) {
            log.error(DEFAULT_EXCEPTION_CAUSE_FORMAT, e.getClass(), e.getMessage(), e);
        } else {
            log.error(DEFAULT_EXCEPTION_CAUSE_FORMAT, e.getClass(), e.getMessage());
        }

        GenericServerResponse serverResponse;
        if (e instanceof DataAccessException || e instanceof PersistenceException)
            serverResponse = new GenericServerResponse(ErrorConstants.DB_EXCEPTION, AppConstants.DB_OP_ERROR, null);
        else
         serverResponse = new GenericServerResponse(ErrorConstants.GENERAL_EXCEPTION.getCode(), ErrorConstants.GENERAL_EXCEPTION.getMessage(), null);
        return new ResponseEntity<>(serverResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity<GenericServerResponse> handleGeneralBentoException(final HttpServletResponse response, CustomizedException e) {
        log.error(DEFAULT_EXCEPTION_CAUSE_FORMAT, e.getClass(), e.getMessage());
        String errMsg = e.getResultInfo().getMessage() != null ? e.getResultInfo().getMessage() : e.getMessage();
        GenericServerResponse serverResponse = new GenericServerResponse(e.getResultInfo().getCode(), errMsg, null);
        return new ResponseEntity<>(serverResponse, e.getStatus());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<GenericServerResponse> handleIOException(final HttpServletResponse response, IOException e) {
        log.error(DEFAULT_EXCEPTION_CAUSE_FORMAT, e.getClass(), e.getMessage());
        GenericServerResponse serverResponse = new GenericServerResponse(ErrorConstants.IO_EXCEPTION, e.getMessage(), null);
        return new ResponseEntity<>(serverResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericServerResponse> handleMethodArgumentNotValidException(
            final HttpServletResponse response, MethodArgumentNotValidException ex) {
        List<String> errorMsg =
                ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
        String message = String.join(",", errorMsg) + " must Not be null";
        GenericServerResponse serverResponse =
                new GenericServerResponse(ErrorConstants.MISSING_REQUEST_BODY_FIELD, message, null);
        return new ResponseEntity<>(serverResponse, HttpStatus.OK);
    }

}

