package com.levelcache.exception;

public class LevelRemoveException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LevelRemoveException(String message) {
        super(message);
    }
	
	public LevelRemoveException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public LevelRemoveException(Throwable cause) {
        super(cause);
    }
}
