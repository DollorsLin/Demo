package com.yun.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author 李启明
 */
public class PayJsonUtils {
	//测试
//	public final static String CHECK_STR="1111111111111111111111111111111111111111111111111111111111111111";//双方之间的秘钥
	//正式
	public final static String CHECK_STR="r84lqr8RQFSQ2zP9QUExUOCB1f6ZTMJ88mi98XzxBvrfHgEVOILvJLihQfhJEnGN";//双方之间的秘钥
	//下面各个节点的key都是可以更换的，具体在测试环境的时候需要跟对接人员沟通好
	public final static String sRootName="transaction";
	public final static String sHeadName="header";
	public final static String sBodyName="body";
	public final static String[] requestHeadlist={"version","transtype","employno","termid","shopid","request_time"};
	public final static String[] requestP033bodyList={"queryId","orderno","cod","payway","banktrace","postrace","tracetime","cardid","signflag","signer","dssn","dsname","cardtype","ums_order_no","target_order_no","buyerId"};
	public final static String[] requestP036bodyList={"orderno","cod","cardid","banktrace","postrace","cxbanktrace"};
	public final static String[] responseHeadNodes={"version","transtype","termid","employno","response_time","response_code","response_msg"};
	public final static String[] responseP033BodyNodes={};
	public final static String[] responseP036BodyNodes={};
	public final static String[] responseErrorBodyNodes={};


	//对接勒克斯的接口
	public final static String[] requestHeads={"version","transtype","employno","termid","shopid","request_time"};
	public final static String[] requestBodys={"queryId","orderno","cod","payway","banktrace","postrace","tracetime","cardid","signflag","signer","dssn","dsname","cardtype","ums_order_no","target_order_no","result"};



	private static java.text.SimpleDateFormat sdfLongTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
	
	//读取推送的参数值，放到json中，后面的所有操作都从该json中取值
	public static JSONObject getRequestParamStream(String context) {
		JSONObject ret=new JSONObject();
		ret.put("code", "00");
		try {
			JSONObject request=JSONObject.fromObject(context);
			//提取header节点参数值
			JSONObject header=request.getJSONObject("header");
			for(String key:requestHeadlist){
//				if (key.equals("shopid")) {
//					continue;
//				}
				if(header.containsKey(key)){
					ret.put(key, header.get(key));
				}else{
					ret.put("code", "01");
					ret.put("msg", "通知报文header中缺少参数["+key+"]");//此处不能return，因为请求头里面还需要一些参数值再响应的时候需要用
				}
			}
			//提取header节点参数值
			JSONObject body=request.getJSONObject("body");
			//根据不同的交易类型，获取参数
			if("P033".equals(ret.getString("transtype"))){//支付通知
				for(String key:requestP033bodyList){
					if (key.equals("signer")) {
						continue;
					}
					if (key.equals("cardtype")) {
						continue;
					}
					if(body.containsKey(key)){
						ret.put(key, body.get(key));
					}else{
						ret.put("code", "01");
						ret.put("msg", "通知报文body中缺少参数["+key+"]");
						return ret;
					}
				}
			}else if("P036".equals(ret.getString("transtype"))){//退款通知
				for(String key:requestP036bodyList){
					if(body.containsKey(key)){
						ret.put(key, body.get(key));
					}else{
						ret.put("code", "01");
						ret.put("msg", "通知报文body中缺少参数["+key+"]");
						return ret;
					}
				}
			}else{
				ret.put("code", "01");
				ret.put("msg", "通知报文transtype参数值错误");
				return ret;
			}
		} catch (Exception e) {
			ret.put("code", "01");
			ret.put("msg", "解析报文异常，请检查context参数是否是json格式");
		}
		return ret;
	}



	public static JSONObject getResponseParam(JSONObject requestData,String[] responsNodes) {
		//获取当前时间，作为response_time的值
		Date date = new java.sql.Date(new Date().getTime());
		String response_time= sdfLongTime.format(date);
		requestData.put("response_time", response_time);
		JSONObject ret=new JSONObject();
		//组织响应的header节点内容
		JSONObject header=new JSONObject();
		for(String key:responseHeadNodes){
			header.put(key, requestData.get(key)==null?"":requestData.get(key));
		}

		//组织响应的body节点内容,通知类的body都是空的，有些商户可能会有查询交易，那么他们的body里面就是有参数的
		JSONObject body=new JSONObject();
		for(String key:responsNodes){
			body.put(key, requestData.get(key)==null?"":requestData.get(key));
		}
		ret.put("header", header);
		ret.put("body", body);
		return ret;
	}

	public static JSONObject settRequestParam(JSONObject requestData) {
		//获取当前时间，作为response_time的值
		Date date = new java.sql.Date(new Date().getTime());
		String response_time= sdfLongTime.format(date);
		requestData.put("response_time", response_time);
		JSONObject ret=new JSONObject();
		//组织响应的header节点内容
		JSONObject header=new JSONObject();
		for(String key:requestHeads){
			header.put(key, requestData.get(key)==null?"":requestData.get(key));
		}
		//组织响应的body节点内容
		JSONObject body=new JSONObject();
		for(String key:requestBodys){
			body.put(key, requestData.get(key)==null?"":requestData.get(key));
		}
		ret.put("header", header);
		ret.put("body", body);
		return ret;
	}


	public static String genOrderNo() {
		String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		String rand = RandomStringUtils.randomNumeric(7);
		return date + rand;
	}
	
}
