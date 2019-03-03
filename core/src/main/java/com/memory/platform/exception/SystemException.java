package com.memory.platform.exception;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:32:16 
* 修 改 人： 
* 日   期： 
* 描   述： 系统异常
* 版 本 号：  V1.0
 */
public class SystemException extends BaseRuntimeException {

	public SystemException() {
		super();
		this.code = "sys";
	}
	public SystemException(String string) {
		super(string);
		this.code = "sys001";
	}
	public SystemException(String string, Throwable throwable) {
		super(string, throwable);
		this.code = "sys001";
	}
	public SystemException(String code, String string) {
		super(string);
		this.code = code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391136148192924221L;

}
