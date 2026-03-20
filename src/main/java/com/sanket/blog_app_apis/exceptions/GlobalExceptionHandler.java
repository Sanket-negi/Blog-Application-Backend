package com.sanket.blog_app_apis.exceptions;

import com.sanket.blog_app_apis.payloads.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponses> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        String message = e.getMessage();
        ApiResponses apiResponses = new ApiResponses(message,false);
        return new ResponseEntity<ApiResponses>(apiResponses, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String FieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(FieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }
}
