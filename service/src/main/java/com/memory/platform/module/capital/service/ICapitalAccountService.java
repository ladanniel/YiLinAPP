package com.memory.platform.module.capital.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.entity.member.Account;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午6:59:37 
* 修 改 人： 
* 日   期： 
* 描   述： 资金账户服务接口
* 版 本 号：  V1.0
 */
public interface ICapitalAccountService {

	/**
	* 功能描述： 获取资金账户信息
	* 输入参数:  @param userId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午7:56:12
	* 修 改 人: 
	* 日    期: 
	* 返    回：CapitalAccount
	 */
	CapitalAccount getCapitalAccount(String userId);
	
	/**
	* 功能描述： 设置支付密码
	* 输入参数:  @param payPassword
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月13日下午5:10:40
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void savePayPassword(Account account);
	
	/**
	* 功能描述： 分页用户资金列表
	* 输入参数:  @param capitalAccount
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日上午9:54:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(CapitalAccount capitalAccount,int start,int limit);
	
	/**
	* 功能描述： 根据id获取资金帐户详情
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日上午10:43:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：CapitalAccount
	 */
	CapitalAccount getCapitalAccountById(String id);
	
	/**
	* 功能描述： Excel数据源
	* 输入参数:  @param capitalAccount
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午5:59:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CapitalAccount>
	 */
	List<CapitalAccount> getList(CapitalAccount capitalAccount);
	
	/**
	* 功能描述： 转账业务
	* 输入参数:  @param capitalAccount  转账资金账户
	* 输入参数:  @param toCapitalAccount	接受转账资金账户
	* 输入参数:  @param money	转账金额
	* 输入参数:  @param remark  转账用户资金记录备注信息
	* 输入参数:  @param toRemark 接受转账资金资金记录备注信息
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月30日下午5:29:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveTransferTool(CapitalAccount capitalAccount,CapitalAccount toCapitalAccount,BigDecimal money,Type type,String remark,String toRemark);
	
	/**
	* 功能描述： 冻结资金 - 服务接口
	* 输入参数:  @param account  冻结账号
	* 输入参数:  @param money	冻结金额
	* 输入参数:  @param remark	备注信息
	* 输入参数:  @param operator	操作人
	* 输入参数:  @param algorithm	  true 增加资金   false减少资金
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月1日上午9:50:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveFreezeTool(Account account,BigDecimal money,String remark,String operator);
	
	/**
	* 功能描述： 解冻资金 - 服务接口
	* 输入参数:  @param account  解冻账号资金
	* 输入参数:  @param money		解冻资金
	* 输入参数:  @param remark	备注信息	
	* 输入参数:  @param operator		操作人
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月1日上午10:35:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateFreezeTool(Account account,BigDecimal money,String remark,String operator);
	
	/**
	* 功能描述： 统计平台资金信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月5日下午2:40:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getSystemCapitalInfo();
	
	/**
	* 功能描述： 统计平台资金
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日下午7:59:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getTotalCapital();
	
	/**
	* 功能描述： 统计个人资金
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日下午8:00:27
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getTotalCapital(Account account);

	Map<String, Object> getCapitalForMap(String userId);

	void savePayment(CapitalAccount capitalAccount, CapitalAccount toCapitalAccount, BigDecimal money, Type type,
			String remark, String toRemark);

	/**
	 * 仲裁赔偿
	 * */
	void saveArbitrationCompensateFor(CapitalAccount capitalAccount, CapitalAccount toCapitalAccount, BigDecimal money,
			Type type, String remark, String toRemark);

	void updateArbitrationCompensateFor(CapitalAccount account, BigDecimal money, String remark, String operator);
}
