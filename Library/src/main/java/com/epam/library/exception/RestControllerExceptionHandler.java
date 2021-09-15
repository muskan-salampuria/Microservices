package com.epam.library.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import feign.FeignException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Map<String,String>> handleFeignException(FeignException fex,WebRequest req) {
		Map<String,String> response=new HashMap<>();
		ExceptionResponse error1 = new ExceptionResponse(new Date().toString(),HttpStatus.NOT_FOUND.name(),fex.getMessage().substring(160, 200),req.getDescription(false));
		System.out.println(error1.error.split("\":"));
		response.put("service", error1.error);
		response.put("trace", error1.trace);
		response.put("status", error1.status);
		response.put("timestamp", new Date().toString());
		response.put("feignError", fex.responseBody().toString());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(BookIDNotFoundException.class)
//	public ResponseEntity<ExceptionResponse> handleTaskIDNotFound(BookIDNotFoundException ex,WebRequest req) {
//		ExceptionResponse error = new ExceptionResponse(new Date().toString(),HttpStatus.NOT_FOUND.name(),ex.getMessage(),req.getDescription(false));
//		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//	}
}
