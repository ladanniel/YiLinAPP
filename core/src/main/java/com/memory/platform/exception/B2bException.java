package com.memory.platform.exception;

public class B2bException extends BaseRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115570752313788555L;
	public B2bException() {
		super();
	}
	public B2bException(String string) {
		super(string);
		this.code = "";
	}
	public B2bException(String code, String string) {
		super(string);
		this.code = code;
	}
	/**
	 * 
	 */
}
