package com.yun.utils.pay;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class XmlUtils {
	public final static String CHECK_STR="1111111111111111111111111111111111111111111111111111111111111111";//双方之间的秘钥
	public final static String sRootName="transaction";
	public final static String sHeadName="transaction_header";
	public final static String sBodyName="transaction_body";
	public final static String[] headlist={"version","transtype","employno","termid","request_time","mac"};
	public final static String[] p033bodyList={"queryId","orderno","cod","payway","banktrace","postrace","tracetime","cardid","dssn","dsname","memo"};
	public final static String[] p036bodyList={"orderno","cod","cardid","banktrace","postrace","cxbanktrace"};
	public final static String[] responseHeadNodes={"version","transtype","termid","employno","response_time","response_code","response_msg"};
	public final static String[] responseP033BodyNodes={};
	public final static String[] responseP036BodyNodes={};
	public final static String[] responseErrorBodyNodes={};
	private static java.text.SimpleDateFormat sdfLongTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 解析xml字符串
	 * @author 作者：李启明
	 * @Title: analyzeXml 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param xmlStr
	 * @return 
	 * @return(返回类型) Map<String,Object>     
	 * @throws
	 */
	public static Map<String,Object> readXml(String xmlStr){
		Map<String,Object> xmlDataMap=new HashMap<String,Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Element  theElem = null, root = null, head = null, body = null, theElemRespCode, theElemRespMsg;
		try {
			factory.setIgnoringElementContentWhitespace(true);

			DocumentBuilder db = factory.newDocumentBuilder();
			StringReader sr = new StringReader(xmlStr);
			InputSource is = new InputSource(sr);
			Document xmldoc = db.parse(is);
			//先找到根节点
			root = xmldoc.getDocumentElement();		
			 //获取head节点
	        head=(Element)root.getElementsByTagName(sHeadName).item(0);
			//取出报文头
			if(headlist !=null && headlist.length>0){
				//提取各类数据
				for(String nodeName:headlist){
					theElem = (Element)head.getElementsByTagName(nodeName).item(0);
					if(theElem == null){
						if(theElem == null){
							xmlDataMap.put("error","[" + nodeName + "]节点未找到");
							return xmlDataMap;
						}
					}
					//如果找到了，就把这个值赋值给
					try {
						xmlDataMap.put(nodeName, theElem.getTextContent());
					} catch (Exception e) {
						xmlDataMap.put(nodeName, "");
					}
				}
				/**
				 * TODO 继续提取报文体，但是考虑到商户可能不止一个交易通知类型，不同通知类型，报文体不一样，因此报文体单独出来提取
				 */
			}
			//获取签名验证
			String mac=(String) xmlDataMap.get("mac");
			if(!validateMAC(mac,xmlStr)){
				xmlDataMap.put("error", "ERP MAC验证失败");
				return xmlDataMap;
			}
			
			//获取交易类型，不同的交易请求的数据不一样
			String transtype=(String) xmlDataMap.get("transtype");
			//找到数据体
            body = (Element)root.getElementsByTagName(sBodyName).item(0);
			if(body == null){
				xmlDataMap.put("error", "解析XML，未找到[" + sBodyName +"]数据");
				return xmlDataMap;
			}
			if("P033".equals(transtype)){
				for(String nodeName:p033bodyList){
					theElem=(Element) body.getElementsByTagName(nodeName).item(0);
					if(theElem == null){
						xmlDataMap.put("error","[" + nodeName + "]节点未找到");
						return xmlDataMap;
					}
					//如果找到了，就把这个值赋值给
					try {
						xmlDataMap.put(nodeName, theElem.getTextContent());
					} catch (Exception e) {
						xmlDataMap.put(nodeName, "");
					}
				}
			}else if("P036".equals(transtype)){//查单交易，等等,此处省略，代码逻辑几乎一样
				for(String nodeName:p036bodyList){
					theElem=(Element) body.getElementsByTagName(nodeName).item(0);
					if(theElem == null){
						xmlDataMap.put("error","[" + nodeName + "]节点未找到");
						return xmlDataMap;
					}
					//如果找到了，就把这个值赋值给
					try {
						xmlDataMap.put(nodeName, theElem.getTextContent());
					} catch (Exception e) {
						xmlDataMap.put(nodeName, "");
					}
				}
			}else{
				xmlDataMap.put("error", "transtype错误");
			}
		} catch (Exception e) {
			 e.printStackTrace();
        	 xmlDataMap.put("error", "解析xml出现异常！");
		}
		 return xmlDataMap;
	}
	
	/**
	 * 判断mac签名是否正确
	 * @author 作者：李启明
	 * @Title: validateMAC 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param mac
	 * @param xml
	 * @return 
	 * @return(返回类型) boolean     
	 * @throws
	 */
	private static boolean validateMAC(String mac,String xml){
		//去掉xml的声明部分
		String str=xml.substring(xml.indexOf("?>")+2);
		int begin = str.indexOf("<mac>");
		int end = str.indexOf("</mac>");
		//截取掉xml中的mac节点
		str = str.substring(0, begin) + str.substring(end+6);
		//去掉首尾的空格
		String replaceString = str.trim();
		System.out.println("请求时mac节点的值是："+mac);
		String mCheckStr=Md5.MD5(replaceString + CHECK_STR);
		System.out.println("计算的mac签名的值是："+mCheckStr);
		if(mCheckStr.equals(mac) || mCheckStr.equals(mac.toUpperCase())) {
			return true;
		}else{
			return false;
		}
	}
	
	
	/***
	 * 根据节点名称生成xml节点
	 * @author 作者：李启明
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param root 需要创建的节点的父节点
	 * @param nodeNames 该父节点下所有的子节点名称
	 * @param map 子节点的值
	 * @param xmldoc Document对象
	 * @return
	 * @throws Exception 
	 * @return(返回类型) Element     
	 * @throws
	 */
	private static Element generateData(Document xmldoc,Element element, String[] nodeNames,Map<String,Object> map) throws Exception{
		Element theElem = null;
		String tmp = "";
		try{
			for(String nodeName : nodeNames){
				theElem = xmldoc.createElement(nodeName);
				tmp=(String) map.get(nodeName);
				if(tmp==null){
					tmp = "";
				}
				//去除XML空格
				theElem.setTextContent(tmp.trim());
				element.appendChild(theElem);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return element;
	}
	/**
	 * 将XML对象转换成字符串
	 * @param root
	 * @return
	 * @throws RouterException 
	 */
	public static String xml2String(Element node) throws Exception {
		 	TransformerFactory transFactory=TransformerFactory.newInstance();
		    StringWriter sw = new StringWriter();
		    try {
		    	
		        Transformer transformer = transFactory.newTransformer();
		        transformer.setOutputProperty("encoding", "utf-8");
		        transformer.setOutputProperty("indent", "yes");
		        DOMSource source=new DOMSource();
		        source.setNode(node);
		        transformer.transform(source, new StreamResult(sw));
		    } catch (TransformerConfigurationException e) {
		      e.printStackTrace();
		    }   
		    return sw.toString();
	 }
	
	
	/**
	 * 组装响应客户端的请求
	 * @author 作者：李启明
	 * @Title: assembleXmlDataNoBody 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param map	响应的数据值
	 * @param nodes 响应的报文体的节点名称数组
	 * @return 
	 * @return(返回类型) String     
	 * @throws
	 */
	public static String assembleXmlDataNoBody(Map<String, Object> map,String[] nodes) {
		String resultStr="";
		//获取当前时间，作为response_time的值
		java.util.Date date = new java.sql.Date(new java.util.Date().getTime());
		String response_time= sdfLongTime.format(date);
		map.put("response_time", response_time);
		
		DocumentBuilderFactory factory = null;
		Element root = null, head = null, body = null;
		Document xmldoc=null;
		
		try {
			factory = DocumentBuilderFactory.newInstance();
			
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = factory.newDocumentBuilder();
			//创建Document对象
			xmldoc = db.newDocument();
			//创建根目录
			root = xmldoc.createElement(sRootName);
	        //创建二级节点transaction_header标签
			head = xmldoc.createElement(sHeadName);
			head = generateData(xmldoc,head, responseHeadNodes ,map);
			root.appendChild(head);
			//报文体
			body = xmldoc.createElement(sBodyName);
			if(nodes.length>0){
				//如果响应的报文体不为空的时候
				body = generateData(xmldoc,body,nodes ,map);
			}
			root.appendChild(body);
			//将生成的xml格式的数据转换成字符串
			resultStr = xml2String(root);
			//去掉<?xml version="1.0" encoding="utf-8"?>
			resultStr = resultStr.substring(resultStr.indexOf("?>")+2);
			//去掉行和空格
			resultStr = resultStr.replaceAll("\r\n", "").replace("\r", "").replace("\n","");
			System.out.println("加密的串是："+resultStr + CHECK_STR);
			//计算加密,生成一个解密后的字符串
			String mac=Md5.MD5(resultStr + CHECK_STR);
			//head节点上创建一个子节点：mac节点
			Element macElem = xmldoc.createElement("mac");
			//为mac节点赋值
			macElem.setTextContent(mac.trim());
			//将mac节点追加到head节点中
			head.appendChild(macElem);
			//然后将这个有了mac节点的xml文件，转换成字符串
			String xml = xml2String(root);
			//去掉这个字符串的空格
			xml = xml.replaceAll("\r\n", "").replace("\r", "").replace("\n","");
			return xml;
		} catch (Exception e) {
			
		}
		return resultStr;
	}
	
	
	public static void main(String[] args) {
		String p033="<?xml version=\"1.0\" encoding=\"utf-8\"?><transaction><transaction_header><version>V2.1.2</version><transtype>P036</transtype><employno>01</employno><termid>88880001</termid><request_time>20190104125515</request_time><mac>37F5D4D0519ABAE3B949137BFADD5726</mac></transaction_header><transaction_body><orderno>20190104104403</orderno><cod>0.01</cod><cardid/><banktrace>05829354300N</banktrace><postrace>058293</postrace><cxbanktrace>05829354300N</cxbanktrace></transaction_body></transaction>";
		//String p036="<?xml version=\"1.0\" encoding=\"utf-8\"?><transaction><transaction_header><version>V2.1.2</version><transtype>P033</transtype><employno>01</employno><termid>00000001</termid><request_time>20180713165549</request_time><shopid>898319873940027</shopid><wlid>d2819d37bce94c84a5db6a4bbd7edcb4</wlid><mac>3C7D2DB92ADE05B276765DC2F3D0EB2D</mac></transaction_header><transaction_body><orderno>1000000000166591</orderno><cod>0.01</cod><payway>97</payway><banktrace>03515065461N</banktrace><postrace>035150</postrace><tracetime>20180713165406</tracetime><cardid/><signflag>0</signflag><dssn/><dsname/><memo/></transaction_body></transaction>";
		Map<String,Object> map=XmlUtils.readXml(p033);
		System.out.println("queryId:"+map.get("queryId"));
		String reponseStr="";
		String response_code="00";//应答码，默认是成功的
		if(map.get("error")==null){
			System.out.println("*******************************收到大华通知的参数开始*******************************");
			for(String key:map.keySet()){
				System.out.println(key+":"+map.get(key));
			}
			System.out.println("*******************************收到大华通知的参数结束*******************************");
			//解析xml未出现问题，根据交易类型和参数，进行相应的业务处理
			System.out.println("*******************************响应大华通知的参数开始*******************************");
			map.put("response_code", response_code);
			map.put("response_msg", "交易成功！");
			String transtype=(String) map.get("transtype");
			if("P033".equals(transtype)){
				//缴费确认，根据传递过来的参数查询数据库，操作数据，商户自行处理
				
				//组装响应的报文
				reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseP033BodyNodes);
			}else if("P036".equals(transtype)){//撤销交易通知
				//撤销通知，根据传递过来的参数查询数据库，操作数据，商户自行处理
				reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseP036BodyNodes);
			}
			
		}else{
			map.put("response_code", "01");
			map.put("response_msg", map.get("error"));
			reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseErrorBodyNodes);
		}
		System.out.println("回写给大华的信息是："+reponseStr);
	}
}
