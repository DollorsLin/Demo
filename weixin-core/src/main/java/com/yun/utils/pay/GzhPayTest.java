package com.yun.utils.pay;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公众号支付，参考AppPay
 */
@Service
public class GzhPayTest {
    public static void main(String[] args) {
//        createOrder();//不分账支付请求，获取app支付要素

        String jsapi_ticket = "kgt8ON7yVITDhtdwci0qef4Fd1u2d2iWwTE-nt2tSR7Me3FmWAhtxJa3QQCiMdLper4cUrqpDxkDJ6ljLgc7PA";
        int timestamp = 1460637652;
        String nonceStr = "C1WNnEpCwq4wa158";
        String url = "http://qq.test.com/test/index.html";


        //字典序排序
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("jsapi_ticket", jsapi_ticket);
        map.put("timestamp", timestamp + "");
        map.put("nonceStr", nonceStr);
        map.put("url", url);

        Collection<String> keyset = map.keySet();

        List list = new ArrayList<String>(keyset);

        Collections.sort(list);
        //这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + "=" + map.get(list.get(i)));

        }

        //微信官网写的按照字典序排序后的字符串
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "×tamp=" + timestamp + "&url=" + url;


        //System.out.println(DigestUtils.shaHex("jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW×tamp=1414587457&url=http://mp.weixin.qq.com?params=value"));
        //微信返回的加密串
        String signature = DigestUtils.shaHex(sign);//sha1加密
        System.out.println(signature);


    }

    private static void createOrder() {

        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.put("mer_id", Const.static_mer_id);//
        treeMap.put("order_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        treeMap.put("cod", "0.01");
        treeMap.put("qrtype", "gzh");
        //treeMap.put("transTid", "12345678");//动态终端号
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            sb.append(treeMap.get(key));
        }
        System.out.println("加密的串是：" + sb.toString());
        String macStr = "";
        try {
            macStr = SM3Util.sm3(sb.toString() + Const.checkStr);
            treeMap.put("mac", macStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MAC的值是：" + macStr);
        StringBuffer param = new StringBuffer();
        param.append(Const.pay_url);
        for (String key : treeMap.keySet()) {
            param.append(key + "=" + treeMap.get(key) + "&");
        }
        System.out.println("公众号支付请求的地址如下，将url复制到微信聊天记录，点击点击吊起支付");
        System.out.println(param.toString());
    }


}
