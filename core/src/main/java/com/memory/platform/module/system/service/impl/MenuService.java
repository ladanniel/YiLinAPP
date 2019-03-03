package com.memory.platform.module.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.Menu;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.dao.MenuDao;
import com.memory.platform.module.system.service.IMenuService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月27日 下午4:30:37 
* 修 改 人： 
* 日   期： 
* 描   述： 彩页业务层实现类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class MenuService implements IMenuService {

	@Autowired
	private MenuDao menuDao;

	public List<Menu> getMenuListByPid(String menuId) {

		return this.menuDao.getMenuListByPid(menuId);
	}

	public void saveMenu(Menu menu) { 
		menu.setCreate_time(new Date());
		menu.setAdd_user_id(UserUtil.getUser().getId());
		this.menuDao.saveMenu(menu);  
	}

	public void removeMenu(String menuId) {
		Menu menu = this.menuDao.getMenu(menuId);

		// 查询当前菜单下面是否有子级，有子级将不能删除
		int count = this.menuDao.getMenuChildCount(menuId);
		if (count > 0) {
			throw new RuntimeException("当前菜单存在子级，不能直接删除！");
		}
		this.menuDao.removeMenu(menu); 
	}


	public void updateMenu(Menu menu) { 
		menu.setUpdate_time(new Date());
		menu.setUpdate_user_id(UserUtil.getUser().getId());
		this.menuDao.updateMenu(menu); 
	}

	public Menu getMenu(String menuId) {
		return this.menuDao.getMenu(menuId);
	}

	@Override
	public List<Menu> getMenuList() {
		return menuDao.getMenuList();
	}
 

	@Override
	public List<Map<String, Object>> getMenuListByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return menuDao.getMenuListByRoleId(roleId);
	}

	@Override
	public List<Menu> getUserMenuList(String roleId, String menuId) {
		// TODO Auto-generated method stub
		return menuDao.getUserMenuList(roleId, menuId);
	}

	/*  
	 *  getMenuOrCode
	 */
	@Override
	public List<Map<String, Object>> getMenuOrCode() {
		// TODO Auto-generated method stub
		return menuDao.getMenuOrCode();
	}

}
