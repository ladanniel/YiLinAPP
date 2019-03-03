package com.memory.platform.module.capital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.core.AppUtil;
import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.capital.WeiXinPayCallBack;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Config;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.module.capital.dao.WeiXinPayCallBackDao;
import com.memory.platform.module.capital.service.IRechargeRecordService;
import com.memory.platform.module.capital.service.IWeiXinPayCallBackService;

public class WeiXinPayCallBackService implements IWeiXinPayCallBackService {

	@Autowired
	private WeiXinPayCallBackDao weiXinPayCallBackDao;
	@Autowired
	private IRechargeRecordService rechageRecordService;


	@Override
	public void save(String xml) {
		// TODO Auto-generated method stub
		WeiXinPayCallBack callBack = JsonPluginsUtil.jsonToBean(xml, WeiXinPayCallBack.class);
		if (callBack.getReturn_code().equals("SUCCESS")) {
			// 获取充值记录
			RechargeRecord record = rechageRecordService.getRechargeRecordByOrderId(callBack.getOut_trade_no());
			if (record == null)
				throw new BusinessException("1","充值记录不存在!");
			if (record.getStatus() == RechargeRecord.Status.success)
				throw new BusinessException("2","已充值!");
			// 验证签名
			String str = callBack.toSignStr();
			String key = rechageRecordService.getWeiXinKey(record.getAccount().getId());
			String md5 = AppUtil.md5(str + "&key=" + key);
			if (!md5.equals(callBack.getSign()))
				throw new BusinessException("3","签名错误!");
			// 保存回调发送数据
			this.weiXinPayCallBackDao.save(callBack);
			// 比对充值记录里金额是否相等
			if (callBack.getTotal_fee() != record.getMoney() * 100)
				throw new BusinessException("充值金额校验失败!");
			if (record != null) {
				rechageRecordService.saveRecharge(record);
				 
				 
			}
		}
	}
}
