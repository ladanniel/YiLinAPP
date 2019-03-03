package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

public class YYQueryGroupRespDTO extends BaseYYGpsStaticDTO {
	public static class Group {
		String id;
		String groupName;
		String createTime;
		String mintime;
		String maxtime;
		String userCount;
		String vhcCount;
		String clientId;
		public String getClientId() {
			return clientId;
		}
		public String getCreateTime() {
			return createTime;
		}
		public String getGroupName() {
			return groupName;
		}
		public String getId() {
			return id;
		}
		public String getMaxtime() {
			return maxtime;
		}
		public String getMintime() {
			return mintime;
		}
		public String getUserCount() {
			return userCount;
		}
		public String getVhcCount() {
			return vhcCount;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setMaxtime(String maxtime) {
			this.maxtime = maxtime;
		}
		public void setMintime(String mintime) {
			this.mintime = mintime;
		}
		public void setUserCount(String userCount) {
			this.userCount = userCount;
		}
		public void setVhcCount(String vhcCount) {
			this.vhcCount = vhcCount;
		}
	}
	ArrayList<Group> data = new ArrayList<YYQueryGroupRespDTO.Group>();
	public ArrayList<Group> getData() {
		return data;
	}
	public void setData(ArrayList<Group> data) {
		this.data = data;
	}
}
