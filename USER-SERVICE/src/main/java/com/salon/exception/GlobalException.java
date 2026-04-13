package com.salon.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salon.dto.ErrorResponse;
import com.salon.dto.UserExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserExceptionResponse> handleUserNotfoundException(UserNotFoundException ex,
			HttpServletRequest request){
		
		UserExceptionResponse userExceptionResponse = 	UserExceptionResponse.builder().message(ex.getMessage())
		                               .path(request.getRequestURI())
		                               .dateTime(LocalDateTime.now()).build();
		
		return new ResponseEntity<>(userExceptionResponse,HttpStatus.NOT_FOUND);
	}
	

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
    	
    	
    	 // Extract first error message
//        String errorMessage = ex.getBindingResult()
//                                .getFieldErrors()
//                                .stream()
//                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                                .findFirst()
//                                .orElse("Validation error");
//
//        UserExceptionResponse response = UserExceptionResponse.builder()
//                .message(errorMessage)
//                .path(request.getRequestURI())
//                .dateTime(LocalDateTime.now())
//                .build();

    	List<String> errors = ex.getBindingResult()
    	        .getFieldErrors()
    	        .stream()
    	        .map(error -> error.getField() + ": " + error.getDefaultMessage())
    	        .toList();

    	ErrorResponse response = ErrorResponse.builder()
                .errors(errors)
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

