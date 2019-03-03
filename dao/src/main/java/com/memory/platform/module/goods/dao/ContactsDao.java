package com.memory.platform.module.goods.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 上午11:46:51 
* 修 改 人： 
* 日   期： 
* 描   述： 联系人信息 － Dao
* 版 本 号：  V1.0
 */
@Repository
public class ContactsDao extends BaseDao<Contacts> {

	/**
	* 功能描述： 获取联系人列表
	* 输入参数:  @param contacts
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午12:05:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Contacts contacts,int start,int limit){
		StringBuffer hql = new StringBuffer(" from Contacts contacts where contacts.companyId = :companyId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyId",contacts.getCompanyId());
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotEmpty(contacts.getSearch())){
			where.append(" and (contacts.name like :name or "
					+ "contacts.mobile like :mobile or "
					+ "contacts.email like :email or "
					+ "contacts.address like :address or "
					+ "contacts.areaFullName like :areaFullName)");
			map.put("name", "%" + contacts.getSearch() + "%");
			map.put("mobile", "%" + contacts.getSearch() + "%");
			map.put("email", "%" + contacts.getSearch() + "%");
			map.put("address", "%" + contacts.getSearch() + "%");
			map.put("areaFullName", "%" + contacts.getSearch() + "%"); 
		}
		if(contacts.getContactsType() != null){
			where.append(" and contacts.contactsType = :contactsType");
			map.put("contactsType", contacts.getContactsType());
		}
		if(StringUtil.isNotEmpty( contacts.getAdd_user_id())){
			where.append(" and add_user_id=:add_user_id " );
			map.put("add_user_id",  contacts.getAdd_user_id());
		}
		where.append(" order by contacts.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
}
