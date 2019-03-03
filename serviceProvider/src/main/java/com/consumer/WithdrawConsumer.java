package com.consumer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.exception.BusinessException;
import com.memory.platform.module.capital.service.IRechargeRecordService;
import com.memory.platform.module.capital.service.IWeiXinPayCallBackService;
import com.memory.platform.module.capital.service.impl.RechargeRecordService;
import com.memory.platform.rocketMQ.broadcasting.BroadcastConsumer;

public class WithdrawConsumer extends BroadcastConsumer {
	Logger logger = Logger.getLogger(WithdrawConsumer.class);
	@Autowired
	private IWeiXinPayCallBackService weiXinPayCallBackService;
	@Autowired
	IRechargeRecordService rechargeRecordService; 
	@Override
	public ConsumeConcurrentlyStatus onCusumer(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		ConsumeConcurrentlyStatus status = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		if(msgs.size()>1) {
			return status;
		}
		for (MessageExt msg : msgs) {
			switch (msg.getTags()) {
			// 微信支付
			case "weiXinWithdraw":
				try {
					String id = new String(msg.getBody(), "utf-8");
					logger.info("收到消息微信取现:" + id + " TAG:" + msg.getTags());
					Map<String, Object> map = rechargeRecordService.withdrawSuccess(id);
					Boolean suc =(Boolean) map.get("success");
					if(suc) {
						status = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;	
					}else {
						status = ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}
					
				}
				catch (BusinessException e) {
					 logger.info(e.getMessage() + " code:" + e.getCode() + " msg:" + e.getMessage());
					 if("1".equals(e.getCode()) || "2".equals(e.getCode()) || "3".equals(e.getCode())){
						 status = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					 }else if("4".equals(e.getCode())) {
						 status = ConsumeConcurrentlyStatus.RECONSUME_LATER;
					 }
				}
				catch (Exception e) {
					status = ConsumeConcurrentlyStatus.RECONSUME_LATER;
					logger.info("提现记录保存失败:" + e.getMessage());
				}
				break;
			}
		}
	 
		return status;
	}
	

	 

}
