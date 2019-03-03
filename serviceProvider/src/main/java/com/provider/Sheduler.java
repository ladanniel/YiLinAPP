package com.provider;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.SendType;
import com.memory.platform.entity.sys.SendRecord.Status;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ISendRecordService;
import com.memory.platform.module.trace.service.IYHTGpsService;
import com.memory.platform.zookeeper.ZooKeeperManager;

@Component
public class Sheduler implements ApplicationContextAware {
	@Autowired
	ZooKeeperManager zooKeeperManager;
	@Autowired
	MemShardStrore memStrore;
	@Autowired
	IYHTGpsService yhtGpsService;
	
	long rate = 2000;

	long gpsRate = 10000;

	public long getGpsRate() {
		return gpsRate;
	}

	public void setGpsRate(long gpsRate) {
		this.gpsRate = gpsRate;
	}

	protected ExecutorService singleThreadPool;
	protected ExecutorService gpsThreadPool;
	String name;

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	@Autowired
	ISendRecordService sendRecordService;
	@Autowired
	IPushService pushService;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	IRobOrderRecordService robOrderService;
	@Autowired
	IAccountService accountService;
	@Autowired
	IGoodsBasicService goodsBasicServer;

	@PostConstruct
	protected void init() {
		singleThreadPool = Executors.newCachedThreadPool();
		gpsThreadPool = Executors.newCachedThreadPool();
	}

	private void SendPushMessage() {
		SendRecord queryRecord = new SendRecord();
		queryRecord.setStatus(Status.waitSend);
		queryRecord.setSend_type(SendType.push);
		List<SendRecord> lst = sendRecordService.getWaitSendMessage(queryRecord, 0, 1000); // 一次最多100条
		pushService.pushMessage(lst);
	}

	public Runnable makeRestart() {
		return new Runnable() {

			@Override
			public void run() {

				start();
			}
		};
	}

	public void startSchedule() {
		singleThreadPool.submit(makeRestart());

	}

	public void start() {

		try {

			do {

				ZooKeeper zKeeper = ZooKeeperManager.getInstance().getZookeeper();
				String rootPath = ZooKeeperManager.getInstance().getAppIntanceRoot() + "/" + "singleRun";
				Stat stat = null;

				stat = zKeeper.exists(rootPath, false);

				if (stat == null) { // 不存在

					zKeeper.create(rootPath, "singRun".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

				}
				String myPath = rootPath + "/" + "run" + getName();
				stat = zKeeper.exists(myPath, new Watcher() {

					@Override
					public void process(WatchedEvent event) {
						if (event.getType() == org.apache.zookeeper.Watcher.Event.EventType.NodeDeleted) {

							startSchedule();
						}
					}
				});
				if (stat == null) {
					Map<String, String> map = new HashMap<>();
					map.put("ip", InetAddress.getLocalHost().getHostAddress());
					map.put("host", InetAddress.getLocalHost().getHostName());
					String json = JsonPluginsUtil.beanToJson(map);
					zKeeper.create(myPath, json.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
					startRun();
					startGpsRun();
				 
				}

			} while (false);

		} catch (Exception e) {
			e.printStackTrace();
			singleThreadPool.submit(makeRestart());
		}

	}

	 

	// gps同步
	private void startGpsRun() {
		try {
			autoGpsService();
			Thread.sleep(getGpsRate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		gpsThreadPool.submit(new Runnable() {

			@Override
			public void run() {
				startGpsRun();
			}
		});
	}

	protected void startRun() {
		try {
			SendPushMessage();
			autoPayment();
			changGoodsBasicsOnLine();

			Thread.sleep(getRate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		singleThreadPool.submit(new Runnable() {

			@Override
			public void run() {

				startRun();
			}
		});
	}

	private void autoGpsService() {
		// Map<String, Object> ret = yhtGpsService.getAllVehicle();
		yhtGpsService.autoSaveVechicle();
	}

	/*
	 * 自动付款
	 */
	protected void autoPayment() throws InterruptedException, KeeperException {

		try {
			List<RobOrderConfirm> confirms = robOrderConfirmService.getAutoPaymentConfrim();

			if (confirms.size() > 0) {
				memStrore.getLock().lock();
				for (RobOrderConfirm robOrderConfirm : confirms) {
					RobOrderRecord robOrderRecord = robOrderService.getRecordById(robOrderConfirm.getRobOrderId());
					Account account = accountService.getAccount(robOrderRecord.getRobbedAccountId());

					try {
						robOrderConfirmService.savePayment(account, robOrderConfirm, robOrderRecord, true);
					} catch (Exception e) {
						robOrderConfirmService.encrimentAutoPaymentErr(robOrderConfirm.getId());
					}

				}
			}

		}

		finally {

			memStrore.getLock().unlock();
		}

	}

	// lix 2017-09-12 添加 自动根据货源ID设置货源上线状态为下线
	protected void changGoodsBasicsOnLine() {
		try {
			List<GoodsBasic> goodsBasicsList = goodsBasicServer.getGoodsBasicsListByOnLine();
			if (goodsBasicsList.size() > 0) {
				for (GoodsBasic goodsBasics : goodsBasicsList) {
					goodsBasicServer.updateAllGoodsOnLine(goodsBasics.getId());
				}
			}
		} finally {
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}
}
