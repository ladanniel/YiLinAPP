package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

import antlr.collections.List;

public class YYAddGroupRespDTO extends BaseYYGpsStaticDTO {
	public static class GroupInfomation{
		int id ;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
	}
	 
	ArrayList<GroupInfomation> data = new ArrayList<GroupInfomation>();	//Arr	分组信息数组
	 
	 
	 
	public ArrayList<GroupInfomation> getData() {
		return data;
	}
	public void setData(ArrayList<GroupInfomation> data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	int id	;
	

}
