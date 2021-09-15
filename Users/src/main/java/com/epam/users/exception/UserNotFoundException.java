package com.epam.users.exception;

public class UserNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6573196431296640935L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
