package com.memory.platform.exception;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:31:47 
* 修 改 人： 
* 日   期： 
* 描   述： Session异常
* 版 本 号：  V1.0
 */
public class SessionException extends BaseRuntimeException {
	public SessionException() {
		super("您已退出登录或长时间没有在网站上活动，为了保证您的账号安全，请您重新登录。");
		this.code = "se";
	}
	public SessionException(String string) {
		super(string);
		this.code = "se";
	}
	public SessionException(String code, String string) {
		super(string);
		this.code = code;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4259584568892271123L;
	
}
