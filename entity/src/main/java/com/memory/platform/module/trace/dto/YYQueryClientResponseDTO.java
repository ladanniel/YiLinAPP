package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

public class YYQueryClientResponseDTO  extends BaseYYGpsStaticDTO{
	public static class Client{
		int id ;
		String compyname;
		String  compyperson;
		String phone;
		String  officeaddrs;
		public String getCompyname() {
			return compyname;
		}
		public String getCompyperson() {
			return compyperson;
		}
		public int getId() {
			return id;
		}
		public String getOfficeaddrs() {
			return officeaddrs;
		}
		public String getPhone() {
			return phone;
		}
		public void setCompyname(String compyname) {
			this.compyname = compyname;
		}
		public void setCompyperson(String compyperson) {
			this.compyperson = compyperson;
		}
		public void setId(int id) {
			this.id = id;
		}
		public void setOfficeaddrs(String officeaddrs) {
			this.officeaddrs = officeaddrs;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
	
	int totalCount ;
	ArrayList<Client> data = new ArrayList<YYQueryClientResponseDTO.Client>();
	
	public ArrayList<Client> getData() {
		return data;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setData(ArrayList<Client> data) {
		this.data = data;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
