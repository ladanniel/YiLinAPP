package com.memory.platform.exception;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:30:56 
* 修 改 人： 
* 日   期： 
* 描   述： 数据异常
* 版 本 号：  V1.0
 */
public class DataBaseException extends BaseRuntimeException {

	public DataBaseException() {
		super();
		this.code = "db";
	}
	public DataBaseException(String message) {
		super(message);
		this.code = "db001";
	}
	public DataBaseException(String code, String string) {
		super(string);
		this.code = code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4259584568892271123L;
	
}
