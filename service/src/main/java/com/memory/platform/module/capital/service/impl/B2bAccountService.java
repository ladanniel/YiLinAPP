package com.memory.platform.module.capital.service.impl;


import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.B2bAccount;
import com.memory.platform.entity.capital.B2bBankAccount;
import com.memory.platform.entity.capital.ResData;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.HttpService;
import com.memory.platform.module.capital.dao.B2bAccountDao;
import com.memory.platform.module.capital.service.IB2bAccountService;

/**
* 创 建 人： 
* 日    期： 2018年8月30日
* 描   	述： B2B服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class B2bAccountService implements IB2bAccountService {

	@Autowired
	private B2bAccountDao b2bAccountDao;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	
	//------------------------------------------------------修改商户号和IP地址--------------------------------------------------------------------
	//测试地址:端口
	String httpUrl="http://127.0.0.1:8080/B2BClient/http/MerchantRequestProcees";
	//商户号
	String merchantNo="12111171";
	
	
	@Override
	public ResData getKey() {
		//交易码
		String transCode= "100000";
		//报文
		String xmlReq = ""
				+"<Key>"+""+"</Key>";
		//添加请求头（xmlReq，版本号，未含签名，客户端时间，商户id，流水号（时间+交易码+序列号），交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", false, 3, 
				sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
		
		//返回
		ResData resData=getXmlToData(xmlData);
		
		return resData;
	}
	

	@Override
	public ResData saveB2bAccount(B2bAccount b2bAccount) {
		//交易码
		String transCode= "101010";
		//向银行请求B2B开户（报文）
		String xmlReq = ""
			+ "<TradeMemBerName>"+b2bAccount.getTradeMemBerName()+"</TradeMemBerName>"
			+ "<Currency>"+b2bAccount.getCurrency()+"</Currency>"
			+ "<SubAcc>"+b2bAccount.getSubAcc()+"</SubAcc>"
			+ "<BoothNo>"+b2bAccount.getBoothNo()+"</BoothNo>"
			+ "<TradeMemBerGrade>"+b2bAccount.getTradeMemBerGrade()+"</TradeMemBerGrade>"
			+ "<GradeCode>"+b2bAccount.getGradeCode()+"</GradeCode>"
			+ "<TradeMemberProperty>"+b2bAccount.getTradeMemberProperty()+"</TradeMemberProperty>"
			+ "<Contact>"+b2bAccount.getContact()+"</Contact>"
			+ "<ContactPhone>"+b2bAccount.getContactPhone()+"</ContactPhone>"
			+ "<Phone>"+b2bAccount.getTradeMemBerName()+"</Phone>"
			+ "<ContactAddr>"+b2bAccount.getContactAddr()+"</ContactAddr>"
			+ "<BusinessName>"+b2bAccount.getBusinessName()+"</BusinessName>"
			+ "<PapersType>"+b2bAccount.getPapersType()+"</PapersType>"
			+ "<PapersCode>"+b2bAccount.getPapersCode()+"</PapersCode>"
			+ "<IsMessager>"+b2bAccount.getIsMessager()+"</IsMessager>"
			+ "<MessagePhone>"+b2bAccount.getMessagePhone()+"</MessagePhone>"
			+ "<Email>"+b2bAccount.getEmail()+"</Email>";
		
		//添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
				sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
		
		//返回
		ResData resData=getXmlToData(xmlData);
		
		//解析XML,开户成功后保存到本地数据库
		if("000000".equals(resData.getCode())) {
			/**开户成功将用户信息保存到本地数据库*/
			
			
		}
		return resData;
	}

	@Override
	public ResData getB2bAccountByNo(String subAccount) {
		//交易码
		String transCode= "101020";
		String xmlReq = ""
						+  "<SubAccount>"+subAccount+"</SubAccount>";
		//添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
				sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
		//返回
	   ResData resData=getXmlToData(xmlData);
		
		return resData;
	}
	

	@Override
	public ResData getB2bAccountByBoothNo(String boothNo) {
		//交易码
		String transCode= "201030";
		//请求查询（报文）
		String xmlReq= ""
				+"<BoothNo/>"+boothNo+"</BoothNo>";
		
		//添加请求头（xmlReq，版本号，未含签名，客户端时间，商户id，流水号（时间+交易码+序列号），交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
				sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
		
		//返回
		ResData resData=getXmlToData(xmlData);
		
		return resData;
	}

	
	@Override
	public ResData updateB2bAccount(B2bAccount b2bAccount) {
		//交易码
	   String transCode= "101050";
	   //请求更新（报文）
	   String xmlReq= ""
				+"<TradeMemCode/>"+b2bAccount.getTradeMemCode()+"</TradeMemCode>"
				+"<TradeMemBerName>"+b2bAccount.getTradeMemBerName()+"</TradeMemBerName>"
				+"<Currency>"+b2bAccount.getCurrency()+"</Currency>"
			    +"<SubAcc>"+b2bAccount.getSubAcc()+"</SubAcc>"
				+"<Contact>"+b2bAccount.getContact()+"</Contact>"
				+"<ContactPhone>"+b2bAccount.getContactPhone()+"</ContactPhone>"
				+"<Phone>"+b2bAccount.getPhone()+"</Phone>"
				+"<ContactAddr>"+b2bAccount.getContactAddr()+"</ContactAddr>"
				+"<BusinessName>"+b2bAccount.getBusinessName()+"</BusinessName>"
				+"<PapersType>"+b2bAccount.getPapersType()+"</PapersType>"
                +"<PapersCode>"+b2bAccount.getPapersCode()+"</PapersCode>"
                +"<IsMessager>"+b2bAccount.getIsMessager()+"</IsMessager>"
                +"<MessagePhone>"+b2bAccount.getMessagePhone()+"</MessagePhone>"
                +"<Email>"+b2bAccount.getEmail()+"</Email>" ;
	   
		//添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
		//返回
		ResData resData=getXmlToData(xmlData);
		
		return resData;
	}


	@Override
	public ResData updateB2bAccountState(String subAccount, String tradeMemCode, Integer state, Boolean isCoerce) {
		//交易码
	  	String transCode= "101060";
	    //请求更新（报文）
	    String xmlReq = "<SubAccount>"+subAccount+"</SubAccount>"
                 + "<TradeMemCode>"+tradeMemCode+"</TradeMemCode>"
                 +"<state>"+state+"</state>"
                 +"<IsCoerce>"+(isCoerce?1:0)+"</IsCoerce>";
	    
		//添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		//POST
		String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
		//返回
	    ResData resData=getXmlToData(xmlData);
		return resData;
	}


	@Override
	public ResData bindBankAccount(B2bBankAccount b2bBankAccount) {
		//交易码
		  String transCode= "101070";
		//请求（报文）
		  String xmlReq = "<OperType>"+b2bBankAccount.getOperType().getIndex()+"</OperType>"
                +"<SubAccount>"+b2bBankAccount.getSubAccount()+"</SubAccount>"
                +"<TradeMemCode>"+b2bBankAccount.getTradeMemCode()+"</TradeMemCode>"
                +"<LinkAccountType>"+b2bBankAccount.getLinkAccountType()+"</LinkAccountType>"
                +"<IsOther>"+b2bBankAccount.getIsOther()+"</IsOther>"
                +"<AccountSign>"+b2bBankAccount.getAccountSign()+"</AccountSign>"
                +"<IsOtherBank>"+b2bBankAccount.getIsOtherBank()+"</IsOtherBank>"
                +"<SettleAccountName>"+b2bBankAccount.getSettleAccountName()+"</SettleAccountName>"
                +"<SettleAccount>"+b2bBankAccount.getSettleAccount()+"</SettleAccount>"
                +"<IsSecondAcc>"+b2bBankAccount.getIsSecondAcc()+"</IsSecondAcc>"
                +"<PayBank>"+b2bBankAccount.getPayBank()+"</PayBank>"
                +"<BankName>"+b2bBankAccount.getBankName()+"</BankName>"
                +"<PapersType>"+b2bBankAccount.getPapersType()+"</PapersType>"
                +"<PapersCode>"+b2bBankAccount.getPapersCode()+"</PapersCode>"
                +"<StrideValidate>"+b2bBankAccount.getStrideValidate()+"</StrideValidate>"
                +"<CurrCode>"+b2bBankAccount.getCurrCode()+"</CurrCode>" ;
		 //添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		 String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		 //POST
		 String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
		 //返回
		 ResData resData=getXmlToData(xmlData);
		 return resData;
	}


	@Override
	public ResData bindBankAccountResult(String subAccount, String tradeMemCode, int outComeAccountType) {
		//交易码
		String transCode= "101080";
		//请求（报文）
		  String xmlReq = "<SubAccount>"+subAccount+"</SubAccount>"
				                    +"<TradeMemCode>"+tradeMemCode+"</TradeMemCode>"
				                    +"<OutComeAccountType>"+outComeAccountType+"</OutComeAccountType>";
		//添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
			String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
	   //POST
			String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
	   //返回
		   ResData resData=getXmlToData(xmlData);
		  
		return resData;
	}


	@Override
	public ResData outComeCapital(String payCode, String subAccount, String tradeMemCode, Double outComeMoney,
			String channelNo, Double sumSubMoney, Double otherBankCost, Double cusPayMoney, Double merOtherPayMoney,
			int outComeAccountType, String outAccount, String outAccountName, String tradeAbstract) {
		  //交易码
		  String transCode= "103010";
		  //请求（报文）
		  String xmlReq = "<PayCode>"+payCode+"</PayCode>"
                +"<SubAccount>"+subAccount+"</SubAccount>"
                +"<TradeMemCode>"+tradeMemCode+"</TradeMemCode>"
                +"<OutComeMoney>"+outComeMoney+"</OutComeMoney>"
                +"<ChannelNo>"+channelNo+"</ChannelNo>"
                +"<SumSubMoney>"+sumSubMoney+"</SumSubMoney>"
                +"<OtherBankCost>"+otherBankCost+"</OtherBankCost>"
                +"<CusPayMoney>"+cusPayMoney+"</CusPayMoney>"
                +"<MerOtherPayMoney>"+merOtherPayMoney+"</MerOtherPayMoney>"
                +"<OutComeAccountType>"+outComeAccountType+"</OutComeAccountType>"
                +"<OutAccount>"+outAccount+"</OutAccount>"
                +"<OutAccountName>"+outAccountName+"</OutAccountName>"
                +"<TradeAbstract>"+tradeAbstract+"</TradeAbstract>";

		  //添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
			String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
			//POST
			String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
			//返回
			ResData resData=getXmlToData(xmlData);
		  
		return resData;
	}


	@Override
	public ResData onCapital(String payCode, String subAccount, String tradeMemCode, Double inMoney,String tradeAbstract) {
		 //交易码
		  String transCode= "103020";
		  //请求（报文）
		  String xmlReq = "<PayCode>"+payCode+"<PayCode>"
                +"<SubAccount>"+subAccount+"</SubAccount>"
                +"<TradeMemCode>"+tradeMemCode+"</TradeMemCode>"
                +"<InMoney>"+inMoney+"</InMoney>"
                +"<TradeAbstract>"+tradeAbstract+"</TradeAbstract>";
		  
		  //添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		  String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
				  sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		  //POST
		  String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
		  //返回
		  ResData resData=getXmlToData(xmlData);
		  
		  return resData;
	}


	@Override
	public ResData outComeCapitalRecordByPayCode(String subAccount, String oldOutComePayCode) {
		 // 交易码
		 String transCode= "103030";
		 //请求（报文）
		 String xmlReq = "<SubAccount>"+subAccount+"</SubAccount>"+"<OldOutComePayCode>"+oldOutComePayCode+"</OldOutComePayCode>";
		 
		 //添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		 String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
					sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		 //POST
			String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
			//返回
		   ResData resData=getXmlToData(xmlData);
		  
		return resData;
	}


	@Override
	public ResData inquiryBalance(String tradeMemCode, String subAccount, String currency) {
		 // 交易码
		 String transCode= "103130 ";
		 //请求（报文）
		 String xmlReq = "<TradeMemCode>"+tradeMemCode+"</TradeMemCode>"+"<SubAccount>"+subAccount+"</SubAccount>"+"<Currency>"+currency+"</Currency>";
		 
		 //添加请求头（xmlReq，版本号，签名，客户端时间，商户id，流水号(时间+交易码+序列号)，交易码）
		 String xml = convertToXmlConHead(xmlReq.toString(), "1.0", true, 3, 
				sdf.format(new Date()), merchantNo, sdf.format(new Date())+transCode+"0001", transCode) ;
		 //POST
		 String xmlData=HttpService.b2bPost(xml, httpUrl, BaseSdk.encoding_GBK);
			
		 //返回
		 ResData resData=getXmlToData(xmlData);
		  
		 return resData;
	}

	
	
	
	
	

	//以下公共方法
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * SSL证书
	 * 
	 * */
	
	
	
	/**
	 * 报文签名
	 * 
	 * */
	
	
	/**
	 * 添加B2B请求头
	 * 
	 * @version 		版本号（交易时要判断版本匹配）；
	 * @isSign 		是否签名；false
	 * @severModel 	服务模式：3 = 新大宗三方存管模式；
	 * @clientTime 	客户端时间：YYYYMMDDHHMMSS；
	 * @merchantNo 	商户代码；12111171
	 * @transCodeId 	交易流水号；YYYYMMDDHH24MISS或YYYYMMDDHHMMSS + 6位交易码 + 4位序列号(0001) 
	 * @transCode 		交易码（6位数字交易码）；
	 * */
	public String convertToXmlConHead(String  xmlReq,String version,Boolean isSign,int severModel,
			String clientTime,String merchantNo,String transCodeId,String transCode) {
		
		StringBuffer xmlTag= new StringBuffer();
		xmlTag.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		xmlTag.append("<CPMB2B>");
		xmlTag.append("<MessageData>");
		// 报文基本信息
		xmlTag.append("<Base>");
		xmlTag.append("<Version>");		xmlTag.append(version);		xmlTag.append("</Version>");
		xmlTag.append("<SignFlag>");	xmlTag.append(isSign?1:0);	xmlTag.append("</SignFlag>");
		xmlTag.append("<SeverModel>");	xmlTag.append(severModel);	xmlTag.append("</SeverModel>");
		xmlTag.append("</Base>");
		// 系统信息
		xmlTag.append("<ReqHeader>");
		xmlTag.append("<ClientTime>");	xmlTag.append(clientTime);	xmlTag.append("</ClientTime>");
		xmlTag.append("<MerchantNo>");	xmlTag.append(merchantNo);	xmlTag.append("</MerchantNo>");
		xmlTag.append("<TransCodeId>");	xmlTag.append(transCodeId);	xmlTag.append("</TransCodeId>");
		xmlTag.append("<TransCode>");	xmlTag.append(transCode);	xmlTag.append("</TransCode>");
		xmlTag.append("</ReqHeader>");
		//交易请求消息
		xmlTag.append("<DataBody>");	
		xmlTag.append(xmlReq);	 /**请求内容**/
		xmlTag.append("</DataBody>");
		
		xmlTag.append("</MessageData>");
		xmlTag.append("</CPMB2B>");
		
		return xmlTag.toString();
	}
	
	/** 
	 * 获取B2B数据节点(Code,Message,Data)的值
	 * 
	 * @param xmlString XML文本 
     */
	 public  ResData getXmlToData(String xmlString){  
	 	String code="",message="",data="";
		 	
 		String rateCodeMessage="";
        StringReader read = new StringReader(xmlString);  
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入  
        InputSource source = new InputSource(read);  
        //创建一个新的SAXBuilder  
        SAXBuilder saxbBuilder = new SAXBuilder();  
        try {  
            //通过输入源构造一个Document  
            Document doc = saxbBuilder.build(source);  
            //这里的是 《CPMB2B      -根元素  
            Element root = doc.getRootElement();  
            List<?> node = root.getChildren();  
            //这里的是 《MessageData
            Element rootMe=(Element) node.get(0);
            List<?> nodeMe = rootMe.getChildren();
            //这里是响应头 《ResHeader
            Element rootHe=(Element) nodeMe.get(1);
            List<?> nodeHe = rootHe.getChildren();
            //这里是相应状态 《State
            Element rootSt=(Element) nodeHe.get(2);
            List<?> nodeSt = rootSt.getChildren(); 
            //这里是响应码
            Element elementCode=((Element)nodeSt.get(0));
            if(elementCode.getName().equals("Code")) {
            	code=elementCode.getValue();
            }
            //这里是响应信息
            Element elementMessage=((Element)nodeSt.get(1));
            if(elementMessage.getName().equals("Message")) {
            	message=elementMessage.getValue();
            }
            //这里是响应报文体
            Element elementDt=(Element) nodeMe.get(2);
            if(elementDt.getName().equals("DataBody")) {
            	data = elementDt.getValue();
            }
        } catch (JDOMException e) {  
            e.printStackTrace(); 
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        
        ResData resData=new ResData();
        resData.setCode(code);
        resData.setMessage(message);
        resData.setData(data);
        
        return resData; 
	 }



	
}
