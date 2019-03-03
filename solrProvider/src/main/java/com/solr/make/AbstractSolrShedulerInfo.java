package com.solr.make;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.global.StringUtil;
import com.memory.platform.memStore.MemShardStrore;
 
 

public class AbstractSolrShedulerInfo {
	public static interface IExcute{
		long excute (DruidPooledConnection connection,java.sql.Date currentTimeStamp);
	}

	@Autowired
	DruidDataSource dataSource;

	@Autowired
	MemShardStrore memStore;

	@Autowired
	    SolrUtils solrUtils;

	/*
	 * 同步的sql
	 * */
	String querySql;

	/*
	 * 查询timeStamp的sql
	 * */
	String  timeStampSql;

	/*
	 * timeStampKey在redis 中的键值 也是solr服务名称 如goodsBasic
	 * */
	String solrName;

	/*
	 * 时间戳在querySql中的名称
	 * */
	String timeStampName;

	/*
	 * 删除id 所在delete_record中的列值 如 goods_basic_id
	 * */
	String deleteColumnName;

	public  void  doSomthing() {
	
		do {
			try {
			
				excute(new IExcute() {
					
					@Override
					public long excute(DruidPooledConnection connection,java.sql.Date currentTimeStamp) {
						PreparedStatement  statement  =  null;
						long maxTimeStamp = currentTimeStamp.getTime();
						try {
							  statement  = connection.prepareStatement(querySql);
							  statement.setDate(1, currentTimeStamp);
							  ResultSet resultSet = statement.executeQuery();
							  ResultSetMetaData metaData = resultSet.getMetaData();
							  List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
							  SolrClient client = solrUtils.getClient(solrName);
							  while (resultSet.next()) {
								  SolrInputDocument  document = new SolrInputDocument();
								  for (int i = 1; i <= metaData.getColumnCount(); i++) {

										String columnName = metaData.getColumnLabel(i  );
									
										Object value = resultSet.getObject(i  );
										if( metaData.getColumnTypeName(i) == "BIT" ){ //bit boolean 值转换成 0和1 
											value = value ==null?0:value.equals(true)?1:0;
										}
										document.addField(columnName, value);
										
								  }
								  maxTimeStamp = Math.max(maxTimeStamp,  resultSet.getDate(timeStampName).getTime()); //取最大时间戳 用于判断变更凭证
								  
								  docs.add(document);
							}
							  resultSet.close();
							  client.add(docs);
							  client.commit();
						} catch ( Exception e) {
						
							maxTimeStamp = 0;
							e.printStackTrace();
						} 
						 
						 return maxTimeStamp;
					}
				} );
				
			} catch (Exception e) {
			 
			}
			
		} while (false);
		
	}

	public void excute(IExcute excute) {
		DruidPooledConnection connection = null;
		PreparedStatement statement  = null;
		do {
		
			try {
				  connection = dataSource.getConnection();
				  
				  statement  = connection.prepareStatement(timeStampSql);
				  String strTimeStamp = memStore.getClient().hget("solrTimeStamp",
						  solrName);
					long time = 0;
					//先执行timestamp 看是否能查询得到
					if (com.memory.platform.global.StringUtil.isNotEmpty(strTimeStamp)) {
						time = Long.parseLong(strTimeStamp);
					}
					 java.sql.Date date = new java.sql.Date (time);
					statement.setDate(1, date);
					ResultSet resultSet = statement.executeQuery();
					if(!resultSet.next()) {
						resultSet.close();
						break;
					}
					resultSet.close();
					/*
					 * 执行插入逻辑 获取最大时间戳
					 * */
				  long maxDate = excute.excute(connection,date);
				  memStore.getClient().hset("solrTimeStamp", //修改最大时间搓
						  solrName,maxDate+"");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (false);
		
		
		if(connection!=null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
		
	}

	void excuteDelete (){
		do {
				DruidPooledConnection connection;
				PreparedStatement  statement;
			try {
				  connection = dataSource.getConnection();
				  
				  statement  = connection.prepareStatement(timeStampSql);
			}catch(Exception exception ){
				
			}
		} while (false);
	}

	public DruidDataSource getDataSource() {
		return dataSource;
	}
	public String getDeleteColumnName() {
		return deleteColumnName;
	}
	public MemShardStrore getMemStore() {
		return memStore;
	}
	
	 public String getQuerySql() {
		return querySql;
	}
	public String getSolrName() {
		return solrName;
	}

	public SolrUtils getSolrUtils() {
		return solrUtils;
	}
	public String getTimeStampName() {
		return timeStampName;
	}
	public String getTimeStampSql() {
		return timeStampSql;
	}
	@PostConstruct
	public void init(){
		if(StringUtil.isEmpty(timeStampSql)) {
			setTimeStampSql( querySql);
		}
		if(StringUtil.isEmpty(timeStampName)) {
			timeStampName = "time_stamp";
		}
		if(StringUtil.isEmpty(deleteColumnName)) {
			deleteColumnName = solrName + "_id";
		}
	 
	}
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public void setDeleteColumnName(String deleteColumnName) {
		this.deleteColumnName = deleteColumnName;
	}
	public void setMemStore(MemShardStrore memStore) {
		this.memStore = memStore;
	}
	/*
	 * 字符串转码 sql在xml配置 小于符号要进行转码
	 * */
	private String xmlDecode(String xmlString){
		if(StringUtil.isEmpty(xmlString)) return xmlString;
		 return xmlString.replace("&lt;", xmlString);
	}
	public void setQuerySql(String querySql) {
		this.querySql = xmlDecode(querySql);
	}

	public void setSolrName(String solrName) {
		this.solrName = solrName;
	}
	public void setSolrUtils(SolrUtils solrUtils) {
		this.solrUtils = solrUtils;
	}
	 
	public void setTimeStampName(String timeStampName) {
		this.timeStampName = timeStampName;
	}
	
	public void setTimeStampSql(String timeStampSql) {
		this.timeStampSql = xmlDecode(timeStampSql);
	}
}
