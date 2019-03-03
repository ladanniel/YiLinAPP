package com.memory.platform.zookeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

/**
 * @author lil Zookeeper管理器 用getDispatch
 * 
 */
public class ZooKeeperManager implements Watcher {

	Logger logger = Logger.getLogger(ZooKeeperManager.class);
	private static ZooKeeperManager instance = null;

	private ThreadLocal<ZooKeeperProcessLock> threadLocalLock = new ThreadLocal<ZooKeeperProcessLock>();

	public ZooKeeperProcessLock getProcessLock() {
		ZooKeeperProcessLock lock = threadLocalLock.get();
		if (lock == null) {
			synchronized (ZooKeeperManager.class) {
				lock = threadLocalLock.get();
				if(lock == null) {
					lock = new ZooKeeperProcessLock(appIntanceRoot + "/lock");
					threadLocalLock.set(lock);
				}
			}
		}
		return lock;
	}

	/*
	 * public void setProcessLock(ZooKeeperProcessLock processLock) {
	 * this.processLock = processLock; }
	 */
	public static ZooKeeperManager getInstance() {
		return instance;
	}

	public static class ZookeeperWatchEvent {
		WatchedEvent wrapEvent = null;

		public WatchedEvent getWrapEvent() {
			return wrapEvent;
		}

		public void setWrapEvent(WatchedEvent wrapEvent) {
			this.wrapEvent = wrapEvent;
		}

	}

	public static final int SESSION_TIMEOUT = 30000;
	private String connectString;

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	private String appRoot = "/apps";

	public String getAppRoot() {
		return appRoot;
	}

	public void setAppRoot(String appRoot) {
		this.appRoot = appRoot;
	}

	private String appIntanceRoot = "/apps/xiaoBiaoRenApps";

	public String getAppIntanceRoot() {
		return appIntanceRoot;
	}

	public void setAppIntanceRoot(String appIntanceRoot) {
		this.appIntanceRoot = appIntanceRoot;
	}

	private String appName = "";

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	private ZooKeeper zooKeeper;
	private int sessionTimeOut = 30000;

	public int getSessionTimeOut() {
		return sessionTimeOut;
	}

	public ZooKeeper getZookeeper() {

		return zooKeeper;
	}

	public ZooKeeper getZooKeeper() {
		return zooKeeper;
	}

	@PostConstruct
	private void init() throws IOException {

		instance = this;

		zooKeeper = new ZooKeeper(connectString, sessionTimeOut, this);

	}

	// 创建启动根目录
	protected void createRootAppPath() {

		try {
			;
			String appNamePath = this.getAppIntanceRoot() + "/"
					+ this.getAppName() + zooKeeper.getSessionId();
			try {
				appNamePath = appNamePath
						+ InetAddress.getLocalHost().getHostName() + "&"
						+ InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (zooKeeper.exists(appNamePath, null) == null) {
				zooKeeper.create(appNamePath, "appName".getBytes(),
  						Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			}

		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {

			logger.info("链接zKeeper成功");
			this.createRootAppPath();

		}
	}

}
