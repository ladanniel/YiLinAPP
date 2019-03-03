package com.memory.platform.core;

public class JdbcContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setJdbcType(String jdbcType) {
		contextHolder.set(jdbcType);
	}
	
	
	public static String getJdbcType() {
		return (String) contextHolder.get();
	}

	public static void clearJdbcType() {
		contextHolder.remove();
	}
}
