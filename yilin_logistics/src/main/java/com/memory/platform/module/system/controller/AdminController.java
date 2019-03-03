package com.memory.platform.module.system.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.BeanKVO;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.entity.sys.Menu;
import com.memory.platform.entity.sys.Resource;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.IMenuService;


@Controller
@RequestMapping("/system/admin")
public class AdminController {
	
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IAccountService accountService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		Account account = UserUtil.getUser(request);
		Account newAccount = accountService.getAccount(account.getId());
		List<Menu> list = this.menuService.getUserMenuList(account.getRole().getId(),"0");
		if(null != account.getCapitalStatus() && !account.getCapitalStatus().equals(CapitalStatus.open)){
			for (Menu menu : list) {
				if(menu.getId().equals("40289781527cb97c01527cbd0d980000")){
					Resource resource = new Resource();
					resource.setUrl("/capital/account/view/opencapital.do");
					menu.setResource(resource);
				}
			}
		}
		
		model.addAttribute("moduleList", list);
		//model.addAttribute("jsList", getJs(request));
		model.addAttribute("user", newAccount);
		model.addAttribute("capitalStatus", account.getCapitalStatus());
		return "index";	
	}
	
	@RequestMapping("/helpinfo")
	public String helpinfo(HttpServletRequest request, Model model) {
		Account account = UserUtil.getUser(request);
		model.addAttribute("user", account);
		return "helpinfo";
	}
	
	 
	
	@RequestMapping("/main")
	public String main() { 
		
		
		return "main";
	}
	
	
	@RequestMapping("/getMenuList")
	@ResponseBody
	public List<Menu> getMenuList(String id, String pid, HttpServletRequest request) {
		if(id == null && pid != null){
			id = pid;
		}
		Account account = UserUtil.getUser(request);
		return this.menuService.getUserMenuList(account.getId(), id);
	}
	
	
	
	
//	/**
//	 * 加载前台界面的js实体资源文件
//	 * @param request
//	 * @return
//	 */
//	public List<String> getJs(HttpServletRequest request){
//		String realPath = request.getSession().getServletContext().getRealPath("/resources/js/model/");
//		File file = new File(realPath);
//		
//		List<String> list = new ArrayList<String>();
//		
//		getJsList(file, list);
//		
//		return list;
//	}
	
	public void getJsList(File file, List<String> list) {
		if (file.isDirectory()) {
			File[] f = file.listFiles();
			for (int i = 0; i < f.length; i++) {
				this.getJsList(f[i], list);
			}
		}
		if (file.isFile()) {
			String filePath = file.getParent() + File.separator + file.getName();
			int index = filePath.indexOf("\\model\\");
			if(index < 0){
				index = filePath.indexOf("/model/");
			}
			
			String js = filePath.substring(index + 7, filePath.length()).replace("\\", "/");
			
			list.add(js);
		}
	}
}
