package com.epam.library.exception;

public class NoSuchBookForRelease extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchBookForRelease(String message) {
		super(message);
	}
}
