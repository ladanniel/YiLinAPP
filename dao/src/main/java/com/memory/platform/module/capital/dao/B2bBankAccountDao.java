package com.memory.platform.module.capital.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.B2bAccount;
import com.memory.platform.entity.capital.B2bBankAccount;
import com.memory.platform.module.global.dao.BaseDao;



/**
 * 绑定出入金账户 本地账户
 * 
 * */
@Repository
public class B2bBankAccountDao extends BaseDao<B2bBankAccount> {
	
	/**
	 * 添加一条出入金账户（绑定账户，银行服务添加成功后才添加本地账户）
	 * 
	 * */
	public B2bBankAccount addb2bBankAccount(B2bBankAccount b2bBankAccount) {
		this.addb2bBankAccount(b2bBankAccount);
		return null;
		
	}
	/**
	 * 修改出入金账户信息 （银行服务修改成功后才修改）
	 * 
	 * */
	public B2bBankAccount updateb2bBankAccount(B2bBankAccount b2bBankAccount) {
		this.update(b2bBankAccount);
		return null;
		
	}
	
	/**
	 * 查询本地保存的所有出入金账户
	 * 
	 * */

	public B2bBankAccount saveb2bBankAccount(B2bBankAccount b2bBankAccount) {
		this.save(b2bBankAccount);
		return null;
		
	}
	

}
