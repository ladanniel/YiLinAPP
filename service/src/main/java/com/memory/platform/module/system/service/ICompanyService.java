package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;

/**
* 创 建 人：杨佳桥
* 日    期： 2016年5月5日 下午5:34:07 
* 修 改 人： 
* 日   期： 
* 描   述： 商户信息-业务接口
* 版 本 号：  V1.0
 */
public interface ICompanyService {
	/**
	* 功能描述： 通过ID查询商户信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午5:37:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：Company
	 */
	 public Company getCompanyById(String id);
	 
	 
	 /**
		* 功能描述： 通过ID查询商户信息
		* 输入参数:  @param id
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年5月5日下午5:37:55
		* 修 改 人: 
		* 日    期: 
		* 返    回：Company
		 */
		 public Company getLoadById(String id);
	 
	 
	 /**
	  * 
	 * 功能描述： 
	 * 输入参数:  @param company
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月12日下午9:42:53
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：void
	  */
	 public void updateCompany(Company company);
	 
	 /**
	 * 功能描述： 通过名称获取商户
	 * 输入参数:  @param name //商户名称
	 * 输入参数:  @param id  //商户ID
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月5日下午7:14:42
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：Company
	  */
	 public Company getCompanyByName(String name,String id);
	 
	 /**
	 * 功能描述： 商户信息审核分页查询
	 * 输入参数:  @param company 商户信息对象
	 * 输入参数:  @param start 
	 * 输入参数:  @param limit
	 * 输入参数:  @return
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月10日下午4:42:13
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：Map<String,Object>
	  */
	 public Map<String, Object> getPageCompanyAut(Company company ,int start, int limit);
	 
	/**
	* 功能描述： 审核商户信息
	* 输入参数:  @param companyId 商户ID
	* 输入参数:  @param isfalse 是否通过
	* 输入参数:  @param failed  未通过的原因
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月11日上午9:39:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> saveAutCompany(String companyId,boolean isfalse,String failed );
	
	
	/**
	 * 功能描述： 商户信息分页查询
	 * 输入参数:  @param company 商户信息对象
	 * 输入参数:  @param start 
	 * 输入参数:  @param limit
	 * 输入参数:  @return
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月10日下午4:42:13
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：Map<String,Object>
	  */
	 public Map<String, Object> getPageCompany(Company company ,int start, int limit);
	 
	 
		/**
		 * 功能描述： 商户信息分页查询
		 * 输入参数:  @param company 商户信息对象
		 * 输入参数:  @param start 
		 * 输入参数:  @param limit
		 * 输入参数:  @return
		 * 异    常： 
		 * 创 建 人: yangjiaqiao
		 * 日    期: 2016年5月10日下午4:42:13
		 * 修 改 人: 
		 * 日    期: 
		 * 返    回：Map<String,Object>
		  */
		 public  Map<String, Object>  getPageCompanyByName(String name ,String companyTypeName,int start, int limit);
	 
	 /**
	 * 功能描述： 增加商户信息，保存账户信息和商户信息
	 * 输入参数:  @param adduser　添加人的用户对象
	 * 输入参数:  @param account　需保存的用户对象
	 * 输入参数:  @param company　需保存的商户信息对象
	 * 输入参数:  @return String 商户ID
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月26日上午9:37:18
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：Map<String,Object>
	  */
	 public String saveAccountCompany(Account adduser,Account account,Company company);
	 
	 /**
	 * 功能描述：  保存商户认证信息
	 * 输入参数:  @param company 商户信息对象
	 * 输入参数:  @param idcard  身份证信息对象
	 * 输入参数:  @param drivingLicense 驾驶证信息对象
	 * 输入参数:  @param businessLicense  营业执照信息对象
	 * 输入参数:  @param path  保存路径
	 * 输入参数:  @param account 添加人对象
	 * 输入参数:  @param type  操作类型
	 * 输入参数:  @return
	 * 输入参数:  @throws Exception
	 * 异    常： 
	 * 创 建 人: yangjiaqiao
	 * 日    期: 2016年5月26日下午1:50:23
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：Map<String,Object>
	  */
	 public Map<String, Object> saveCompantAuthInfo(Company company, Idcard idcard, DrivingLicense drivingLicense,
				BusinessLicense businessLicense,String path,Account account,String type) throws Exception;
	 
	 
	/**
	* 功能描述： 修改商户字段信息
	* 输入参数:  @param updateColume 修改的字段信息
	* 输入参数:  @return
	* 输入参数:  @throws Exception
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月27日下午1:52:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	 public void updateCompanyFiled(UpdateColume updateColume) throws Exception;
	 /**
		* 功能描述： 查询所有商户
		* 异    常： 
		* 创 建 人: liyanzhang
		* 日    期: 2016年6月12日 16:57:49
		* 修 改 人: 
		* 日    期: 
		* 返    回：List<Company>
		 */
	 public List<Company> getCompanyList();
	 
	 /*
	  * aiqiwu 2017-06-05
	  * */
	 public List<Company> getCompanyListByTypeId(String typeId);


	Map<String, Object> getCompanyByAccountId(String id);
	 
}
