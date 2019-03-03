package com.memory.platform.module.global.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.bouncycastle.jce.provider.symmetric.AES.OFB;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;

import com.memory.platform.core.AppUtil;
import com.memory.platform.core.MapUtil;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.global.StringUtil;
 

/**
 * Hibernate查询、删除、修改数据操作
 * 
 * @author yangyang
 *
 * @param <T>
 */
public class BaseDao<T> {
	private SessionFactory sessionFactory;
	Logger loger = Logger.getLogger(BaseDao.class); 
	/*
	 * 添加Solr行为
	 * */
	public SolrClient getSolrClient(String clientType)   {
		SolrClient ret = null;
		SolrUtils utils =   AppUtil.getApplicationContex().getBean(SolrUtils.class);
		do {
			if(utils ==null){
				loger.info("配置文件为配置SolrUtil");
				break;
			}
			ret =  utils.getClient(clientType);
		} while (false);
		return ret ;
	}
	public Map<String, Object>  querySolr(String clientType,String queryKey,String orderBy,String hilight, int start ,int rows) {
		 Map<String, Object>  ret = new  HashMap<String,Object>();
		do {
			SolrClient client  = getSolrClient(clientType);
			if(client == null) {
				break;
			}
			SolrQuery params = new SolrQuery();
			params.set("q", queryKey);
			params.set("start", start);
			params.set("rows", rows);
			
			if(StringUtil.isNotEmpty(orderBy)) {
				params.add("sort", orderBy);
			}
			if(StringUtil.isNotEmpty(hilight)) {
				params.add("hl", "true");
				params.add("hl.fl",hilight);
			}
			SolrDocumentList docs;
			try {
				docs = client.query(params).getResults();
			} catch (java.lang.Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}  
			if(docs.size()>0) {
				ret = new HashMap<String, Object>();
				 List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>(docs.size());
				for (SolrDocument sd : docs) {
					// 找出数据，组织回发
					lst.add(MapUtil.toHashMap(sd.getFieldValueMap()));
				}
				ret.put("list", lst);
				
			}
			ret.put("total", docs.getNumFound());
		
		} while (false);
	 
		return ret ;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void cloassSession(Session session) {
		session.close();
	}

	/**
	 * 对象保存
	 * 
	 * @param t（实体对象）
	 */
	public void save(T t) {
		getSession().save(t);
	}

	/**
	 * 删除单条数据
	 * 
	 * @param t（实体对象）
	 */
	public void delete(T t) {
		getSession().delete(t);
	}

	/**
	 * 修改数据
	 * 
	 * @param t（实体对象）
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/**
	 * 通过SQL修改数据
	 * 
	 * @param sql（SQL语句）
	 * @param objects（需要修改的数据值）
	 */
	public void updateSQL(String sql, Object[] objects) {
		Query query = getSession().createSQLQuery(sql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 通过SQL修改数据
	 * 
	 * @param sql（SQL语句）
	 * @param objects（需要修改的数据值）
	 */
	public void updateSQL(String sql, Map<String, Object> map) {
		Query query = getSession().createSQLQuery(sql);
		this.setParameter(query, map);
		query.executeUpdate();
	}

	/**
	 * 通过HQL修改数据
	 * 
	 * @param sql（HQL语句）
	 * @param objects（需要修改的数据值）
	 */
	public void updateHQL(String hql, Map<String, Object> map) {
		Query query = getSession().createQuery(hql).setProperties(map);
		query.executeUpdate();
	}

	/**
	 * 删除一组数据
	 * 
	 * @param t（删除的实体对象）
	 * @param ids（一组ID）
	 */
	@SuppressWarnings("unused")
	public void DeleteAllSelectRecord(T t, int[] ids) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < ids.length; i++) {
			T t1 = this.getObjectById(t.getClass(), ids[i] + "");
			if (t == null) {
				continue;
			} else {
				list.add(t1);
			}
		}
		if (list.size() != 0) {
			 
				for (T t2 : list) {
					getSession().delete(t2);
				}

 
		}
	}

	/**
	 * 通过HQL查询数据
	 * 
	 * @param hql
	 *            （查询的HQL语句）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getObjectByHql(String hql) {
		List<T> list = new ArrayList<T>();
		list = (List<T>) this.getSession().createQuery(hql).list();
		return list.get(0);
	}

	/**
	 * 通过带条件的HQL语句，查询数据
	 * 
	 * @param hql（HQL语句）
	 * @param map（查询的条件值）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getListByHql(String hql, Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		list = (List<T>) this.getSession().createQuery(hql).setCacheable(true).setProperties(map).list();
		return list;
	}

	/**
	 * 通过带条件SQL查询数据，返回List集合
	 * 
	 * @param sql（SQL语句）
	 * @param map（条件值）
	 * @param class1（需要映射到哪个实体里面）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> getListBySql(String sql, Map<String, Object> map, Class class1) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		list = (List<T>) this.getSession().createSQLQuery(sql).addEntity(class1).setProperties(map).setCacheable(true)
				.list();
		return list;
	}

	/**
	 * 通过SQL查询集合数据，返回类型为List<map>
	 * 
	 * @param sql（SQL语句）
	 * @param map（条件值）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getListBySQLMap(String sql, Map<String, Object> map) {
		// Long l1 = System.currentTimeMillis();
		// System.out.println("开始时间：------------------------"+l1);
		// 获得SQLQuery对象
		Query query = this.getSession().createSQLQuery(sql).setProperties(map);
		// 设定结果结果集中的每个对象为Map类型
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		// 执行查询
		List list = query.list();
		// Long l2 = System.currentTimeMillis();
		// System.out.println("结束时间：------------------------"+l2);
		// System.out.println("总消耗时间：------------------------"+(l2-l1));
		return list;
	}

	/**
	 * 通过SQL查询集合数据，返回类型为List<map>
	 * 
	 * @param sql（SQL语句）
	 * @param map（条件值）
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListBySQL(String sql, Map<String, Object> map) {
		Query query = this.getSession().createSQLQuery(sql).setProperties(map);
		return query.list();
	}

	/**
	 * 通过SQL查询集合数据，返回List<map>,条件为Object数组
	 * 
	 * @param sql（SQL语句）
	 * @param objects（Object数组，条件值）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getListBySQLMap(String sql, Object[] objects) {
		// 获得SQLQuery对象
		Query query = this.getSession().createSQLQuery(sql);
		if (objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		// 设定结果结果集中的每个对象为Map类型
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		// 执行查询
		List list = query.list();
		return list;
	}

	/**
	 * 功能描述： 通过sql返回map对象数据 输入参数: @param sql 输入参数: @param map 输入参数: @return 异 常：
	 * 创 建 人: yangjiaqiao 日 期: 2016年8月8日上午11:03:43 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getSqlMap(String sql, Map<String, Object> map) {
		// Long l1 = System.currentTimeMillis();
		// System.out.println("开始时间：------------------------"+l1);
		// 获得SQLQuery对象
		Query query = this.getSession().createSQLQuery(sql);
		query = this.setParameter(query, map);
		// 设定结果结果集中的每个对象为Map类型
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		// 执行查询
		Map<String, Object> map_result = (Map) query.uniqueResult();
		// Long l2 = System.currentTimeMillis();
		// System.out.println("结束时间：------------------------"+l2);
		// System.out.println("总消耗时间：------------------------"+(l2-l1));
		return map_result;
	}

	/**
	 * 通过简单的HQL查询，返回集合数据
	 * 
	 * @param hql（hql语句）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getListByHql(String hql) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		list = (List<T>) this.getSession().createQuery(hql).list();
		return list;
	}

	/**
	 * 通过ID返回实体对象
	 * 
	 * @param class1
	 *            （需要查询的实体Class）
	 * @param id（查询的ID值）
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getObjectById(Class class1, String id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(class1, id);
	}

	/**
	 * 通过ID返回实体对象
	 * 
	 * @param class1
	 *            （需要查询的实体Class）
	 * @param id（查询的ID值）
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T loadObjectById(Class class1, String id) {
		// TODO Auto-generated method stub
		return (T) getSession().load(class1, id);
	}

	/**
	 * 通过HQL查询，返回对象数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param map
	 *            （条件值）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getObjectByColumn(String hql, Map<String, Object> map) {
		return (T) getSession().createQuery(hql).setProperties(map).uniqueResult();
	}

	/**
	 * 通过HQL查询，返回对象数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param map
	 *            （条件值）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getSingleByHql(String hql, Map<String, Object> map) {

		List<T> lsTs = getSession().createQuery(hql).setProperties(map).setMaxResults(1).setFirstResult(0).list();
		return (lsTs == null || lsTs.size() == 0) ? null : lsTs.get(0);

	}

	/**
	 * 查询分页数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param properties
	 *            （条件参数，MAP（key,value）
	 * @param offset
	 *            (第几页)
	 * @param length
	 *            （多少条）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPage(String hql, Map<String, Object> properties, int offset, int length) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		int count = 0;
		String countHql = hql.indexOf("order by") > 0 ? hql.substring(0, hql.indexOf("order by")) : hql;
		String hql_1 = "select count(*) " + countHql;
		Query queryCount = getSession().createQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		Long l = (Long) queryCount.uniqueResult();
		count = Integer.valueOf(l.toString());
		Query query = getSession().createQuery(hql);
		query = this.setParameter(query, properties);
		query.setFirstResult(offset * length);
		query.setMaxResults(length);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 查询分页数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param properties
	 *            （条件参数，MAP（key,value）
	 * @param offset
	 *            (第几页)
	 * @param length
	 *            （多少条）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageSQLMap(String sql, Map<String, Object> properties, int offset, int length) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		String countSql = sql.indexOf("order by") > 0 ? sql.substring(0, sql.indexOf("order by")) : sql;
		String hql_1 = "SELECT count(*) FROM " + "(  " + countSql + ")aa";
		Query queryCount = getSession().createSQLQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		BigInteger countBigInteger = (BigInteger) queryCount.uniqueResult();
		count = countBigInteger == null ? 0 : countBigInteger.intValue();
		SQLQuery query = getSession().createSQLQuery(sql);
		query = (SQLQuery) this.setParameter(query, properties);
		((SQLQuery) query).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(offset * length);
		query.setMaxResults(length);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	public Map<String, Object> getPageSql(String sql, Map<String, Object> properties, int offset, int length,
			Class class1) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		int count = 0;
		String countSql = sql.indexOf("order by") > 0 ? sql.substring(0, sql.indexOf("order by")) : sql;
		countSql = sql.indexOf("GROUP BY") > 0 ? sql.substring(0, sql.indexOf("GROUP BY")) : sql;
		String hql_1 = "select count(*) " + countSql.substring(countSql.indexOf("FROM"), countSql.length());
		Query queryCount = getSession().createSQLQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		BigInteger countBigInteger = (BigInteger) queryCount.uniqueResult();
		count = countBigInteger.intValue();
		SQLQuery query = getSession().createSQLQuery(sql);
		query = (SQLQuery) this.setParameter(query, properties);
		((SQLQuery) query).addEntity(class1);
		query.setFirstResult(offset * length);
		query.setMaxResults(length);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageSqlTransformer(String sql, Map<String, Object> properties, int offset, int length,
			Class class1) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		int count = 0;
		String countSql = sql.indexOf("order by") > 0 ? sql.substring(0, sql.indexOf("order by")) : sql;
		String hql_1 = "select count(*) " + countSql.substring(countSql.indexOf("FROM"), countSql.length());
		Query queryCount = getSession().createSQLQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		BigInteger countBigInteger = (BigInteger) queryCount.uniqueResult();
		count = countBigInteger.intValue();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(class1));
		query = (SQLQuery) this.setParameter(query, properties);
		query.setFirstResult(offset * length);
		query.setMaxResults(length);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getSqlTransformer(String sql, Map<String, Object> properties, Class class1) {
		// TODO Auto-generated method stub
		List<T> list = new ArrayList<T>();
		int count = 0;
		String countSql = sql.indexOf("order by") > 0 ? sql.substring(0, sql.indexOf("order by")) : sql;
		String hql_1 = "select count(*) " + countSql.substring(countSql.indexOf("FROM"), countSql.length());
		Query queryCount = getSession().createSQLQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		BigInteger countBigInteger = (BigInteger) queryCount.uniqueResult();
		count = countBigInteger.intValue();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(class1));
		query = (SQLQuery) this.setParameter(query, properties);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	protected Query setParameter(Query query, Map<String, Object> map) {
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				// 这里考虑传入的参数是什么类型，不同类型使用的方法不同
				if (obj instanceof Collection<?>) {
					query.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(string, (Object[]) obj);
				} else {
					query.setParameter(string, obj);
				}
			}
		}
		return query;
	}

	/**
	 * 通过HQL查询，有多少条数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param map
	 *            （条件值）
	 * @return
	 */
	public int getCountHql(String hql, Map<String, Object> map) {
		int count = 0;
		String hql_1 = "select count(*) " + hql;
		Long l = (Long) getSession().createQuery(hql_1).setProperties(map).uniqueResult();
		count = Integer.valueOf(l.toString());
		return count;
	}

	/**
	 * 通过SQL查询数据量
	 * 
	 * @param sql
	 *            （SQL语句）
	 * @param map
	 *            （条件值）
	 * @return
	 */
	public int getCountSql(String sql, Map<String, Object> map) {
		int count = 0;
		Integer l = Integer.parseInt(getSession().createSQLQuery(sql).setProperties(map).uniqueResult().toString());
		count = l;
		return count;
	}

	/**
	 * 通过SQL查询数据量
	 * 
	 * @param sql
	 *            （SQL语句）
	 * @param map
	 *            （条件值）
	 * @return
	 */
	public boolean getCountSqlIs(String sql, Map<String, Object> map) {
		int count = 0;
		Integer l = Integer.parseInt(getSession().createSQLQuery(sql).setProperties(map).uniqueResult().toString());
		count = l;
		return count > 0 ? false : true;
	}

	/**
	 * 通过带条件SQL查询数据，返回List集合
	 * 
	 * @param sql（SQL语句）
	 * @param map（条件值）
	 * @param class1（需要映射到哪个实体里面）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListBySql(String sql, Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list = (List<String>) this.getSession().createSQLQuery(sql).setProperties(map).list();
		return list;
	}

	/**
	 * 查询分页数据
	 * 
	 * @param hql
	 *            （HQL语句）
	 * @param properties
	 *            （条件参数，MAP（key,value）
	 * @param offset
	 *            (第几页)
	 * @param length
	 *            （多少条）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageSqlListMap(String sql, Map<String, Object> properties, int offset, int length) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		String countSql = sql.indexOf("order by") > 0 ? sql.substring(0, sql.indexOf("order by")) : sql;
		String hql_1 = "SELECT count(*) FROM " + "(  " + countSql + ")aa";
		Query queryCount = getSession().createSQLQuery(hql_1);
		queryCount = this.setParameter(queryCount, properties);
		BigInteger countBigInteger = (BigInteger) queryCount.uniqueResult();
		count = countBigInteger == null ? 0 : countBigInteger.intValue();
		SQLQuery query = getSession().createSQLQuery(sql);
		query = (SQLQuery) this.setParameter(query, properties);
		((SQLQuery) query).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(offset * length);
		query.setMaxResults(length);
		list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("list", list);
		return map;
	}

	/**
	 * 修改数据
	 * 
	 * @param t（实体对象）
	 */
	public void merge(T t) {
		getSession().merge(t);
	}
}
