package com.memory.platform.module.order.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LgisticsNode {
	private Long time;
	private String desc;
	
	public String getTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (sdf.format(new Date(Long.valueOf(String.valueOf(this.getTime())+"000"))));
	}
	
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
