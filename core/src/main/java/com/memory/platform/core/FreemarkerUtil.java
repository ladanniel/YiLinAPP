package com.memory.platform.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	// Freemarker工具类
	private static FreemarkerUtil util;
	// 初使化FreeMarker配置
	private static Configuration config;

	private FreemarkerUtil() {

	}

	/**
	 * 初始化FreemarkerUtil对象
	 * 
	 * @param pname
	 * @return
	 */
	public static FreemarkerUtil getInstance(String pname) {
		if (util == null) {
			config = new Configuration();
		//	config.setClassForTemplateLoading(FreemarkerUtil.class, pname);
			String path = 	FreemarkerUtil.class.getClassLoader().getResource("").toString().replace("file:/", "") +pname ;
			 
		
			try {
				config.setDirectoryForTemplateLoading(new File( path ));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			util = new FreemarkerUtil();
		}
		return util;
	}

	/**
	 * 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致 否则会出现乱码
	 * 
	 * @param fname
	 * @return
	 */
	private Template getTemplate(String fname) {
		try {
			return config.getTemplate(fname,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过标准输出流输出模板内容
	 * 
	 * @param root
	 * @param fname
	 */
	public void sprint(Map<String, Object> root, String fname) {
		try {
			getTemplate(fname).process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 通过标准输出流输出模板内容
	 * 
	 * @param root
	 * @param fname
	 */
	public void fprint(Map<String, Object> root, String fname, String outPath) {
		try {
			// 合并数据模型与模板
			File file = new File(outPath);
			if(file.exists() == false){
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(outPath);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			getTemplate(fname).process(root, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
