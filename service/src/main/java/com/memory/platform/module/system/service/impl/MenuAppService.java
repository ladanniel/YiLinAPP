package com.memory.platform.module.system.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.MenuApp;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.dao.MenuAppDao;
import com.memory.platform.module.system.service.IMenuAppService;


@Service
@Transactional
public class MenuAppService implements IMenuAppService{
	
	@Autowired
	private MenuAppDao menuAppDao;

	public Map<String, Object> getPage(MenuApp menuApp,int start, int limit) {
		return this.menuAppDao.getPage(menuApp,start, limit);
	}

	/*  
	 *  getMenuApp
	 */
	@Override
	public MenuApp getMenuApp(String menuAppId) {
		// TODO Auto-generated method stub
		return menuAppDao.getObjectById(MenuApp.class, menuAppId);
	}

	/*  
	 *  saveMenuApp
	 */
	@Override
	public void saveMenuApp(MenuApp menuApp) {
		// TODO Auto-generated method stub
		Account user = UserUtil.getUser();
		menuApp.setAdd_user_id(user.getId());
		menuApp.setCreate_time(new Date());
		menuAppDao.save(menuApp);
	}

	/*  
	 *  updateMenuApp
	 */
	@Override
	public void updateMenuApp(MenuApp menuApp) {
		// TODO Auto-generated method stub
		Account user = UserUtil.getUser();
		menuApp.setUpdate_user_id(user.getId());
		menuApp.setUpdate_time(new Date());
		menuAppDao.update(menuApp);
	}

	/*  
	 *  removeMenuApp
	 */
	@Override
	public void removeMenuApp(String id) {
		// TODO Auto-generated method stub
		menuAppDao.removeMenuApp(id);
	}

	/*  
	 *  getMenuAppList
	 */
	@Override
	public List<MenuApp> getMenuAppList() {
		// TODO Auto-generated method stub
		return menuAppDao.getMenuAppList();
	}

	/*  
	 *  getRoleMenuAppIds
	 */
	@Override
	public String getRoleMenuAppIds(String roleId) {
		// TODO Auto-generated method stub
		return menuAppDao.getRoleMenuAppIds(roleId);
	}

	 

}
