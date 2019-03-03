package com.memory.platform.module.system.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.SendMessage;
import com.memory.platform.entity.sys.SendMessage.Type;
import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.Status;
import com.memory.platform.global.HTTPUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.module.system.dao.SendMessageDao;
import com.memory.platform.module.system.dao.SendRecordDao;
import com.memory.platform.module.system.service.ISendMessageService;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:37:03 
* 修 改 人： 
* 日   期： 
* 描   述： 短信接口 － 服务类
* 版 本 号：  V1.0
 */
@Service
public class SendMessageService implements ISendMessageService {

	@Autowired
	private SendMessageDao sendMessageDao;
	@Autowired
	private SendRecordDao sendRecordDao;
	
	@Override
	public void saveMessage(SendMessage sendMessage) {
		sendMessageDao.save(sendMessage);
	}

	@Override
	public void updateMessage(SendMessage sendMessage) {
		sendMessageDao.update(sendMessage);
	}

	@Override
	public Map<String, Object> getPage(SendMessage sendMessage, int start, int limit) {
		return sendMessageDao.getPage(sendMessage, start, limit);
	}

	@Override
	public SendMessage getSendMessage(String id) {
		return sendMessageDao.getObjectById(SendMessage.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveRecordAndSendMessage(String mobiles, String content,String sendPoint){
		Map<String, Object> map_ret = new HashMap<String,Object>();
		List<SendMessage> list = sendMessageDao.getEnabledSendMessage();
		if(list.size() == 0){
			map_ret.put("success", false);
			map_ret.put("msg","没有可启用的短信接口。请联系管理员启用。");
			return map_ret;
		}
		SendMessage sendMessage = list.get(0);
		String url = sendMessage.getSendUrl();
		String shortUrl = "";
		String arrayUrl[] = url.split("&");
		for (int i = 0; i < arrayUrl.length; i++) {
			if(i == arrayUrl.length - 2){
				shortUrl += "&" + arrayUrl[i] + mobiles;
			}else if(i == arrayUrl.length - 1){
				try {
					shortUrl += "&" + arrayUrl[i] + URLEncoder.encode(content,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if(i == 0){
				shortUrl += arrayUrl[i];
			}else{
				shortUrl += "&" +arrayUrl[i];
			}
		}
		String ret = HTTPUtil.sendGet(shortUrl);
		String arry[] = sendMessage.getSendSuccess().split(":");
		if(sendMessage.getType().equals(Type.json)){
			Map<String, Object> map = JsonPluginsUtil.jsonToMap(ret);
			if(mobiles.length() > 11){
				String array[] = mobiles.split(",");
				for (String string : array) {
					SendRecord sendRecord = new SendRecord();
					sendRecord.setContent(content);
					sendRecord.setCreate_time(new Date());
					sendRecord.setPhone(string);
					sendRecord.setSendMessage(sendMessage);
					sendRecord.setSendPoint(sendPoint);
					if(map.get(arry[0]).equals(arry[1])){
						sendRecord.setStatus(Status.success);
					}else{
						sendRecord.setStatus(Status.fail);
					}
					sendRecordDao.save(sendRecord);
				}
			}else{
				SendRecord sendRecord = new SendRecord();
				sendRecord.setContent(content);
				sendRecord.setCreate_time(new Date());
				sendRecord.setPhone(mobiles);
				sendRecord.setSendMessage(sendMessage);
				sendRecord.setSendPoint(sendPoint);
				if(map.get(arry[0]).equals(arry[1])){
					sendRecord.setStatus(Status.success);
				}else{
					sendRecord.setStatus(Status.fail);
				}
				sendRecordDao.save(sendRecord);
			}
			if(map.get(arry[0]).equals(arry[1])){
				map_ret.put("success", true);
				map_ret.put("msg", "发送成功。");
			}else{
				map_ret.put("success", false);
				map_ret.put("msg","错误代码："+ map.get(arry[0]));
			}
			
		}else if(sendMessage.getType().equals(Type.text)){
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(ret);
			} catch (DocumentException e) {
				map_ret.put("success", false);
				map_ret.put("msg", "解析数据异常");
				return map_ret;
			} 
			Element root = doc.getRootElement();
			if(mobiles.length() > 11){
					String array[] = mobiles.split(",");
					for (String string : array) {
						SendRecord sendRecord = new SendRecord();
						sendRecord.setContent(content);
						sendRecord.setCreate_time(new Date());
						sendRecord.setPhone(string);
						sendRecord.setSendMessage(sendMessage);
						sendRecord.setSendPoint(sendPoint);
						if(root.elementText(arry[0]).equals((arry[1]))){
							sendRecord.setStatus(Status.success);
						}else{
							sendRecord.setStatus(Status.fail);
						}
						sendRecordDao.save(sendRecord);
					}
				}else{
					SendRecord sendRecord = new SendRecord();
					sendRecord.setContent(content);
					sendRecord.setCreate_time(new Date());
					sendRecord.setPhone(mobiles);
					sendRecord.setSendMessage(sendMessage);
					sendRecord.setSendPoint(sendPoint);
					if(root.elementText(arry[0]).equals((arry[1]))){
						sendRecord.setStatus(Status.success);
						map_ret.put("success", true);
						map_ret.put("msg", "发送成功。");
					}else{
						sendRecord.setStatus(Status.fail);
						map_ret.put("success", false);
						map_ret.put("msg","错误代码："+ root.elementText(arry[0]));
					}
					sendRecordDao.save(sendRecord);
			}
		}else if(sendMessage.getType().equals(Type.array)){
			String[] arr = ret.split(",");
			if(mobiles.length() > 11){
				String array[] = mobiles.split(",");
				for (String string : array) {
					SendRecord sendRecord = new SendRecord();
					sendRecord.setContent(content);
					sendRecord.setCreate_time(new Date());
					sendRecord.setPhone(string);
					sendRecord.setSendMessage(sendMessage);
					sendRecord.setSendPoint(sendPoint);
					if(arr[1].length() == 3){
						sendRecord.setStatus(Status.fail);
					}else{
						sendRecord.setStatus(Status.success);
					}
					sendRecordDao.save(sendRecord);
				}
			}else{
				SendRecord sendRecord = new SendRecord();
				sendRecord.setContent(content);
				sendRecord.setCreate_time(new Date());
				sendRecord.setPhone(mobiles);
				sendRecord.setSendMessage(sendMessage);
				sendRecord.setSendPoint(sendPoint);
				if(arr[1].length() == 3){
					sendRecord.setStatus(Status.fail);
				}else{
					sendRecord.setStatus(Status.success);
				}
				sendRecordDao.save(sendRecord);
			}
			if(arr[1].subSequence(0, 1).equals(arry[1])){
				map_ret.put("success", true);
				map_ret.put("msg", "发送成功。");
			}else{
				map_ret.put("success", false);
				map_ret.put("msg","错误代码："+ arr[1]);
			}
		}
		return map_ret;
	}
	
	@Override
	public List<SendMessage> getEnabledSendMessage() {
		return sendMessageDao.getEnabledSendMessage();
	}}
