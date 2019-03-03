package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

public class YYQueryVehicelRespDTO extends BaseYYGpsStaticDTO {

	public static class Vehicel{
		String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	ArrayList<Vehicel> data = new ArrayList<Vehicel>();

	public ArrayList<Vehicel> getData() {
		return data;
	}

	public void setData(ArrayList<Vehicel> data) {
		this.data = data;
	}
}
