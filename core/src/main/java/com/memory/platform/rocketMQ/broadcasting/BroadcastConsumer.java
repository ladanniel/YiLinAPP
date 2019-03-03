package com.memory.platform.rocketMQ.broadcasting;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import com.memory.platform.rocketMQ.connection.RocketMQPushConsumer;

/**
 * aiqiwu 
 * 2018-03-19
 * 消费者
 * */
public class BroadcastConsumer  {
	private String consumerGroup;
	private String consumerTopic;
	private String consumerTag;
	private ClientConfig clientConfig;
	private DefaultMQPushConsumer consumer;
	public DefaultMQPushConsumer getConsumer() {
		return consumer;
	}

	private ExecutorService excuterService;
	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public ConsumeConcurrentlyStatus onCusumer(List<MessageExt> msgs ,  ConsumeConcurrentlyContext context) {
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
 
	@PostConstruct
	public void init() throws MQClientException {
       
		consumer =  new DefaultMQPushConsumer();
		consumer.resetClientConfig(clientConfig);
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.setConsumerGroup(consumerGroup);
		// 指定订阅内容
		consumer.subscribe(consumerTopic, consumerTag);
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				
				return onCusumer(msgs,context);
			}
		});
		consumer.start();
		/*excuterService =  Executors.newCachedThreadPool();
		excuterService.execute( new Runnable() {
			
			@Override
			public void run() {
				try {
					consumer.start();
				} catch (MQClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("%s%n", "startRocketMQConsumer");
			}
		});
	*/
	}
	
	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}
	
	public void setConsumerTopic(String consumerTopic) {
		this.consumerTopic = consumerTopic;
	}
	
	public void setConsumerTag(String consumerTag) {
		this.consumerTag = consumerTag;
	}

	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = RocketMQPushConsumer.newInstance();
		// 消息模型，支持以下两种：集群消费(clustering)，广播消费(broadcasting)
		consumer.setMessageModel(MessageModel.BROADCASTING);
		consumer.setConsumerGroup("broadcast");
		consumer.subscribe("topicTest", "TagA || TagC || TagD");
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.printf(Thread.currentThread().getName() + " consumerA Receive New Messages: " + msgs + "%n");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		consumer.start();
	}

}