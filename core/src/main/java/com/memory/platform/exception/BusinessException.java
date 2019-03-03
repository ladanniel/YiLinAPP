package com.memory.platform.exception;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:30:22 
* 修 改 人： 
* 日   期： 
* 描   述： 业务异常
* 版 本 号：  V1.0
 */
public class BusinessException extends BaseRuntimeException {
	public BusinessException() {
		super();
	}
	public BusinessException(String string) {
		super(string);
		this.code = "";
	}
	public BusinessException(String code, String string) {
		super(string);
		this.code = code;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4391136148192924221L;

}
