package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

import antlr.collections.List;

public class StaticLoginResponseDTO  extends BaseYYGpsStaticDTO{
	public class UserInfomatrion {
		int id ;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
	 
	ArrayList<UserInfomatrion> data= new ArrayList<UserInfomatrion>()	;//arr	用户信息数组
	int id	;//	用户id
 
	String jsessionid;	//	会话ID
	String exkey;//		登录加密key  (登录成功后，每次传入exkey=xxxx类似sessionid作用)
	public ArrayList<UserInfomatrion> getData() {
		return data;
	}
	public String getUserID(){
		
		if(data.size()==0) return "";
		return data.get(0).getId()+"";
	}
 
	public String getExkey() {
		return exkey;
	}
	public int getId() {
		return id;
	}
	public String getJsessionid() {
		return jsessionid;
	}
	 
	public void setData(ArrayList<UserInfomatrion> data) {
		this.data = data;
	}
	 
	public void setExkey(String exkey) {
		this.exkey = exkey;
	}
	 
	public void setId(int id) {
		this.id = id;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	 

}
