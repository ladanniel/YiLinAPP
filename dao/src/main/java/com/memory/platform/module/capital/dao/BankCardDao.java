package com.memory.platform.module.capital.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午6:32:02 
* 修 改 人： 
* 日   期： 
* 描   述： 银行卡dao
* 版 本 号：  V1.0
 */
@Repository
public class BankCardDao extends BaseDao<BankCard> {

	/**
	* 功能描述： 获取用户绑定的银行卡集合
	* 输入参数:  @param userId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月26日下午10:32:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<BankCard>
	 */
	public List<BankCard> getAll(String userId){
		String hql = " from BankCard bankCard where bankCard.account.id = :userId and bankCard.bandStatus = :bandStatus";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("bandStatus", BankCard.BandStatus.success);
		return this.getListByHql(hql, map);
	}
	
	/**
	* 功能描述： 根据银行卡号获取银行卡信息
	* 输入参数:  @param bankCard
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午7:27:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：BankCard
	 */
	public BankCard getBankCardByBankCard(String bankCard){
		String hql = " from BankCard bankCard where bankCard.bankCard = :bankCard";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("bankCard", bankCard);
		List<BankCard> list = this.getListByHql(hql, map);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	* 功能描述： 根据订单号查询银行卡
	* @param @param orderId
	* @param @return
	* 异    常： 
	* 创 建 人: xiaolong
	* 日    期: 2016年12月26日 上午11:06:52 
	* 修 改 人: 
	* 日    期: 
	* 返    回：BankCard
	 */
	public BankCard getBankCardByOrderId(String orderId){
		String hql = " from BankCard vo where vo.orderNo = :orderId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		List<BankCard> list = this.getListByHql(hql, map);
		if(null == list || list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	public List<Map<String, Object>> getAllForMap(String userId){
		String sql = "select vo.id,vo.bank_card,vo.open_bank,vo.bank_name,vo.bank_type,vo.mobile,vo.cn_name,vo.image,vo.markImage from mem_bank_card as vo where vo.account_id = :userId and vo.bandStatus = :bandStatus";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("bandStatus", 1);
		return this.getListBySQLMap(sql, map);
	}

	public Map<String, Object> getMyBindBankCardPage(String accountId, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				" select bankCard.id,bankCard.bank_card as bankCard,bankCard.open_bank as openBank,bankCard.bank_name as bankName,"
				+ "bankCard.bank_type as bankType,bankCard.cn_name as cnName,bankCard.image,bankCard.markImage "
				+ "FROM mem_bank_card bankCard where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		if (StringUtils.isNotBlank(accountId)) {
			where.append(" and bankCard.account_id = :accountId");
			map.put("accountId", accountId);
		}
		where.append(" and bankCard.bandStatus = :bandStatus ");
		map.put("bandStatus", BankCard.BandStatus.success.ordinal());
		return this.getPageSQLMap(sql.append(where).toString(), map, start, limit);
	}
	
	
}
