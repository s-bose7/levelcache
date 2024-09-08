package live.levelcache.exception;

public class RemoveLevelException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RemoveLevelException(String message) {
        super(message);
    }
	
	public RemoveLevelException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public RemoveLevelException(Throwable cause) {
        super(cause);
    }
}
