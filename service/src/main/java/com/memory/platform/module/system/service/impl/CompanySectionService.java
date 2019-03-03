package com.memory.platform.module.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.module.system.dao.CompanySectionDao;
import com.memory.platform.module.system.service.ICompanySectionService;


/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月27日 上午10:40:54 
* 修 改 人： 
* 日   期： 
* 描   述： 组织机构实现类
* 版 本 号：  V1.0
 */
@Service
public class CompanySectionService implements ICompanySectionService {

	@Autowired
	private CompanySectionDao companySectionDao;

	@Override
	public List<CompanySection> getList(String companyId) {
		return companySectionDao.getList(companyId);
	}

	@Override
	public Map<String, Object> saveCompanySection(CompanySection companySection) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			companySection.setCreate_time(new Date());
			companySectionDao.save(companySection);
			if(companySection.getParent_id() != null && !"".equals(companySection.getParent_id())) {
				CompanySection parent = companySectionDao.getObjectById(CompanySection.class, companySection.getParent_id());
				companySection.setOrder_path(parent.getOrder_path()+"_"+companySection.getId());
			}else {
				companySection.setParent_id("0");
				companySection.setOrder_path(companySection.getId());
			}
			companySectionDao.update(companySection);
			map.put("msg", "恭喜！添加成功！");
			map.put("success", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "添加失败，请联系管理员");
			map.put("success", false);
			return map;
		}
		
	}

	@Override
	public List<CompanySection> getListLikeNotById(String id) {
		return companySectionDao.getListLikeNotById(id);
	}

	@Override
	public CompanySection getCompanySection(String id) {
		return companySectionDao.getObjectById(CompanySection.class, id);
	}

	@Override
	public Map<String, Object> updateCompanySection(CompanySection companySection) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CompanySection persistent = getCompanySection(companySection.getId());
			persistent.setName(companySection.getName());
			persistent.setDescs(companySection.getDescs());
			companySectionDao.update(persistent);
			map.put("msg", "恭喜！修改成功！");
			map.put("success", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "修改失败，请联系管理员");
			map.put("success", false);
			return map;
		}
	}

	@Override
	public Map<String, Object> deleteCompanySection(String id, String companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		CompanySection persistent = getCompanySection(id);
		if(persistent != null && companyId != null && !"".equals(companyId)) {
			if(companyId.equals(persistent.getCompany_id())) {
				if(!"0".equals(persistent.getParent_id() ) ) {
					if(isChild(persistent.getId())) {
						map.put("success", false);
						map.put("msg", "存在子集组织机构");
						return map;
					}
					if(isUserActv(persistent.getId())) {
						map.put("success", false);
						map.put("msg", "该组织机构有用户在使用");
						return map;
					}
					companySectionDao.delete(persistent);
					map.put("msg", "删除成功");
					map.put("success", true);
					return map;
				}else {
					map.put("msg", "顶级节点不能删除");
					map.put("success", false);
					return map;
				}
				
			}
		}
		return map;
	}
	
	public boolean isUserActv(String id) {
		List<Account> list = companySectionDao.getListAccount(id);
		if(list.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isChild(String parentId) {
		List<CompanySection> list = companySectionDao.getListLikeNotById(parentId);
		if(list.size()>0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public CompanySection getCompanySectionByCompanyId(String id) {
		return companySectionDao.getCompanySectionByCompanyId(id);
	}

	@Override
	public List<CompanySection> getListByCompany(String companyId) {
		return companySectionDao.getListByCompany(companyId);
	}

	@Override
	public List<CompanySection> getParentListByCompany(String companyId) {
		return companySectionDao.getParentListByCompany(companyId);
	}
	
	@Override
	public List<Map<String,Object>> getCompanySectionByCompanyIdForMap(String companyId) {
		return companySectionDao.getCompanySectionByCompanyIdForMap(companyId);
	}
}
