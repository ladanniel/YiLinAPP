package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

public class YYGroupResponseDTO extends BaseYYResponseDTO {
	public static class Vehicle{
		 
		 int id;
		String name;
		String vKey;
	 
		public int getId() {
			return id;
		}
		/**
		 * @author rog
		 *	车牌号
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return 授权码
		 */
		public String getvKey() {
			return vKey;
		}
		public void setId(int id) {
			this.id = id;
		}
		 /**
		 * @param name 车牌号
		 */
		public void setName(String name) {
			this.name = name;
		}
		 public void setvKey(String vKey) {
			this.vKey = vKey;
		}
	 }

	
	public static class YYGroup  {
		//分组id
		int id ;
		//分组名称
		String name;
		//车辆
		ArrayList<Vehicle> vehicles = new ArrayList<YYGroupResponseDTO.Vehicle>();
		
		public int getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public ArrayList<Vehicle> getVehicles() {
			return vehicles;
		}
		public void setId(int id) {
			this.id = id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setVehicles(ArrayList<Vehicle> vehicles) {
			this.vehicles = vehicles;
		}
		 
	}
	
	
	 ArrayList<YYGroup> groups = new ArrayList<YYGroupResponseDTO.YYGroup>();
	 public ArrayList<YYGroup> getGroups() {
		return groups;
	}


	public void setGroups(ArrayList<YYGroup> groups) {
		this.groups = groups;
	}


	
}
