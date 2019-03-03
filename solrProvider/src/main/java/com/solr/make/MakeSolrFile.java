package com.solr.make;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.memory.platform.core.AppUtil;
import com.memory.platform.core.FreemarkerUtil;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.solr.dao.SolrGoodsBasicDao;
import com.memory.platform.module.solr.service.impl.SolrGoodsBasicService;
 

/*
 * by lil
 * 根据sql创建Solr文件
 * */
@Service
public class MakeSolrFile {
	Logger logger = Logger.getLogger(MakeSolrFile.class);
	@Autowired
	SolrGoodsBasicService dbService;
	static String outputDir;
	static String templateDir;

	FreemarkerUtil freemarkerUtil;
	FreemarkerUtil scheduleJavaFtl;

	static {
		outputDir = MakeSolrFile.class.getClassLoader().getResource("")
				.toString()
				+ "solrOutput/";
		outputDir = outputDir.replace("file:/", "");
		templateDir = MakeSolrFile.class.getClassLoader().getResource("")
				.toString()
				+ "solrTemplate/";
		templateDir = templateDir.replace("file:/", "");
	}

	public MakeSolrFile() {
		freemarkerUtil = FreemarkerUtil.getInstance("solrTemplate");

	}

	static void initConfig(String[] args) {
		String appPath = "";
		if (args != null && args.length > 0) {
			System.out.println(args);
			String p = "";
			for (int i = 0; i < args.length; i++) {
				p += args[i];
			}
			appPath = p;

		}
		if (StringUtil.isEmpty(appPath)) {
			appPath = "classpath:application.xml";
		}

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { appPath });
		context.start();
	}

	public static void main(String[] args) throws IOException {
		initConfig(args);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line = null;
		do {
			//line = reader.readLine();
			MakeSolrFile info = (MakeSolrFile) AppUtil.getApplicationContex()
					.getBean("makeSolrFile");
			 Map<String, AbstractSolrShedulerInfo>	infos = AppUtil.getApplicationContex().getBeansOfType(AbstractSolrShedulerInfo.class);
			 for (Entry<String, AbstractSolrShedulerInfo> entry : infos.entrySet()) {
				
				 AbstractSolrShedulerInfo infoShe = entry.getValue();
				 System.out.println(String.format("=========生成%s ", infoShe.getSolrName()));
				info.makeFileWithScheduler( infoShe );

			}
			
			 
		} while (StringUtil.isNotEmpty(line) && !"exit".equals(line));

	}

	private List makeDBModel(String sql) {
		com.alibaba.druid.pool.DruidDataSource dataSource = (DruidDataSource) AppUtil
				.getApplicationContex().getBean("dataSource");
		ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>>();
		try {
			PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
			statement.setDate(1, new Date(0));
			ResultSet resultSet =statement.executeQuery();
			if (resultSet.next()) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					HashMap<String, Object> columnData = new HashMap<String, Object>();
					String type = metaData.getColumnTypeName(i + 1);
				 
					String columnName = metaData.getColumnLabel(i + 1);
					columnData.put("name", columnName);
					columnData.put("type", type);
					ret.add(columnData);
				}
			}
			resultSet.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return ret;
	}

	/*
	 * 根据Sql创建Solr配置文件 有3个文件 1:schema 文件 这个是solr配置文件 2:同步文件 .java 3:spring 配置文件
	 */
	private void makeFileWithScheduler(AbstractSolrShedulerInfo schedulerInfo) {
		List<Map<String, Object>> model = makeDBModel(schedulerInfo.getQuerySql());
		Map<String, Object> outModel = new HashMap<String, Object>();
		outModel.put("data", model);
		freemarkerUtil.fprint(outModel, "schema.ftl", outputDir + schedulerInfo.getSolrName()
				+ "schema.xml");
		freemarkerUtil.fprint(outModel, "SolrScheduler.ftl", outputDir
				+ schedulerInfo.getSolrName() + "Scheduler.java");
	}

}
