package org.oxtail.exception;

/**
 * Base {@link RuntimeException} for oxtail code
 * 
 * @author liam knox
 */
public class OxtailRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OxtailRuntimeException() {
		super();
	}

	public OxtailRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public OxtailRuntimeException(String message) {
		super(message);
	}

	public OxtailRuntimeException(Throwable cause) {
		super(cause);
	}

}
