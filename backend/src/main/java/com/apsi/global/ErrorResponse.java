package com.apsi.global;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	// ********************************************************************************************
	// POLA

	private HttpStatus status;
	private String message;
	private List<String> errors;

	// ********************************************************************************************
	// METODY

	public static ResponseEntity<?> getEntity(HttpStatus status, String message, String errorMessage) {
		ErrorResponse errorResponse = new ErrorResponse(status, message, errorMessage);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	public static <T extends RuntimeException> ResponseEntity<?> getEntity(T exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), "");
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	// ********************************************************************************************
	// KONSTRUKTORY

	public ErrorResponse(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ErrorResponse(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

	// ********************************************************************************************
	// GETTERY / SETTERY

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
