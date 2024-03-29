package com.in28minutes.productcatalogueservice.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.in28minutes.productcatalogueservice.user.UserNotFoundException;

//Apply this to all controllers
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		
		//Create a new instance of our specific exception bean
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoiundException(UserNotFoundException ex, WebRequest request) throws Exception {
		
		//Create a new instance of our specific exception bean
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));

		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Create a new instance of our specific exception bean
		//ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),"Validation Failed", ex.getBindingResult().toString());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),"Validation Failed", ex.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
}
