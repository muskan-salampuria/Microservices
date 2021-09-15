package com.epam.library.exception;

public class MaximumBookIssuedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaximumBookIssuedException(String message) {
		super(message);
	}
}
