package org.nico.soson.exception;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class ReflectException extends SosonException{

	private static final long serialVersionUID = 4611907615415503886L;

	public ReflectException() {
		super();
	}

	public ReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectException(String message) {
		super(message);
	}

	public ReflectException(Throwable cause) {
		super(cause);
	}

}
