package org.nico.soson.exception;

/** 
 * 
 * @author nico
 * @email ainililia@163.com
 */

public class InstanceException extends SosonException{

	private static final long serialVersionUID = 4611907615415503886L;

	public InstanceException() {
		super();
	}

	public InstanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InstanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InstanceException(String message) {
		super(message);
	}

	public InstanceException(Throwable cause) {
		super(cause);
	}

}
