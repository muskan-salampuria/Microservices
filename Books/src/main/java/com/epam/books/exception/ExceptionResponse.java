package com.epam.books.exception;

public class ExceptionResponse {

	String timestamp;
	String status;
	String error;
	String trace;

	public ExceptionResponse(String timestamp, String status, String error, String trace) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.trace = trace;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getTrace() {
		return trace;
	}

}
