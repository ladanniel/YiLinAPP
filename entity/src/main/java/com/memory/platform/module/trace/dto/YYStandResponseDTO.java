package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

public class YYStandResponseDTO extends BaseYYGpsStaticDTO {
	public static class DataInfo{
		String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	ArrayList<DataInfo> data= new ArrayList<DataInfo>();
	public ArrayList<DataInfo> getData() {
		return data;
	}
	public void setData(ArrayList<DataInfo> data) {
		this.data = data;
	}
	
}
