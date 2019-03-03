package com.memory.platform.module.trace.dto;

public class BaseYYGpsStaticDTO {
	Boolean flag; //		是否成功
	String msg;//		返回提示信息
	String data_sql	;//	待用
	public String getData_sql() {
		return data_sql;
	}
	public void setData_sql(String data_sql) {
		this.data_sql = data_sql;
	} 
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
