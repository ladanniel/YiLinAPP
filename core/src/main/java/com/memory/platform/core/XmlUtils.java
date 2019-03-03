package com.memory.platform.core;

import java.io.StringReader;
import java.io.StringWriter;

import javax.sound.sampled.AudioFormat.Encoding;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringEscapeUtils;

import com.memory.platform.global.sdk.LogUtil;

public class XmlUtils {
	
	
	public static String convertToXml(Object obj) {
		
		String xml = convertToXml(obj, "UTF-8");
		
		return StringEscapeUtils.unescapeXml(xml);
	}
	
	/**
	 * JavaBean转换成xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception ex) {
			LogUtil.writeErrorLog(ex.getMessage(), ex);
		}
		return result;
	}

	
	
	/**
	 * xml转换成JavaBean
	 * 
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception ex) {
			LogUtil.writeErrorLog(ex.getMessage(), ex);
		}
		return t;
	}
}
