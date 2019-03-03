package com.memory.platform.module.goods.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.GoodsBasicStockType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class GoodsBasicStockTypeDao extends BaseDao<GoodsBasicStockType> {
	//lix 2017-08-09 添加 用户判断车队用户对当前货源是否可以操作抢单
	public Map<String, Object> getMyGoodsBasicStockTypeWithGoodsBasicID(String goodsBasicID, Account account) {
		 
		 String sql = "select  count(*) as cnt  from goods_basic_stock_type as a where a.goods_basic_id = :goodsBasicID "
		 		+ " and (EXISTS (SELECT b.card_type_id FROM sys_track AS b WHERE b.company_id = :company_id AND a.stock_type_id = b.card_type_id) or a.stock_type_id='0')";
		 Map<String, Object> parameter = new HashMap<>();
		 parameter.put("goodsBasicID", goodsBasicID);
		 parameter.put("company_id", account.getCompany().getId());
		return this.getSqlMap(sql, parameter);
	}

}


