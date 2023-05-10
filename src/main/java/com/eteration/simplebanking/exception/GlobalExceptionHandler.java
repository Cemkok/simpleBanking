package com.eteration.simplebanking.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eteration.simplebanking.exception.customException.AccountNotFoundException;
import com.eteration.simplebanking.exception.customException.CustomerAlreadyExistException;
import com.eteration.simplebanking.exception.customException.InsufficientBalanceException;
import com.eteration.simplebanking.exception.customException.NegativeValueEntryException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException e) {
		ErrorResponse error = new ErrorResponse("failed",HttpStatus.BAD_REQUEST, "Error :" + e.getMessage(),
				e.getClass().getSimpleName());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NegativeValueEntryException.class)
	public ResponseEntity<ErrorResponse> handleNegativeValueEntryException(NegativeValueEntryException e) {
		ErrorResponse error = new ErrorResponse("failed",HttpStatus.BAD_REQUEST, "Error :" + e.getMessage(),
				e.getClass().getSimpleName());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();

		ErrorResponse response = new ErrorResponse("failed",HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage(),
				"MethodArgumentNotValidException");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {
	
		ErrorResponse error = new ErrorResponse("failed",HttpStatus.BAD_REQUEST, "Error :" + e.getMessage(),
				e.getClass().getSimpleName());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(CustomerAlreadyExistException e) {
	
		ErrorResponse error = new ErrorResponse("failed",HttpStatus.BAD_REQUEST, "Error :" + e.getMessage(),
				e.getClass().getSimpleName());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getSimpleName());
	}
}
