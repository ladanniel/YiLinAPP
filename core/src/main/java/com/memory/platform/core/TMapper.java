package com.memory.platform.core;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.springframework.jdbc.core.RowMapper;


public class TMapper<T> implements RowMapper<T> {

	private Class<T> cls;

	public Class<T> getCls() {
		return cls;
	}

	public void setCls(Class<T> cls) {
		this.cls = cls;
	}

	public TMapper(Class<T> tClass) {
		this.cls = tClass;
	}

	public T mapRow(ResultSet rs, int rowNum) {
		T t = null;
		try {
			t = this.cls.newInstance();

			ResultSetMetaData metaData = rs.getMetaData();
			int len = metaData.getColumnCount();
			for (int i = 0; i < len; i++) {
				String fieldName = metaData.getColumnName(i + 1);
				//sqlserver分页时特有的字段，排除掉。
				if("ROW_NUMBER".equals(fieldName)){
					continue;
				}
				
				Object fieldValue = rs.getObject(fieldName);
				Field field = this.cls.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(t, fieldValue);
			}
		} catch (Exception e) {
			throw new RuntimeException(AppUtil.getExMsg(e.getMessage() + " - 数据表字段没有实体属性对应！"));
		} 
		return t;
	}

}
