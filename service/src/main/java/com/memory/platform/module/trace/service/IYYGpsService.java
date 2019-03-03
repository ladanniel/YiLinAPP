package com.memory.platform.module.trace.service;

import java.util.Map;

import com.memory.platform.module.trace.dto.StaticLoginResponseDTO;
import com.memory.platform.module.trace.dto.YYAddClientRespDTO;
import com.memory.platform.module.trace.dto.YYAddGroupRespDTO;
import com.memory.platform.module.trace.dto.YYAddVehicleReqDTO;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO;
import com.memory.platform.module.trace.dto.YYLocationResponseDTO;
import com.memory.platform.module.trace.dto.YYQueryClientResponseDTO;
import com.memory.platform.module.trace.dto.YYQueryGroupRespDTO;
import com.memory.platform.module.trace.dto.YYQueryVehicelRespDTO;
import com.memory.platform.module.trace.dto.YYStandResponseDTO;

public interface IYYGpsService {
	/**
	 * 获取数据字典表
	 * 
	 * @return
	 */
	Map<String, Object> getDicData();

	/**
	 * 修改客户
	 * 
	 * @param id
	 * @param compyname
	 * @param phone
	 * @param officeaddrs
	 * @return
	 */
	YYQueryClientResponseDTO updateClient(String id, String compyname, String phone, String officeaddrs);

	/**
	 * 删除客户
	 * 
	 * @param id
	 * @return
	 */
	YYQueryClientResponseDTO deleteClient(String id);

	/**
	 * 静态接口登陆 同一个会话
	 * 
	 * @return
	 */
	StaticLoginResponseDTO staticLogin();

	/**
	 * 添加客户
	 * 
	 * @param compyname
	 *            企业名称
	 * @param phone
	 *            企业联系电话
	 * @param officeaddrs
	 *            办公地址
	 */
	YYAddClientRespDTO addClient(String compyname, String phone, String officeaddrs);

	/**
	 * 查询客户
	 * 
	 * @param colNameAndValue
	 *            查询条件MAP 列明和列值
	 */
	YYQueryClientResponseDTO queryClient(Map<String, String> colNameAndValue);

	/**
	 * 添加分组
	 * 
	 * @param req
	 * @return
	 */
	YYAddGroupRespDTO AddGroup(String groupName, int userCount, int vhcCount, int clientID);

	/**
	 * 修改分组
	 * 
	 * @param groupID
	 * @param groupName
	 * @param userCount
	 * @param vhcCount
	 * @param ClientID
	 * @return
	 */
	YYAddGroupRespDTO updateGroup(String groupID, String groupName, int userCount, int vhcCount, int ClientID);

	/**
	 * 删除group
	 * 
	 * @param groupID
	 * @return
	 */
	YYAddGroupRespDTO deleteGroup(String groupID);

	/**
	 * 查询分组
	 * 
	 * @param groupName
	 * @return
	 */
	YYQueryGroupRespDTO queryGroup(String groupName);

	/**
	 * 添加车辆
	 * 
	 * @param req
	 * @return
	 */
	YYStandResponseDTO addVehicle(YYAddVehicleReqDTO req);

	/**
	 * 删除车辆
	 * 
	 * @param req
	 * @return
	 */
	YYStandResponseDTO deleteVehicle(String id);

	/**
	 * 修改车辆信息
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	YYStandResponseDTO updateVehicle(String id, YYAddVehicleReqDTO req);

	/**
	 * 获取分组车辆
	 * 
	 * @return
	 */
	YYGroupResponseDTO getGroup();

	/**
	 * 获取车辆位子信息
	 * 
	 * @param vid
	 *            车辆id
	 * @param vKey
	 *            车辆授权码
	 * @return
	 */
	YYLocationResponseDTO getVeLocation(String vid, String vKey);

	/**
	 * 获取所有车辆
	 * 
	 * @return
	 */
	YYGroupResponseDTO getVehicles();

	YYQueryVehicelRespDTO queryVehicle(Map<String, String> colNameAndValue);
	
	String getClientId();
}
