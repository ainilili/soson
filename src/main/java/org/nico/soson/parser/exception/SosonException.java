package org.nico.soson.parser.exception;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class SosonException extends RuntimeException{

	private static final long serialVersionUID = 3231650171846541753L;

	public SosonException() {
		super();
	}

	public SosonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SosonException(String message, Throwable cause) {
		super(message, cause);
	}

	public SosonException(String message) {
		super(message);
	}

	public SosonException(Throwable cause) {
		super(cause);
	}

}
