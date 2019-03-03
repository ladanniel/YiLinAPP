package com.memory.platform.module.own.service.impl;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.UserUtil;

public class BaseOwnService {
	public String getCompanyTypeName() {
		Account account = UserUtil.getAccount();
		if (account == null)
			return "";
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		return omcpanyTypeName;
	}

	public boolean isTruck() {

		return "车队".equals(getCompanyTypeName());
	}

	public boolean isDriver() {

		return "个人司机".equals(getCompanyTypeName());
	}

	public boolean isConsignor() {

		return "货主".equals(getCompanyTypeName());
	}

}