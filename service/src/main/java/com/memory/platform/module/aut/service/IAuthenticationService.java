package com.memory.platform.module.aut.service;

import java.io.IOException;
import java.util.Map;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;

/**
* 创 建 人：杨佳桥
* 日    期： 2016年4月30日 下午9:09:59 
* 修 改 人： 
* 日   期： 
* 描   述：认证信息业务接口类
* 版 本 号：  V1.0
 */
public interface IAuthenticationService {
	
	/**
	* 功能描述： 商户认证信息保存、修改、补录
	* 输入参数:  @param company  商户信息
	* 输入参数:  @param idcard 身份证信息
	* 输入参数:  @param drivingLicense 驾驶证信息
	* 输入参数:  @param businessLicense   营业执照信息
	* 输入参数:  @param path 图片保存路劲
	* 输入参数:  @param account  登陆账户信息 
	* 输入参数:  @param type  保存类型：(save)保存
	* 输入参数:  @param aut_supplement_type  补录信息类型：idcard身份证信息、driving驾驶证信息、business营业执照信息
	* 输入参数:  @return
	* 输入参数:  @throws Exception
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:08:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> saveAuthenticationCompantInfo(Company company, Idcard idcard, DrivingLicense drivingLicense,
			BusinessLicense businessLicense,String path,Account account,String type,String aut_supplement_type) throws Exception;
	
	
	
	/**
	* 功能描述： 用户认证信息保存
	* 输入参数:  @param idcard  身份证信息
	* 输入参数:  @param drivingLicense 驾驶证信息
	* 输入参数:  @param path 保存文件路径
	* 输入参数:  @param account 登陆账户信息
	* 输入参数:  @param type 保存类型
	* 输入参数:  @param aut_supplement_type  补录信息类型：idcard身份证信息、driving驾驶证信息
	* 输入参数:  @return
	* 输入参数:  @throws Exception
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:06:26
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> saveAuthenticationUserInfo(Idcard idcard, DrivingLicense drivingLicense,
			String path,Account account,String type,String aut_supplement_type) throws Exception;
	
	/**
	* 功能描述： 验证数据的准确性
	* 输入参数:  @param company //商户对象
	* 输入参数:  @param idcard //身份证对象
	* 输入参数:  @param drivingLicense //驾驶证对象
	* 输入参数:  @param businessLicense //营业执照对象
	* 输入参数:  @param type //save:保存、edit:修改
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月9日下午6:40:14
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 * @throws Exception 
	 */
	public Map<String, Object> getCheckAuthenticationInfo(Company company, Idcard idcard, DrivingLicense drivingLicense,
			BusinessLicense businessLicense,String type) throws Exception;
	
	/**
	* 功能描述： 获取身份证上传文件的路径
	* 输入参数:  @param idcard //身份证信息
	* 输入参数:  @param path //上传文件路径
	* 输入参数:  @return
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:24:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Idcard
	 */
	public Idcard  getIdcardImagePath(Idcard idcard,String path,String type) throws IOException;
	
	/**
	* 功能描述： 获取驾驶证上传文件的路径
	* 输入参数:  @param drivingLicense 驾驶证信息
	* 输入参数:  @param path 上传文件路劲
	* 输入参数:  @return
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:27:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：DrivingLicense
	 */
	public DrivingLicense getDrivingLicenseImagePath(DrivingLicense drivingLicense,String path,String type) throws IOException;
	
	/**
	* 功能描述： 获取营业执照图片上传路径
	* 输入参数:  @param businessLicense 营业执照信息
	* 输入参数:  @param path 保存文件路径
	* 输入参数:  @return
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月17日下午2:31:47
	* 修 改 人: 
	* 日    期: 
	* 返    回：BusinessLicense
	 */
	public BusinessLicense getBusinessLicenseImagePath(BusinessLicense businessLicense,String path,String type) throws IOException;
}
