package com.epam.books.exception;

public class BookIDNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6573196431296640935L;

	public BookIDNotFoundException(String message) {
		super(message);
	}

}
