package com.salon.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String mesage) {
		super(mesage);
	}
	
	public UserNotFoundException() {
		super();
	}
}
