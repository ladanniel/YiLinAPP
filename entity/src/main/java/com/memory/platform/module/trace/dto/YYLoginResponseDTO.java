package com.memory.platform.module.trace.dto;

public class YYLoginResponseDTO  extends BaseYYResponseDTO{
	 
	int  uid;
	String uKey;
	public int getUid() {
		return uid;
	}
	public String getuKey() {
		return uKey;
	}
	 
	 
	public void setUid(int uid) {
		this.uid = uid;
	}
	 public void setuKey(String uKey) {
		this.uKey = uKey;
	}
	 
}
