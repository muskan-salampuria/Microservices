package com.epam.books.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,WebRequest req) {
		List<String> inputErrors=new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(err->{
			inputErrors.add(err.getDefaultMessage());
		});
		ExceptionResponse error = new ExceptionResponse(new Date().toString(),HttpStatus.BAD_REQUEST.name(),inputErrors.toString(),req.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookIDNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleTaskIDNotFound(BookIDNotFoundException ex,WebRequest req) {
		ExceptionResponse error = new ExceptionResponse(new Date().toString(),HttpStatus.NOT_FOUND.name(),ex.getMessage(),req.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
}
