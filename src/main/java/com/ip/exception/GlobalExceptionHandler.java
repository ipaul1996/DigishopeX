package com.ip.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SalesAnalysisNotFoundException.class)
	public ResponseEntity<ErrorDetails> SalesAnalysisNotFoundExceptionHandler(SalesAnalysisNotFoundException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CredentialException.class)
	public ResponseEntity<ErrorDetails> credentialExceptionHandler(CredentialException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> loginExceptionHandler(Exception e, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException ne, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ne.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException mae) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), "Validation Error", mae.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	

}
