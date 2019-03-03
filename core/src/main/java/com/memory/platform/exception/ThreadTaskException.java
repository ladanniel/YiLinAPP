package com.memory.platform.exception;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月27日 上午11:32:53 
* 修 改 人： 
* 日   期： 
* 描   述： 线程任务异常
* 版 本 号：  V1.0
 */
public class ThreadTaskException extends BaseRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7311823072748308866L;
	public ThreadTaskException() {
		super();
	}
	public ThreadTaskException(String string) {
		super(string);
		this.code = "task";
	}
	public ThreadTaskException(String code, String string) {
		super(string);
		this.code = code;
	}

}
