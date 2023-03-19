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
	
	//Custom Exceptions
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
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorDetails> categoryExceptionHandler(CategoryException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetails> orderExceptionHandler(OrderException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrderDetailException.class)
	public ResponseEntity<ErrorDetails> orderDetailExceptionHandler(OrderDetailException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ShipperException.class)
	public ResponseEntity<ErrorDetails> shipperExceptionHandler(ShipperException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SupplierException.class)
	public ResponseEntity<ErrorDetails> supplierExceptionHandler(SupplierException ce, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ce.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	//Parent of all the exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> loginExceptionHandler(Exception e, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	//Handler not found exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException ne, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), ne.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	//Validation exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException mae) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), "Validation Error", mae.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	

}
