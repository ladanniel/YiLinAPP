package com.memory.platform.module.account.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.Company.Status;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.module.account.service.IRegisterService;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.dao.CompanyDao;
import com.memory.platform.module.system.dao.CompanyTypeDao;
import com.memory.platform.module.system.dao.RoleDao;

/**
 * 
 * 创 建 人： 武国庆 日 期： 2016年4月30日 下午9:12:15 修 改 人： 日 期： 描 述：账户注册 服务类 版 本 号： V1.0
 */
@Service
public class RegisterService implements IRegisterService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private CompanyTypeDao companyTypeDao;

	@Override
	public void saveCompanyReg(Account account) {

		Company company = new Company();
		// 商户类型
		String companyTypeID = account.getCompanyType();
		Role role = roleDao.getAdminRoleByCompanyType(companyTypeID);
		if (role == null) {
			throw new BusinessException("角色不存在！");
		}
		CompanyType companyType = companyTypeDao.getCompanyTypeById(companyTypeID);
		company.setCompanyType(companyType);

		company.setAudit(Auth.notapply);// 未申请审（默认）
		company.setStatus(Status.open); // 启用
		company.setSource(1); // 注册来源前端
		company.setCreate_time(new Date());

		company.setContactTel(account.getPhone());
		companyDao.save(company);
		// 账户
		account.setStatus(Account.Status.start);// 启用状态
		if (companyTypeID.equals("2")) { // 货主
			if (account.getAccount() == null || StringUtils.isEmpty(account.getAccount())) {
				account.setAccount(account.getPhone() + "_hz");
			} else {
				account.setAccount(account.getAccount() + "_hz");
			}
			account.setRole_type(RoleType.consignor);
		} else if (companyTypeID.equals("4")) { // 车队
			if (account.getAccount() == null || StringUtils.isEmpty(account.getAccount())) {
				account.setAccount(account.getPhone() + "_cd");
			} else {
				account.setAccount(account.getAccount() + "_cd");
			}
			account.setRole_type(RoleType.truck);
		}
		if(null !=  accountDao.getAccountByAccount(account.getAccount())) {
			throw new BusinessException("账号已存在");
		}
		account.setAuthentication(Auth.notapply);// 认证状态
		account.setPassword(AppUtil.md5(account.getPassword()));
		account.setCompany(company);
		account.setRole(role);
		account.setCreate_time(new Date());
		account.setUserType(UserType.company);
		accountDao.save(account);
		company.setAccount_id(account.getId());
		company.setAdd_user_id(account.getId());
		companyDao.merge(company);
	}
}
