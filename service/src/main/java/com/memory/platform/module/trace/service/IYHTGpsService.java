package com.memory.platform.module.trace.service;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.memory.platform.entity.gps.VechicleGps;

public interface IYHTGpsService {

	 

	Map<String, Object> getAllVehicle();

	void autoSaveVechicle();

	VechicleGps getGpsDataWithDeviceID(String deviceID);

	List<VechicleGps> getGpsDataWithDeviceIDs(List<String> deviceIDS);

	VechicleGps getGpsDataWithCardNo(Jedis client, String cardNo);

	VechicleGps getGpsDataWithCardNo(String cardNo);

}
