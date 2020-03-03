package com.yun.utils.pay;


public class Const {
	//测试环境
//	public static final String pay_url="https://dhjt-test.chinaums.com/queryService/UmsWebPayPlugins?";
//	public static final String query_url="https://dhjt-test.chinaums.com/queryService/UmsWebPayQuery?";
//	public static final String refund_url="https://dhjt-test.chinaums.com/queryService/UmsWebPayRefund?";

	//生成环境
	public static final String pay_url="https://dhjt.chinaums.com/queryService/UmsWebPayPlugins?";
	public static final String query_url="https://dhjt.chinaums.com/queryService/UmsWebPayQuery?";
	public static final String refund_url="https://dhjt.chinaums.com/queryService/UmsWebPayRefund?";

	//银联测试秘钥
//	public static final String checkStr="1111111111111111111111111111111111111111111111111111111111111111";
//	public static final String checkStr="11111111111111111111111111111111";

	//银联正式秘钥
	public static final String checkStr="r84lqr8RQFSQ2zP9QUExUOCB1f6ZTMJ88mi98XzxBvrfHgEVOILvJLihQfhJEnGN";

	public static final String static_mer_id="7bb2ffd047fb40ccabd93667ad622d34";////联系大华捷通项目组获取
//	public static final String qrcode_url_test="http://websitetest.shenghuayule.net/pay/#/pay?id=";//测试
	public static final String qrcode_url_test="https://web-test.quteemall.com/pay/#/pay?id=";//测试
	public static final String qrcode_url="https://web.quteemall.com/pay/#/pay?id=";//正式
	public static final String PAY_FINISHED_URL="https://web.quteemall.com/pay/callback.htm";
	public static final String ACCOUNT_PAYEE="智胜影游";
	public static final String REMARK="";
	public static final Long EXPIRE_TIME = 1000*117l;
	public static final String TEXT="http://2u5f778182.qicp.vip:16461/bills/abc?";
	public static final String TEXT_ONE="http://192.168.1.52:8074/bills/abc?";
	public static final String TEXT_LUX="http://testapi.kj-tek.com/web/order/callBack_MM";//测试
	public static final String CALLBACK_LUX="https://api.kj-tek.com/web/order/callBack_MM";//正式


	//测试和生产一样
	public static final String TEST_TOKEN_LUX="MMJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyYW5kb20iOiJwTVBwTTYzbTkxYmZpS1ZXZ1lHbFlFVktzbnlRSCIsImlkIjoiNTUwN2M3ODUtYWVjMC00NzI4LWJlNDUtYjUxYWVhODVhOGYwIiwiaXNzIjoia2pzLmNvbSIsImV4cCI6MTU2MTk3Mzg0MX0.yX0UAWcpUT5FzYO6Ld2JfgAIsYbB9POSRp7HUFjspxQ";//测试
	public static final String TOKEN_LUX="";//正式
}
