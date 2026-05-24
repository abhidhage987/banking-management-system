package com.bank.exception;

import com.bank.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice

public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<Map<String, String>>
	handleValidationException(
	        MethodArgumentNotValidException ex
	) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult()
	            .getFieldErrors()
	            .forEach(error -> {

	                errors.put(
	                        error.getField(),
	                        error.getDefaultMessage()
	                );
	            });

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(errors);
	}

	@ExceptionHandler(ResourceNotFoundException.class)

	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

		ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(InsufficientBalanceException.class)

	public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {

		ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)

	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

		ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}