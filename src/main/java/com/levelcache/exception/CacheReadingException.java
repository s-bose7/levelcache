package com.levelcache.exception;

import com.levelcache.core.LevelCache;

/**
 * Exception thrown by a {@link LevelCache} when it encounters an error while
 * attempting to read data.
 */
public class CacheReadingException extends RuntimeException {

    private static final long serialVersionUID = -8474075524692815336L;

    /**
     * Constructs a new CacheReadingException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public CacheReadingException(String message) {
        super(message);
    }

    /**
     * Constructs a new CacheReadingException with the specified detail message
     * and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CacheReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CacheReadingException with the specified cause and a
     * detail message of (cause==null ? null : cause.toString()) (which typically
     * contains the class and detail message of cause). This constructor is useful
     * for exceptions that are little more than wrappers for other throwables (for
     * example, {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CacheReadingException(Throwable cause) {
        super(cause);
    }

}
