package com.memory.platform.entity.capital;

import java.io.Serializable;

public class ResData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String	code;
	String  message;
	String  data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}
