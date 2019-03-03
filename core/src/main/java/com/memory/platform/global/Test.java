package com.memory.platform.global;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Test {

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
//		String str = HTTPUtil.sendGet("http://106.ihuyi.cn/webservice/sms.php?method=Submit&account=cf_15285112550&password=xl19900517&mobile=15285112550&content=%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E6%98%AF%EF%BC%9A%E3%80%90%E5%8F%98%E9%87%8F%E3%80%91%E3%80%82%E8%AF%B7%E4%B8%8D%E8%A6%81%E6%8A%8A%E9%AA%8C%E8%AF%81%E7%A0%81%E6%B3%84%E9%9C%B2%E7%BB%99%E5%85%B6%E4%BB%96%E4%BA%BA%E3%80%82");
//		Document doc = DocumentHelper.parseText(str); 
//		Element root = doc.getRootElement();
//
//
//		String code = root.elementText("code");	
//		String msg = root.elementText("msg");	
//		String smsid = root.elementText("smsid");	
//		
//		
//		System.out.println(code);
//		System.out.println(msg);
//		System.out.println(smsid);
		String txnAmt = "200.0";
		if(txnAmt.contains(".")){
			System.out.println(txnAmt.substring(0, txnAmt.indexOf(".")));
		}
	}

}
