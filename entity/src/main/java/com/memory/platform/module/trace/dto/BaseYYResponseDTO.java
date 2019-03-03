package com.memory.platform.module.trace.dto;

public class BaseYYResponseDTO {
	String version;
	boolean success;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
