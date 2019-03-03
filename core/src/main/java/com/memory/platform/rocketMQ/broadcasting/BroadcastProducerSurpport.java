package com.memory.platform.rocketMQ.broadcasting;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.rocketMQ.RocketUtils;
import com.memory.platform.rocketMQ.RocketUtils.TagTopic;

/**
 * aiqiwu 
 * 2018-03-19
 * 发送数据
 * */
public class BroadcastProducerSurpport {
	
	public void sendMsg(TagTopic tag, String key, byte[] value) {
		try {
			Message msg = new Message(tag.toString(), tag.toString(), key, value);
			RocketUtils.getProducer(tag).send(msg, new SendCallback() {

				@Override
				public void onException(Throwable arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(SendResult arg0) {
					System.out.println(
							"send message success,topic=" + arg0.getQueueOffset() + ",msgId=" + arg0.getMsgId());
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
