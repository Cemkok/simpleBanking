package com.eteration.simplebanking.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {

	private String status;
	private HttpStatus httpStatus;
	private String message;
	private String error;
	

	public ErrorResponse(String status, String message, String ExceptionClassName) {
		this.status = status;
		this.message = message;
		this.error = ExceptionClassName;
	}

	public ErrorResponse(String status, HttpStatus httpStatus, String message, String ExceptionClassName) {
		this.status =status;
		this.httpStatus = httpStatus;
		this.message = message;
		this.error = ExceptionClassName;
	}

}
