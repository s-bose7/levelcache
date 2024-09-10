package com.levelcache.exception;

/**
 * Exception thrown when there is an error during cache initialization.
 */
public class CacheInitializationException extends Exception {

    private static final long serialVersionUID = -1100595628325513962L;

	/**
     * Constructs a new CacheInitializationException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public CacheInitializationException(String message) {
        super(message);
    }

    /**
     * Constructs a new CacheInitializationException with the specified detail message
     * and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CacheInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CacheInitializationException with the specified cause and a
     * detail message of (cause==null ? null : cause.toString()) (which typically
     * contains the class and detail message of cause). This constructor is useful
     * for exceptions that are little more than wrappers for other throwables (for
     * example, {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CacheInitializationException(Throwable cause) {
        super(cause);
    }

}
