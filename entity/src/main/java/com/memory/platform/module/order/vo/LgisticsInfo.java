package com.memory.platform.module.order.vo;

import java.util.List;

public class LgisticsInfo {
	
	private String msg;
	private Integer status;
	private List<LgisticsNode> context;
	
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<LgisticsNode> getContext() {
		return context;
	}
	public void setContext(List<LgisticsNode> context) {
		this.context = context;
	}

}
