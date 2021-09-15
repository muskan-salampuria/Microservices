package com.epam.users.exception;

public class UsernameExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameExistsException(String message) {
		super(message);
	}
}
