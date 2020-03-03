package com.yun.utils.pay;

import java.security.MessageDigest;

public class Md5 {
	public  static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void main(String[] args) {
		String xml="<transaction><transaction_header><version>V2.1.2</version><transtype>P003</transtype><termid>01393263</termid><employno>01</employno><response_time>20190509114507</response_time><response_code>35</response_code><response_msg>已通知</response_msg></transaction_header><transaction_body/></transaction>1Qlm4BJxM6kC0f6SmNFPgzg1fWUJ5d1z";
		String fuck = "0135已通知2019050911450701393263P003V2.1.21Qlm4BJxM6kC0f6SmNFPgzg1fWUJ5d1z";
		String  str = MD5(xml);
		String ff = MD5(fuck);
        System.out.println("ff:"+ff);
		System.out.println(str);//加密后
		
	}
	
}

