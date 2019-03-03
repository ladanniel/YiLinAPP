package com.memory.platform.module.goods.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.base.Area;
import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.dao.ContactsDao;
import com.memory.platform.module.goods.service.IContactsService;
import com.memory.platform.module.system.dao.AreaDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 下午2:16:53 
* 修 改 人： 
* 日   期： 
* 描   述： 联系人 － 服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class ContactsService implements IContactsService {

	@Autowired
	private ContactsDao contactsDao;
	@Autowired
	private AreaDao areaDao;
	public void updateAreaData(Contacts contacts) {
		Account acount = UserUtil.getAccount();
		contacts.setAdd_user_id(acount.getId());
		String areaID = contacts.getAreaId();
		Area area =  areaDao.getObjectById(Area.class,areaID);
		contacts.setPoint(area.getLat()+","+area.getLng());
		contacts.setAreaFullName(area.getFull_name());
		
	}
	@Override
	public void saveContacts(Contacts contacts) {
		this.updateAreaData(contacts);
		contactsDao.save(contacts);
	}

	@Override
	public void updateContacts(Contacts contacts) {
		this.updateAreaData(contacts);
		contactsDao.update(contacts);
	}

	@Override
	public Map<String, Object> getPage(Contacts contacts, int start, int limit) {
		return contactsDao.getPage(contacts, start, limit);
	}

	@Override
	public Contacts getContactsById(String id) {
		return contactsDao.getObjectById(Contacts.class, id);
	}

	@Override
	public void removeContacts(String id) {
		contactsDao.delete(getContactsById(id));
	}

}
