package com.memory.platform.module.system.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.dao.BusinessLicenseDao;
import com.memory.platform.module.aut.dao.DrivingLicenseDao;
import com.memory.platform.module.aut.dao.IdcardDao;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.dao.CompanyDao;
import com.memory.platform.module.system.dao.CompanySectionDao;
import com.memory.platform.module.system.dao.CompanyTypeDao;
import com.memory.platform.module.system.dao.RoleDao;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ISendMessageService;


/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月5日 下午5:35:35 
* 修 改 人： 
* 日   期： 
* 描   述： 商户业务接口实现类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class CompanyService implements ICompanyService{

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CompanySectionDao companySectionDao;
	@Autowired
	private IdcardDao idcardDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CompanyTypeDao companyTypeDao;
	
	@Autowired
	private DrivingLicenseDao drivingLicenseDao;
	
	@Autowired
	private BusinessLicenseDao businessLicenseDao;
	@Autowired
	private ISendMessageService sendMessageService; 
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	IPushService pushService;
	/*  
	 *  getCompanyById
	 */
	@Override
	public Company getCompanyById(String id) {
		// TODO Auto-generated method stub
		return companyDao.getObjectById(Company.class, id);
	}
	
	/*  
	 *  getCompanyByName
	 */
	@Override
	public Company getCompanyByName(String name,String id) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyByName(name,id);
	}

	/*  
	 *  getPage
	 */
	@Override
	public Map<String, Object> getPageCompanyAut(Company company, int start, int limit) {
		return companyDao.getPage(company, start, limit);
	}

	/*  
	 *  saveAutCompany
	 */
	@Override
	public Map<String, Object> saveAutCompany(String companyId, boolean isfalse, String failed) {
		String inPoint = "com.memory.platform.module.system.service.impl.CompanyService.saveAutCompany";
		Account autAccount = UserUtil.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		Company company = companyDao.getObjectById(Company.class, companyId);
		Account account = accountDao.getObjectById(Account.class, company.getAccount_id());
		if(company.getAudit().equals(Auth.auth)){
			map.put("success", false);
			map.put("msg",  "【"+company.getName()+"】商户已经审核通过，无需重复审核!");
		}else if(company.getAudit().equals(Auth.supplement)){
			map.put("success", false);
			map.put("msg",  "【"+company.getName()+"】商户正在补录认证信息，还不能进行审核!");
		}else if(company.getAudit().equals(Auth.notAuth)){
			map.put("success", false);
			map.put("msg",  "【"+company.getName()+"】商户已经审核[未通过]，无需重复审核!");
		}else if(company.getAudit().equals(Auth.notAuth)){
			map.put("success", false);
			map.put("msg",  "【"+company.getName()+"】商户已经审核[未通过]，无需重复审核!");
		}
		if(isfalse){
			if(companySectionDao.getList(company.getId()).size() <= 0){
				CompanySection companySection = new CompanySection();
				companySection.setName(company.getName());
				companySection.setCreate_time(new Date());
				companySection.setCompany_id(company.getId());
				companySection.setAdd_user_id(autAccount.getId());
				companySection.setDescs(company.getName());
				companySection.setParent_id("0");
				companySection.setLeav(1);
				companySectionDao.save(companySection);
				account.setCompanySection(companySection);
			}
			
			company.setAudit(Auth.auth);
			company.setFailed_msg(null);
			company.setAut_time(new Date());
			company.setAut_user_id(autAccount.getId());
			companyDao.update(company); 
			
			if(null != company.getIdcard_id()){
				account.setIdcard_id(company.getIdcard_id());
				Idcard idcard = idcardDao.getObjectById(Idcard.class,company.getIdcard_id());
				account.setName(idcard.getName());
			}
			if(null != company.getDriving_license_id()){
				account.setDriving_license_id(company.getDriving_license_id());
			}
			account.setAuthentication(Auth.auth);
			accountDao.update(account);
			AppUtil.setLoginUser(account);
			String sendMsg = "尊敬的用户您好！您所提交的商户认证信息已通过审核，畅享我们的服务吧！";
			//Map<String, Object> map_v = sendMessageService.saveRecordAndSendMessage(account.getPhone(), sendMsg, inPoint);
			//if (!Boolean.valueOf(map_v.get("success").toString())) {
				//throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
			//}
			Map<String, String> sendMap = new HashMap<String, String>();
			pushService.saveRecordAndSendMessageWithAccountID(account.getId(), "【认证审核】", sendMsg,
					sendMap, "CompanyService.saveAutCompany");
			map.put("success", true);
			map.put("msg", "【"+company.getName()+"】商户信息审核成功!");
		}else{
			if(StringUtils.isBlank(failed)){
				map.put("success", false);
				map.put("msg", "请输入未通过的原因!");
			}else{
				String sendMsg = "";
				if(company.getAudit().equals(Auth.waitProcessSupplement)){
					company.setAudit(Auth.supplement);
					company.setFailed_msg("<span style='color: red'>您所补录的认证信息，管理员审核【未通过】原因为:</span><span style='color: #1ab394;'>"+failed+"</span>");
					sendMsg = "尊敬的用户您好！您所补录的商户认证信息未通过审核，原因为:"+failed;
				}else{
					company.setAudit(Auth.notAuth);
					company.setFailed_msg(failed);
					sendMsg = "尊敬的用户您好！您所提交的商户认证信息未通过审核，原因为:"+failed;
				}
				company.setAut_time(new Date());
				company.setAut_user_id(autAccount.getId());
				companyDao.update(company);
				account.setAuthentication(Auth.notAuth);
				accountDao.update(account);
				AppUtil.setLoginUser(account);
				//Map<String, Object> map_v = sendMessageService.saveRecordAndSendMessage(account.getPhone(), sendMsg, inPoint);
				//if (!Boolean.valueOf(map_v.get("success").toString())) {
					//throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
				//}
				Map<String, String> sendMap = new HashMap<String, String>();
				pushService.saveRecordAndSendMessageWithAccountID(account.getId(), "【认证审核】", sendMsg,
						sendMap, "CompanyService.saveAutCompany");
				map.put("success", true);
				map.put("msg",  "【"+company.getName()+"】商户信息审核成功!");
			}
		}
		return map;
	}

	/*  
	 *  updateCompany
	 */
	@Override
	public void updateCompany(Company company) {
		companyDao.update(company);
	}

	/*  
	 *  getPageCompany
	 */
	@Override
	public Map<String, Object> getPageCompany(Company company, int start, int limit) {
		if(company.getCompanyAuths() == null || company.getCompanyAuths().length == 0){
			Auth[] auths = {Auth.auth};
			company.setCompanyAuths(auths);
		}
		return companyDao.getPage(company, start, limit);
	}

	/*  
	 *  saveAccountCompany
	 */
	@Override
	public String saveAccountCompany(Account adduser, Account account, Company company) {
		Role role = roleDao.getAdminRoleByCompanyType(company.getCompanyTypeId());
		if(role==null){
			throw new BusinessException("角色不存在！");
		}
		CompanyType companyType = companyTypeDao.getCompanyTypeById(company.getCompanyTypeId());
		company.setCompanyType(companyType);
		company.setSource(0); //注册来源前端
		company.setCreate_time(new Date());
		company.setAdd_user_id(adduser.getId());
		company.setAudit(Auth.notapply);//未申请审（默认）
		company.setStatus(Company.Status.open);
		companyDao.save(company);
		if(!companyType.getIs_aut()){
			account.setName(company.getName());
			CompanySection companySection = new CompanySection();
			companySection.setName(company.getName());
			companySection.setCreate_time(new Date());
			companySection.setCompany_id(company.getId());
			companySection.setAdd_user_id(account.getId());
			companySection.setDescs(company.getName());
			companySection.setParent_id("0");
			companySection.setLeav(1);
			companySectionDao.save(companySection);
			account.setCompanySection(companySection);
		}
		account.setRole(role);
		account.setStatus(Account.Status.start);//启用状态
		account.setAuthentication(Auth.notapply);//认证状态
		account.setPassword(AppUtil.md5(account.getPassword()));
		account.setCompany(company);
		account.setAdd_user_id(adduser.getId());
		account.setCreate_time(new Date());
		account.setUserType(UserType.company);
		accountDao.save(account); 
		company.setAccount_id(account.getId());
		companyDao.update(company);
		
		return company.getId();
		
		
	}

	/*  
	 *  saveCompantAuthInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveCompantAuthInfo(Company company, Idcard idcard, DrivingLicense drivingLicense,
			BusinessLicense businessLicense, String path, Account account, String type) throws Exception {
		Map<String, Object> map_check = authenticationService.getCheckAuthenticationInfo(null, idcard, drivingLicense, businessLicense, type);
		Account accountUser = accountDao.getObjectById(Account.class, company.getAccount_id());
		if((boolean) map_check.get("isfalse")){
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if(idcard != null && idcard.getIdcard_no() != null && !"".equals(idcard.getIdcard_no())){
					idcard.setAdd_user_id(account.getId());
					idcard.setCreate_time(new Date());
					idcard.setAccount_id(account.getId());
					idcard.setCompany_id(company.getId());
					idcard = authenticationService.getIdcardImagePath(idcard, path,type);
					if(type.equals("save")){
						idcardDao.save(idcard);
					}
					company.setIdcard_id(idcard.getId());
					accountUser.setName(idcard.getName());
					accountUser.setIdcard_id(idcard.getId());
				}
				if(drivingLicense != null && drivingLicense.getDriving_license_no() != null && !"".equals(drivingLicense.getDriving_license_no())){
					drivingLicense.setAdd_user_id(account.getId());
					drivingLicense.setCreate_time(new Date());
					drivingLicense.setAccount_id(account.getId());
					drivingLicense.setCompany_id(company.getId());
					drivingLicense.setValid_end_date(DateUtil.getDateAddYear(drivingLicense.getValid_start_date(), drivingLicense.getValid_year()));
					drivingLicense = authenticationService.getDrivingLicenseImagePath(drivingLicense, path,type);
					if(type.equals("save")){
						drivingLicenseDao.save(drivingLicense);
					}
					company.setDriving_license_id(drivingLicense.getId());
					accountUser.setDriving_license_id(drivingLicense.getId());
				}
				if(businessLicense != null && businessLicense.getCompany_no() != null && !"".equals(businessLicense.getCompany_no())){
					businessLicense.setAdd_user_id(account.getId());
					businessLicense.setCreate_time(new Date());
					businessLicense.setAccount_id(account.getId());
					businessLicense = authenticationService.getBusinessLicenseImagePath(businessLicense, path,type);
					if(type.equals("save")){
						businessLicenseDao.save(businessLicense);
					} 
					company.setBusiness_license_id(businessLicense.getId());
				}
				company.setUpdate_user_id(account.getId());
				company.setUpdate_time(new Date());
				if(companySectionDao.getList(company.getId()).size() <= 0){
					CompanySection companySection = new CompanySection();
					companySection.setName(company.getName());
					companySection.setCreate_time(new Date());
					companySection.setCompany_id(company.getId());
					companySection.setAdd_user_id(account.getId());
					companySection.setDescs(company.getName());
					companySection.setParent_id("0");
					companySection.setLeav(1);
					companySectionDao.save(companySection);
					accountUser.setCompanySection(companySection);
				}
				company.setAudit(Auth.auth);
				company.setFailed_msg(null);
				company.setAut_time(new Date());
				company.setAut_user_id(account.getId());
				accountUser.setAuthentication(Auth.auth);
				companyDao.update(company);
				accountDao.merge(accountUser);
				map.put("success", true);
				map.put("msg", "认证信息添加成功！");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", e.getMessage());
			}
			return map;
		}else{
			return ((Map<String, Object>) map_check.get("map"));
		}
	}

	/*  
	 *  updateCompanyFiled
	 */
	@Override
	public void updateCompanyFiled(UpdateColume updateColume) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String updateHql = " update Company company set company."+updateColume.getField()+" = :filedValue where company.id = :id"; 
		if(updateColume.getField().equals("status")){
			Company.Status mytype = Enum.valueOf(Company.Status.class, updateColume.getField_value());
			map.put("filedValue", mytype);
		}else{
			map.put("filedValue", updateColume.getField_value());
		}
		map.put("id", updateColume.getId());
		companyDao.updateHQL(updateHql, map);
		
	}

	@Override
	public List<Company> getCompanyList() {
		return companyDao.getCompanyList();
	}

	/*  
	 *  getLoadById
	 */
	@Override
	public Company getLoadById(String id) {
		// TODO Auto-generated method stub
		return companyDao.loadObjectById(Company.class, id);
	}

	@Override
	public Map<String, Object> getPageCompanyByName(String name,String conpanyTypeName, int start, int limit) {
		return companyDao.getPageCompanyByName(name, conpanyTypeName,start, limit);
	}
	@Override
	public Map<String, Object> getCompanyByAccountId(String id) {
		return companyDao.getCompanyByAccountId(id);
	}

	/*
	 * aiqiwu 2017-06-05
	 * */
	@Override
	public List<Company> getCompanyListByTypeId(String typeId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyListByTypeId(typeId);
	}
}
