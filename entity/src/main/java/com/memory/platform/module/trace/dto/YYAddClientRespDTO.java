package com.memory.platform.module.trace.dto;

import java.util.ArrayList;
import java.util.List;

 

/**
 * 添加客户返回的信息
 * @author rog
 *
 */
public class YYAddClientRespDTO extends BaseYYGpsStaticDTO {
	public class Client {
		//客户Id
		int id ;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
	}
	
	int id ;
	/**
	 *客户数组 
	 */
	List<Client> data = new ArrayList<Client>();
	
	public List<Client> getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public void setData(List<Client> data) {
		this.data = data;
	}

	public void setId(int id) {
		this.id = id;
	}
}
