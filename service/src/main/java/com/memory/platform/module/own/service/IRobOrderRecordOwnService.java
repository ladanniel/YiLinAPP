package com.memory.platform.module.own.service;

import java.util.Map;

public interface IRobOrderRecordOwnService {

	Map<String, Object> consignorCanVerifyGoodsBasic(String goodsBasicID);

	Map<String, Object> truckCanViewRobOrderRecord(String roborderRecordID);

}
