package com.memory.platform.module.own.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import net.sourceforge.jtds.jdbc.DateTime;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsBasicStockType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecord.Status;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.order.service.impl.RobOrderRecordService;
import com.memory.platform.module.own.service.IGoodsBasicOwnService;

/**
 * 货物功能服务
 * 
 * @author lil
 *
 */
@Service
public class GoodsBasicOwnService extends BaseOwnService implements
		IGoodsBasicOwnService {
	public static final String canRob = "ownCanRob";
	public static final String robOrderID = "ownRobOrderRecordId";
	public static final String canUpOnline = "ownCanUpOnline"; // 是否可上线
	public static final String canDownOnline = "ownCanDownOnline";// 是否可下线
	Logger logger = Logger.getLogger(GoodsBasicOwnService.class);
	@Autowired
	IRobOrderRecordService robOrderRecordService;
	@Autowired
	IGoodsBasicService goodsBasicService;

	/**
	 * 获取我对物品的操作权限
	 * 
	 * @param goodsBasicID
	 * @return
	 */
	@Override
	public Map<String, Object> getMyOwnWithGoodsBasicID(String goodsBasicID) {
		Map<String, Object> ret = new HashMap<>();
		Account account = UserUtil.getAccount();
		if (isTruck() || isDriver()) {
			List<RobOrderRecord> records = robOrderRecordService
					.getMyRobOrderRecordWithGoodsBasicID(goodsBasicID, account);
			List<RobOrderRecord> aRecords = new ArrayList<>();
			for (RobOrderRecord robOrderRecord : records) {
				if (robOrderRecord.getStatus() == Status.apply
						|| robOrderRecord.getStatus() == Status.dealing) {
					aRecords.add(robOrderRecord);

				}
			}
			if (aRecords == null || aRecords.size() == 0) {
				Map<String, Object> goodsBasicStockTypes = goodsBasicService
						.getMyGoodsBasicStockTypeWithGoodsBasicID(goodsBasicID,
								account);
				// lix 2017-09-12 添加 判断当前货源是否可以操作抢单
				GoodsBasic goodsBasic1 = goodsBasicService
						.getGoodsBasicById(goodsBasicID);
				// lix 2017-09-05 添加 判断当前登录用户是否可以操作抢单:1）、当前车队用户的车辆类型没有货源发布时填写的车辆类型不能操作抢单，
				//2）、不是车队管理员不能操作抢单，3）、已下线的货源信息不能操作抢单
				if (goodsBasicStockTypes.get("cnt").toString() == "0"
						|| account.getIsAdmin() == false||goodsBasic1.getOnLine()==false) {
					ret.put(canRob, false);
				} else {
					ret.put(canRob, true);
				}
			} else {
				ret.put(canRob, false);

				ret.put(robOrderID, aRecords.get(aRecords.size() - 1).getId());
			}
		} else { // 货主
			GoodsBasic goodsBasic = goodsBasicService
					.getGoodsBasicById(goodsBasicID);
			if (goodsBasic.getAccount().getId().equals(account.getId())) {
				if (goodsBasic.isOnLine()) { // 是否可以下线的权限
					double surPlusWidth = goodsBasic.getTotalWeight()
							- goodsBasic.getEmbarkWeight();
					DateFormat df = DateFormat.getDateInstance();
					if (surPlusWidth > 0
							&& (goodsBasic.isLongTime() || df.format(goodsBasic
									.getFiniteTime()).compareTo(df.format(new Date())) >= 0)) {//lix 比较截止时间是否大于或等于当前时间，因截止时间又可能为当前时间
						ret.put(canDownOnline, true);
					}

				}else {
					
					double surPlusWidth = goodsBasic.getTotalWeight()
							- goodsBasic.getEmbarkWeight();
					DateFormat df1 = DateFormat.getDateInstance();
					if (surPlusWidth > 0
							&& (goodsBasic.isLongTime() || df1.format(goodsBasic
									.getFiniteTime()).compareTo(df1.format(new Date())) >= 0)) {//lix 比较截止时间是否大于或等于当前时间，因截止时间又可能为当前时间
						ret.put(canUpOnline, true);
					}
				}
			}
			ret.put(canRob, false);
		}
		return ret;
	}

}
