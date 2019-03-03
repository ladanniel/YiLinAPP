package com.memory.platform.exception;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:29:22 
* 修 改 人： 
* 日   期： 
* 描   述： 
* 版 本 号：  V1.0
 */
public class BaseRuntimeException extends RuntimeException {

	protected String code;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4259584568892271123L;
	public BaseRuntimeException() {
		super();
	}
	public BaseRuntimeException(String string) {
		super(string);
	}
	public BaseRuntimeException(String string, Throwable throwable) {
		super(string, throwable);
	}
	public BaseRuntimeException(String code, String string) {
		super(string);
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	
	public void recoredException(){
		
	}
}
