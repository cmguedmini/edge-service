package com.poc.facade.pocfacade.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BAD_REQUEST_ERROR_MSG = "Unable to process the request";
	
	public BadRequestException() {
		super(BAD_REQUEST_ERROR_MSG);
	}
	
	public BadRequestException(String message) {
		super(message);
	}
	
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
