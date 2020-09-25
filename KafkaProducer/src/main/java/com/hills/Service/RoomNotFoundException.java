package com.hills.Service;

import org.springframework.stereotype.Component;


public class RoomNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public RoomNotFoundException(String exception) {
		super(exception);
	}
	
	public RoomNotFoundException() {
		
	}

}
