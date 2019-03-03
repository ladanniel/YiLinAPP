package com.memory.platform.rocketMQ.broadcasting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
 
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * aiqiwu 
 * 2018-03-19
 * 生产者
 * */
public class BroadcastProducer {
 
	private String producerGroup;
	private ClientConfig clientConfig;
	DefaultMQProducer producer;
	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	ExecutorService sigleThread ;
	//private static ThreadLocal<Map<String, producer>> threadLocal = new ThreadLocal<>();
	@PostConstruct
	public void init() throws MQClientException {
		System.out.printf("%s%n", "initRocketMQProducer");
		producer =new DefaultMQProducer();
		producer.resetClientConfig(clientConfig);
		producer.setProducerGroup(producerGroup);
		producer.start();
	/*	sigleThread = Executors.newSingleThreadExecutor();
		sigleThread.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					producer.start();
				} catch (MQClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.printf("%s%n", "startRocketMQProducer");
			}
		});*/
		
	
	}

	public void destory() {
		producer.shutdown();
	}

	public DefaultMQProducer getProducer() {
		return producer;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

 
	public void sendMsg(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException  {
	 
		producer.send(msg);
	}
}