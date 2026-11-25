package com.stocksense.inventory.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlenotFound(ResourceNotFoundException ex , HttpServletRequest request){
        return build(HttpStatus.NOT_FOUND , ex.getMessage() , request , null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String , String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.putIfAbsent(error.getField() , error.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST , "Validation failed" , request , fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex , HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST , "Malformed or unreadable request body" , request , null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handletypeMisMatch(MethodArgumentTypeMismatchException ex , HttpServletRequest request){
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST , message , request , null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex , HttpServletRequest request){
        log.error("Unexpected error on {}",request.getRequestURI(),ex); // full details stay in the log
        return build(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong. Please try again later",request,null);

    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(ConflictException ex, HttpServletRequest request){
        return build(HttpStatus.CONFLICT , ex.getMessage(),request,null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(DataIntegrityViolationException ex , HttpServletRequest request){
        log.warn("Data integrity violation on {}: {}", request.getRequestURI() ,ex.getMostSpecificCause().getMessage());
        return build(HttpStatus.CONFLICT,"Request conflicts with existing data",request,null);
    }

    public ResponseEntity<ErrorResponse> build(HttpStatus status , String message, HttpServletRequest request, Map<String,String> fieldErrors){
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}
