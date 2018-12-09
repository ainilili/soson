package org.nico.soson.exception;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class DeserializeException extends SosonException{

	private static final long serialVersionUID = 1L;

	public DeserializeException() {
		super();
	}

	public DeserializeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeserializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeserializeException(String message) {
		super(message);
	}

	public DeserializeException(Throwable cause) {
		super(cause);
	}

}
