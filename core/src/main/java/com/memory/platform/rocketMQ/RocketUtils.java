package com.memory.platform.rocketMQ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import com.memory.platform.rocketMQ.connection.RocketMQPushConsumer;
import com.memory.platform.rocketMQ.connection.RocketProducer;

public class RocketUtils {
	private static Map<TagTopic, DefaultMQProducer> producerMap = null;
	private static Map<TagTopic, DefaultMQPushConsumer> consumerMap = null;

	public enum TagTopic {
		weiXinPay,
	}

	private static onConsumerListener mListener;

	public static void setOnConsumerListener(onConsumerListener listener) {
		mListener = listener;
	}

	public interface onConsumerListener {
		void onConsumer(String xml);
	}

	public static DefaultMQProducer getProducer(final TagTopic tag) {
		if (tag == null)
			return null;
		if (producerMap == null) {
			synchronized (RocketUtils.class) {
				producerMap = new HashMap<TagTopic, DefaultMQProducer>();
			}
		}
		if (!producerMap.containsKey(tag)) {
			synchronized (RocketUtils.class) {
				if (!producerMap.containsKey(tag)) {
					DefaultMQProducer producer = null;
					try {
						producer = RocketProducer.newInstance();
						producer.setProducerGroup(tag.toString());
						producer.start();
					} catch (MQClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					producerMap.put(tag, producer);
				}
			}
		}
		return producerMap.get(tag);
	}

	public static void main(String[] args) {
		for(int i = 0;i<5;i++) {
			DefaultMQProducer mq = RocketUtils.getProducer(TagTopic.weiXinPay);
		}
	}

	public static DefaultMQPushConsumer getConsumer(final TagTopic tag) {
		if (tag == null)
			return null;
		if (consumerMap == null) {
			synchronized (RocketUtils.class) {
				consumerMap = new HashMap<TagTopic, DefaultMQPushConsumer>();
			}
		}
		if (!consumerMap.containsKey(tag)) {
			synchronized (RocketUtils.class) {
				if (!consumerMap.containsKey(tag)) {
					DefaultMQPushConsumer consumer = null;
					try {
						consumer = RocketMQPushConsumer.newInstance();
						consumer.setMessageModel(MessageModel.BROADCASTING);
						consumer.setConsumerGroup(tag.toString());
						// 指定订阅内容
						consumer.subscribe(tag.toString(), tag.toString());
						consumer.registerMessageListener(new MessageListenerConcurrently() {

							@Override
							public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
									ConsumeConcurrentlyContext context) {
								System.out.printf(Thread.currentThread().getName() + " consumerA Receive New Messages: "
										+ msgs + "%n");
								for (Message msg : msgs) {
									if (mListener != null) {
										mListener.onConsumer(new String(msg.getBody()));
									}
								}
								return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
							}
						});
						consumer.start();
					} catch (MQClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					consumerMap.put(tag, consumer);
				}
			}
		}
		return consumerMap.get(tag);
	}
}
