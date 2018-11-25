package org.nico.soson.parser.exception;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class UnSupportedException extends SosonException{

	private static final long serialVersionUID = 4611907615415503886L;

	public UnSupportedException() {
		super();
	}

	public UnSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnSupportedException(String message) {
		super(message);
	}

	public UnSupportedException(Throwable cause) {
		super(cause);
	}

}
