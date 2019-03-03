package com.memory.platform.module.own.service;

import java.util.Map;

public interface IRobOrderConfirmOwnService {

	Map<String, Object> getOwnWithGoodsBasic(String goodsBasicID);

	Map<String, Object> getOwnWithRobOrderConfirm(Map<String, Object> confirmMap);
	
}
