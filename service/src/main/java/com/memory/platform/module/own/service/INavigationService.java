package com.memory.platform.module.own.service;

import java.util.Map;

public interface INavigationService {

	Map<String, Object> getRobOrderRecordConfirmNavigationAction(String robOrderConfirmID);

	Map<String, Object> getRobOrderRecordNavigationAction(String robOrderRecordID);

	Map<String, Object> getGoodsBasicNavigationAction(String goodsBasicID);

}
