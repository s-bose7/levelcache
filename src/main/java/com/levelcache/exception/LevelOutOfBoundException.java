package com.levelcache.exception;

public class LevelOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LevelOutOfBoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LevelOutOfBoundException(Throwable cause) {
		super(cause);
	}
	
	public LevelOutOfBoundException(String message) {
		super(message);
	}

}
