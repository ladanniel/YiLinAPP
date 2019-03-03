package com.memory.platform.zookeeper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.memory.platform.global.StringUtil;
/*
 * by lil 进程锁   lookRoot 父节点ID 默认/apps/lock 表示锁所有APPS以下的进程   /apps/xiaobiaoren/lock 表示锁xiaobiaoren类型的APP
 * 
 * */
public class ZooKeeperProcessLock {
	// 默认锁全部APP
	private String lockRoot = "/apps/lock";
	String lockID = null;
	AtomicInteger lockCount = new AtomicInteger(0);
	String id = "";

	public ZooKeeperProcessLock(String lockRoot) {
		if (StringUtil.isNotEmpty(lockRoot))
			this.lockRoot = lockRoot;
	}

	public String getLockRoot() {
		return lockRoot;
	}

	public String getLockPath() {
		return lockRoot + "/lock_";
	}

	public long getSeqWithID(String id, String lockPath) {
		String path = lockPath;
		int index = lockPath.lastIndexOf("/");
		if (index != -1) {
			path = lockPath.substring(index + 1);
		}
		return Long.parseLong(id.replace(path, ""));
	}

	public void lock() throws KeeperException, InterruptedException {
		TryLock(0);

	}

	public boolean TryLock(long wait) throws KeeperException, InterruptedException {
		boolean ret = false;
		if (StringUtil.isNotEmpty(lockID)) {
			lockCount.incrementAndGet();
			return ret;
		}
		if(StringUtil.isNotEmpty(id)) {
			ZooKeeperManager.getInstance().getZooKeeper().delete(id, 0);
			id = null;
		}
		String data = "lock";
		//long lockOldTime =  System.currentTimeMillis();
			id = ZooKeeperManager.getInstance().getZooKeeper().create(getLockPath(), data.getBytes(),
					Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			//System.out.println("创建节点时间 " + (System.currentTimeMillis() - lockOldTime));
		// long mySeq = Long.parseLong(id.replace(getLockPath(), ""));
		List<String> locks = ZooKeeperManager.getInstance().getZooKeeper().getChildren(getLockRoot(), false);
		do {

			if (locks.size() == 1) {
				ret = true;
				break;
			}
			if(locks.size()==0) {
				System.out.print("locks == 0");
			}
			TreeSet<String> set = new TreeSet<String>();
			set.addAll(locks);
			String minID = getLockRoot() + "/" + set.first();

			if (minID.equals(id) == false) {
			 
				final Semaphore semp = new Semaphore(0);

				
				Stat stat = ZooKeeperManager.getInstance().getZooKeeper().exists(minID, new Watcher() {

					@Override
					public void process(WatchedEvent event) {

						if (event.getType() == EventType.NodeDeleted) {

							semp.release();

						}
					}

				});
			//	System.out.println("stat " + stat);
				if (stat == null) { // 递归继续原有逻辑 这种情况被监听的已经被删除了
					return TryLock(wait);
				}
				long current = System.currentTimeMillis();
				boolean bWait = semp.tryAcquire(wait == 0 ? Long.MAX_VALUE : wait, TimeUnit.MILLISECONDS);
				if (bWait == false) {
					break;
				}

				long elapse = System.currentTimeMillis() - current;
				if (elapse >= wait) {
					if (wait != 0)
						break;

				}
				// 递归继续原有逻辑 只是等待时间少了
				return TryLock(wait == 0 ? wait : wait - elapse);

			}
			ret = true;
		} while (false);
		if (ret == true) {
			lockID = id;
			id = null;
			lockCount.incrementAndGet();
		} else {
			if (StringUtil.isNotEmpty(id)) {
				ZooKeeperManager.getInstance().getZooKeeper().delete(id, 0);
			}
			id= null;
		}
		
		return ret;
	}

	public void lockInterruptibly() throws InterruptedException {

	}

	public Condition newCondition() {

		return null;
	}

	public boolean tryLock() {

		return false;
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

		try {
			return this.TryLock(time * unit.toMillis(time));
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public void unlock() throws InterruptedException, KeeperException {
	 
		if (StringUtil.isNotEmpty(lockID) && lockCount.decrementAndGet() == 0  ) {
			String tmpLockID = lockID;
			lockID = null;
			id = null;
			//long lockOldTime =  System.currentTimeMillis();
			ZooKeeperManager.getInstance().getZooKeeper().delete(tmpLockID, 0);
			//System.out.println("zookeeperDelete 时间" + (System.currentTimeMillis() - lockOldTime));
		}

	}

}
