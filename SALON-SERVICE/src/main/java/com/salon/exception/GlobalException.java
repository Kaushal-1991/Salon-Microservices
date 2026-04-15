package com.salon.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salon.dto.SalonExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<SalonExceptionResponse> handleResourceNotFound(ResourceNotFound ex,
			HttpServletRequest request){
		
		SalonExceptionResponse exceptionResponse = SalonExceptionResponse.builder()
				                                                         .message(ex.getMessage())
				                                                         .path(request.getRequestURI())
				                                                         .dateTime(LocalDateTime.now())
				                                                         .build();
		return new ResponseEntity<SalonExceptionResponse>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SalonExceptionResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
    	
    	
    	 // Extract first error message
        String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .findFirst()
                                .orElse("Validation error");

        SalonExceptionResponse response = SalonExceptionResponse.builder()
                .message(errorMessage)
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

    	

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
