package com.order.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CustomException(String exception) {
		super(exception);
	}
}
