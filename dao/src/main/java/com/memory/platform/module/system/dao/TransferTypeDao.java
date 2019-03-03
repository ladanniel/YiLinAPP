package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月10日 下午2:42:56 
* 修 改 人： 
* 日   期： 
* 描   述： 转账类型dao
* 版 本 号：  V1.0
 */
@Repository
public class TransferTypeDao extends BaseDao<TransferType> {

	/**
	* 功能描述： 分页列表
	* 输入参数:  @param transferType
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:55:31
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(TransferType transferType, int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from TransferType transferType where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(StringUtil.isNotEmpty(transferType.getSearch())){
			where.append(" and (transferType.name like :name or "
					+ "transferType.companyType.name like :rname)");
			map.put("name","%" + transferType.getSearch() + "%");
			map.put("rname","%" + transferType.getSearch() + "%");
		}
		where.append(" order by transferType.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	/**
	* 功能描述： 检测名称是否存在
	* 输入参数:  @param name
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:03:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：TransferType
	 */
	public TransferType checkName(String name,String typeId){
		String hql = " from TransferType transferType where transferType.name = :name and transferType.companyType.id = :typeId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("typeId", typeId);
		List<TransferType> list = this.getListByHql(hql, map);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	* 功能描述： 获取全部类型
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午5:16:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<TransferType>
	 */
	public List<TransferType> getAll(String companyTypeId){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyTypeId", companyTypeId);
		String hql = " from TransferType transferType where transferType.companyType.id = :companyTypeId";
		return this.getListByHql(hql,map);
	}
	
	/**
	* 功能描述： 检测名称是否存在
	* 输入参数:  @param name
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:03:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：TransferType
	 */
	public TransferType checkName(String name){
		String hql = " from TransferType transferType where transferType.name = :name";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		List<TransferType> list = this.getListByHql(hql, map);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	* 功能描述： 获取全部类型
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午5:16:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<TransferType>
	 */
	public List<TransferType> getAll(){
		String hql = " from TransferType transferType";
		return this.getListByHql(hql);
	}
	public List<Map<String, Object>> getTransferTypeForMap(String companyTypeId){
		StringBuffer sql = new StringBuffer("select vo.id,vo.name from sys_transfer_type as vo where vo.company_type_id = :companyTypeId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyTypeId", companyTypeId);
		return this.getListBySQLMap(sql.toString(), map);
	}
}
