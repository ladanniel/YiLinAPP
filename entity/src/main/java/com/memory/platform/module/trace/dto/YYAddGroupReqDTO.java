package com.memory.platform.module.trace.dto;

public class YYAddGroupReqDTO {
	String groupName;
	int userId = 0;
	String exKey;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getExKey() {
		return exKey;
	}
	public void setExKey(String exKey) {
		this.exKey = exKey;
	}
}
