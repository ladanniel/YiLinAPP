package com.memory.platform.rocketMQ.connection;

import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * aiqiwu 
 * 2018-03-19
 * 消息队列消费者配置
 * */
public class RocketMQPushConsumer {

	private RocketMQPushConsumer() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static class singleton {
		private static DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

		static {
			consumer.resetClientConfig(RocketClientConfig.newInstance());
		}

		private static DefaultMQPushConsumer restConfig(ClientConfig config) {
			consumer.resetClientConfig(config);
			return consumer;
		}
	}

	public static DefaultMQPushConsumer newInstance() {
		return RocketMQPushConsumer.singleton.consumer;
	}

	public static DefaultMQPushConsumer restConfigInstance(ClientConfig config) {
		return RocketMQPushConsumer.singleton.restConfig(config);
	}
}