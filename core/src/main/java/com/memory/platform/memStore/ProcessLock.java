package com.memory.platform.memStore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.hibernate.id.GUIDGenerator;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.dubbo.remoting.Client;
import com.memory.platform.core.AppUtil;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;

/**
 * 进程锁
 * 
 * @author lil
 * 
 */
public class ProcessLock {
	String key = "lock_global";
	String lockValue = "";
	String memShardStrore = "memShardStrore";
	MemShardStrore memStore = null;

	public String getLockValue() {
		if (StringUtil.isEmpty(lockValue)) {
			lockValue = Thread.currentThread().getId() + "";

		}
		return lockValue;
	}

	public void setLockValue(String lockValue) {
		this.lockValue = lockValue;
	}

	AtomicInteger lockNumber = new AtomicInteger();

	int expire = 60; // 过期时间 分布式万一锁上机器的死机了 就会影响到其他机器

	private MemShardStrore getMemStore() {
		if (memStore == null) {
			ApplicationContext applicationContext = AppUtil
					.getApplicationContex();
			if (applicationContext == null) {

				throw new NullPointerException("applicationContext is null");

			}
			memStore = (MemShardStrore) applicationContext
					.getBean(memShardStrore);
			if (memStore == null) {

				throw new NullPointerException("memShardStrorName");
			}
		}
		return memStore;
	}

	public boolean tryLock(long time, TimeUnit unit)
			  {
		if (this.lockNumber.get() > 0) {
			this.lockNumber.incrementAndGet();
			return true;
		}
		time = time * unit.toMillis(time);
		MemShardStrore memStore = getMemStore();
		Jedis jedis = memStore.getClient();
		/*
		 * memStore.excute( new MemShardStrore.IExcute() {
		 * 
		 * @Override public void excute(Jedis jedis) {
		 * 
		 * 
		 * } });
		 */

		do {
			long result = 0;
			String lockValue = getLockValue();
			long oldTime = System.currentTimeMillis();
			long maxTime = 0;
			if (time > 0) {
				maxTime = oldTime + time;
			}
			long factor = 0;
			while ((result = jedis.setnx(key, lockValue)) != 1) {

				try {
					if (maxTime > 0 && System.currentTimeMillis() > maxTime) {
						break;
					}
					if (++factor > 1000) {
						factor = 0;
						Thread.sleep(1);
					}

				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

			if (result == 1) {
				lockNumber.incrementAndGet();
				jedis.expire(key, expire);
			}
			// memStore.getClient().close();
		} while (false);

		return lockNumber.get() > 0;
	}

	public void unlock() {
		if (lockNumber.get() > 0 && lockNumber.decrementAndGet() == 0) {
			MemShardStrore memStore = getMemStore();
			// String memValue = memStore.getClient().get(key);

			memStore.getClient().del(key);

			// memStore.getClient().close();
		}

	}

	public void lock()   {

		tryLock(0, TimeUnit.MILLISECONDS);

	}
}
