package com.memory.platform.memStore;

import java.util.HashSet;
import java.util.Set;

import javassist.expr.NewArray;

import javax.annotation.PostConstruct;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.druid.sql.visitor.functions.If;
import com.memory.platform.core.AppUtil;

import com.memory.platform.global.StringUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

/**
 * @author lil 内存共享数据库 redis
 * 
 */

public class MemShardStrore implements ApplicationContextAware {

	public static interface IExcute {
		void excute(Jedis jedis);
	}

	public static final String ThreadLocalJedis = "ThreadLocalJedis";
	static MemShardStrore instance = null;
	ThreadLocal<Jedis> jdisLocal = new ThreadLocal<>();
	ThreadLocal<ProcessLock> locks = new ThreadLocal<>();
	JedisPool jedisPool = null;
	JedisCluster jedisCluster = null;
	Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
	String clusterIP = "";
	GenericObjectPoolConfig poolConfig = null;
	int connectTimeout = 2000;

	public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public GenericObjectPoolConfig getPoolConfig() {
		return this.poolConfig;
	}

	public String getClusterIP() {
		return clusterIP;
	}

	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}

	public static MemShardStrore getInstance() {
		if (instance == null) {
			instance = (MemShardStrore) AppUtil.getApplicationContex().getBean(
					"memShardStrore");

		}
		return instance;
	}

	public JedisCluster getJedisCluster() {
		if (jedisCluster == null) {
			synchronized (MemShardStrore.class) {
				if (jedisCluster == null) {
					jedisCluster = new JedisCluster(this.getHostAndPorts(),
							connectTimeout, 1000, 10, getPassword(),
							getPoolConfig());

				}
			}
		}
		return jedisCluster;
	}

	private Set<HostAndPort> getHostAndPorts() {
		if (hostAndPorts.size() == 0) {
			if (StringUtil.isNotEmpty(getClusterIP())) {
				String[] arr = getClusterIP().split(",");
				for (String strIPAndPort : arr) {
					String[] arrIPAndPort = strIPAndPort.split(",");
					if (arrIPAndPort.length > 1) {
						HostAndPort info = new HostAndPort(arrIPAndPort[0],
								Integer.parseInt(arrIPAndPort[1]));
						hostAndPorts.add(info);
					}
				}
			}
		}
		return hostAndPorts;
	}

	public JedisPool getJedisPool() {
		if (jedisPool == null) {
			synchronized (lock) {
				if (jedisPool == null) {
					GenericObjectPoolConfig config = new GenericObjectPoolConfig();

					jedisPool = new JedisPool(config, ip, port,
							Protocol.DEFAULT_TIMEOUT, getPassword());
				}
			}
		}
		return jedisPool;
	}

	public ProcessLock getLock() {
		ProcessLock ret = locks.get();
		if (ret == null) {
			synchronized (lock) {
				ret = locks.get();
				if (ret == null) {
					ret = new ProcessLock();
					locks.set(ret);
				}
			}
		}
		return ret;
	}

	Logger logger = Logger.getLogger(MemShardStrore.class);

	String ip;
	String password;
	static Object lock = new Object();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void excute(IExcute excute) {
		Jedis client = null;
		try {
			client = getClient();
			excute.excute(client);
		} finally {
			if (client != null) {
				client.close();
			}
		}

	}

	public Jedis getClient() {

		// return getJedisPool().getResource();

		Jedis client = jdisLocal.get();
		if (client == null) {
			synchronized (lock) {
				client = jdisLocal.get();
				if (client == null) {
					client = new Jedis(this.ip, port);
					client.auth(password);
				}
				jdisLocal.set(client);
			}
		}

		return client;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@PostConstruct
	public void init() {
		instance = this;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		AppUtil.setApplicationContex(applicationContext);

	}
}
