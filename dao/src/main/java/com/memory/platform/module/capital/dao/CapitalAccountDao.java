package com.memory.platform.module.capital.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午6:47:10 
* 修 改 人： 
* 日   期： 
* 描   述： 资金账户dao
* 版 本 号：  V1.0
 */
@Repository
public class CapitalAccountDao extends BaseDao<CapitalAccount> {

	/**
	* 功能描述： 获取资金账户信息
	* 输入参数:  @param userId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午7:57:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：CapitalAccount
	 */
	public CapitalAccount getCapitalAccount(String userId){
		String hql = " from CapitalAccount capitalAccount where capitalAccount.account.id = '" + userId + "'";
		List<CapitalAccount> list = this.getListByHql(hql);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	/**
	* 功能描述： 
	* 输入参数:  @param capitalAccount
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日上午9:59:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(CapitalAccount capitalAccount,int start, int limit){
		StringBuffer hql = new StringBuffer(" from CapitalAccount capitalAccount where 1 = 1");
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotEmpty(capitalAccount.getSearch())){
			where.append(" and (capitalAccount.account.account = :account or "
					+ "capitalAccount.account.phone = :phone or "
					+ "capitalAccount.account.name = :name)");
			map.put("account", capitalAccount.getSearch());
			map.put("phone", capitalAccount.getSearch());
			map.put("name", capitalAccount.getSearch());
		}
		where.append(" order by capitalAccount.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	/**
	* 功能描述： excel数据源
	* 输入参数:  @param capitalAccount
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午6:00:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CapitalAccount>
	 */
	public List<CapitalAccount> getList(CapitalAccount capitalAccount){
		StringBuffer hql = new StringBuffer(" from CapitalAccount capitalAccount where 1 = 1");
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotEmpty(capitalAccount.getSearch())){
			where.append(" and (capitalAccount.account.account = :account or "
					+ "capitalAccount.account.phone = :phone or "
					+ "capitalAccount.account.name = :name)");
			map.put("account", capitalAccount.getSearch());
			map.put("phone", capitalAccount.getSearch());
			map.put("name", capitalAccount.getSearch());
		}
		where.append(" order by capitalAccount.create_time desc");
		return this.getListByHql(hql.append(where).toString(), map);
	}
	
	/**
	* 功能描述： 根据账号查找资金账户
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月27日下午5:07:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：CapitalAccount
	 */
	public CapitalAccount getCapitalAccountByAccount(String account){
		StringBuffer hql = new StringBuffer(" from CapitalAccount capitalAccount where capitalAccount.account.account = :account");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("account", account);
		return this.getListByHql(hql.toString(), map).get(0);
	}
	
	/**
	* 功能描述： 统计平台资金情况
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月5日下午2:39:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> getSystemCapitalInfo(){
		String sql = "SELECT  SUM(`total`)  AS total,SUM(`avaiable`)  AS avaiable,SUM(`frozen`)  AS frozen,SUM(`totalrecharge`)  AS totalrecharge,SUM(`totalcash`)  AS totalcash FROM `mem_capital_account`";
		return this.getListBySQLMap(sql, new HashMap()).get(0);
	}

	public Map<String, Object> getCapitalForMap(String userId){
		String sql = "select vo.total,vo.avaiable,vo.frozen,vo.totalrecharge,vo.totalcash from mem_capital_account as vo where vo.account_id = '" + userId + "'";
		Map<String, Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = this.getListBySQLMap(sql, map);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
}
