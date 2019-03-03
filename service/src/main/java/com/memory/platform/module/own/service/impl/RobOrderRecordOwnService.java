package com.memory.platform.module.own.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.own.service.IRobOrderRecordOwnService;

/**
 * 抢单功能权限
 * 
 * @author lil
 *
 */
@Service
public class RobOrderRecordOwnService extends BaseOwnService implements IRobOrderRecordOwnService {
	@Autowired
	IGoodsBasicService goodsBasicSvr;
	@Autowired
	IRobOrderRecordService robOrderRecordSvr;
	// 是否可验证货物抢单
	public static final String canVerifyRobOrderRecord = "ownCanVerifyRobOrderRecord";

	public static final String canTruckViewRobOrderRecord = "ownCanTruckViewRobOrderRecord";

	// 判断货主是否可以抢单审核
	@Override
	public Map<String, Object> consignorCanVerifyGoodsBasic(String goodsBasicID) {
		Map<String, Object> ret = new HashMap<>();
		do {
			Account account = UserUtil.getAccount();
			if (account == null || super.isConsignor() == false) {

				ret.put(canVerifyRobOrderRecord, false);
				break;
			}
			GoodsBasic goodsBasic = goodsBasicSvr.getGoodsBasicById(goodsBasicID);
			if (goodsBasic == null || goodsBasic.getAccount().getId().equals( account.getId())==false) {
				ret.put(canVerifyRobOrderRecord, false);
				break;

			}
			ret.put(canVerifyRobOrderRecord, true);
		} while (false);

		return ret;
	}

	// 判断司机和车队是否可以看到这条抢单记录
	@Override
	public Map<String, Object> truckCanViewRobOrderRecord(String roborderRecordID) {
		Map<String, Object> ret = new HashMap<>();
		do {
			Account account = UserUtil.getAccount();
			if (account == null || (super.isConsignor() == true)) {

				ret.put(canTruckViewRobOrderRecord, false);
				break;
			}
			RobOrderRecord robOrderRecord = robOrderRecordSvr.getRecordById(roborderRecordID);
			if (robOrderRecord == null || robOrderRecord.getAccount().getId().equals(account.getId()) ==false ) {
				ret.put(canTruckViewRobOrderRecord, false);
				break;

			}
			ret.put(canTruckViewRobOrderRecord, true);
		} while (false);

		return ret;
	}

}
