package com.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.exception.BusinessException;
import com.memory.platform.module.capital.service.IWeiXinPayCallBackService;
 
import com.memory.platform.rocketMQ.broadcasting.BroadcastConsumer;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducer;
import com.memory.platform.rocketMQ.connection.Config;

public class RechargeConsumer extends BroadcastConsumer {
	Logger logger = Logger.getLogger(RechargeConsumer.class);
	@Autowired
	private IWeiXinPayCallBackService weiXinPayCallBackService;

	@Override
	public ConsumeConcurrentlyStatus onCusumer(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		ConsumeConcurrentlyStatus status  =ConsumeConcurrentlyStatus.RECONSUME_LATER;
		for (MessageExt msg : msgs) {
			switch (msg.getTags()) {
			// 微信支付
			case "weiXinPay":
				try {
					String json = new String(msg.getBody(), "utf-8");
					logger.info("收到消息:" + json + " TAG:" + msg.getTags());
					if (!StringUtils.isEmpty(json)) {
						weiXinPayCallBackService.save(json);
						status =   ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
				
				}catch(BusinessException e) {
					if("1".equals(e.getCode()) ||"2".equals(e.getCode()) || "3".equals(e.getCode()) ) {
						status =  ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
				}
				catch (Exception e) {
					 
					logger.info("充值记录保存失败:" + e.getMessage());
				}
				break;
			}
		}

		return status;
	}
	

	public static void main (String[] args)  {
		try {
			test();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}


	private static void test() throws MQClientException, IOException,
			UnsupportedEncodingException {
		ClientConfig cfg = new ClientConfig();
		cfg.setVipChannelEnabled(false);
	//	cfg.setNamesrvAddr("127.0.0.1:9876");
		cfg.setNamesrvAddr("192.168.0.201:9876");
		BroadcastConsumer consumer = new BroadcastConsumer(){

			@Override
			public ConsumeConcurrentlyStatus onCusumer(
					List<MessageExt> msgs,
					ConsumeConcurrentlyContext context) {
				MessageExt msgInfo = msgs.get(0);
				
				try {
					String msgString = new String( msgInfo.getBody(),"utf8");
					   System.out.print("收到消息" + msgs.size()  + " " + msgString);
				} catch (UnsupportedEncodingException e) {
					 
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			 
				return super.onCusumer(msgs, context);
			}
			
		};
		consumer.setClientConfig(cfg);
		consumer.setConsumerGroup("xiaoBiaoRen");
		consumer.setConsumerTopic("recharge");
		consumer.init();
		
		BroadcastProducer producer = new BroadcastProducer();
		producer.setProducerGroup("xiaoBiaoRen");
		producer.setClientConfig(cfg);
		producer.init();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String cmd ;
		while ( (cmd = reader.readLine())!=null) {
				MessageExt msg = new MessageExt();
				msg.setBody(cmd.getBytes("utf8"));
				msg.setTopic("recharge");
				try {
					
					producer.sendMsg(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}
