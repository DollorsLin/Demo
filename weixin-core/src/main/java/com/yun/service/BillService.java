package com.yun.service;


import com.yun.pojo.*;
import com.yun.utils.JsonResult;
import com.yun.utils.TimeDateUtils;
import com.yun.utils.pay.*;
import com.yun.vo.Refund;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付
 */
@Service
public class BillService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private CommandService commandService;



    @Value("${checkStr}")
    private String checkStr;

    @Value("${pay_url}")
    private String payUrl;

    @Value("${refund_url}")
    private String refundUrl;

    @Value("${sign_pay_finished_url}")
    private String signPayFinishedUrl;

    @Value("${mer_id}")
    private String merId;

    private String PAYEE = "幻想舞伴";


    /**
     * 创建订单
     */
    public JsonResult createOrder(String serialNumber) {
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        Integer productId = bySerialNumber.getProductId();
        Order lastOrder = orderService.signLastOrder(productId);
        ProductOrder detail = productOrderService.getFirstProductOrder(productId);
        Product product = productService.get(productId);
        Integer customerId = product.getCustomerId();
        Customer customer = customerService.get(customerId);
        BigDecimal price = product.getPrice();
        if (price == null || price.compareTo(new BigDecimal(0))==0) {
            price = new BigDecimal(5);
        }
        Date expiredTime = product.getExpiredTime();
        Date now = new Date();
        //判断最后一把订单的状态
        //如果已完成，则可以创建新订单
//        System.out.println("求签过期时间："+expiredTime.getTime());
//        System.out.println("now:::"+now.getTime());
        if (expiredTime != null) {
            if (lastOrder != null) {
                if (lastOrder.getOrderStatus() == 2) {
                    //已支付，未消费
                    if (detail.getStatus() == 1) {
                        return JsonResult.error("有其他人正在支付，请稍后再试。");
                    }
                }
                //正在支付中
                if (expiredTime.getTime() > now.getTime()) {
                    return JsonResult.error("有其他人正在支付，请稍后再试。");
                }
            }
        }

        Map<String, Object> m = new HashMap<>();
        m.put("type", "1004");
        commandService.addCommand(serialNumber, productId, m);


        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("mer_id", merId);//
        treeMap.put("qrtype", "gzh");
        treeMap.put("cod", price);
        //支付完成后回调的地址
        treeMap.put("MerBackUrl", signPayFinishedUrl);
        String orderNo = JSONUtils.genOrderNo();
        Order order = new Order();
        order.setGoodsId(0);
        order.setUserId(0);
        order.setSerialNumber(serialNumber);
        order.setCustomerId(customerId);
        order.setAmountInFact(price);
        order.setAmount(price);
        order.setOrderTime(new Date());
        order.setOrderStatus(1);
        order.setAmountRefund(new BigDecimal(0));
        order.setProductId(productId);
        order.setOtherOrderNo(orderNo);
        Order newOrder = orderService.save(order);

        //设置30秒扫码过期时间
        product.setExpiredTime(new Date(now.getTime() + 30000l));
        productService.orderingUpdate(product);

        treeMap.put("order_no", order.getOtherOrderNo());
        treeMap.put("expireTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime() + 30000l));
        String payUrl = getPayUrl(treeMap);
        Map<String, Object> map = new HashMap<>();
        map.put("url", payUrl);
        map.put("orderNo", orderNo);
        map.put("orderTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
        //收款账户名称
        map.put("accountPayee", PAYEE);
        map.put("price", price);
        return JsonResult.ok(map);
    }


    /**
     * 生成相应url
     *
     * @param treeMap
     * @return
     */
    public String getPayUrl(TreeMap<String, Object> treeMap) {
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            sb.append(treeMap.get(key));
        }
        System.out.println("支付加密的串是：" + sb.toString());
        String macStr = "";
        try {
            macStr = SM3Util.sm3(sb.toString() + checkStr);
            treeMap.put("mac", macStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MAC的值是：" + macStr);
        StringBuffer param = new StringBuffer();
        param.append(payUrl);
        for (String key : treeMap.keySet()) {
            param.append(key + "=" + treeMap.get(key) + "&");
        }
        System.out.println("公众号支付请求的地址如下，将url复制到微信聊天记录，点击点击吊起支付");
        System.out.println(param.toString());
        return param.toString();
    }


    public JsonResult notifyByUrl(TreeMap<String, Object> params) {
        System.out.println("求签机同步回调成功 ");
        Map<String, Object> map = new HashMap<>();
        String otherNo = params.get("orderId").toString();
        String payStatus = params.get("status").toString();
        String umsOrderNo = params.get("refId").toString();
        String targetOrderNo = params.get("mer_id").toString();
        String queryNumber = params.get("queryId").toString();
        if (!checkSign(params)) {
            System.out.println("求签机同步通知的报文:验签失败");
            return JsonResult.error(500, "验签失败");
        }
        Order order = orderService.getByOtherNo(otherNo);
        Integer status = order.getOrderStatus();
        Integer customerId = order.getCustomerId();
        Customer customer = customerService.get(customerId);
        map.put("payee", PAYEE);
        if (status == 2) {
            //代表异步已更新数据，无需再更新
            System.out.println("幻想舞伴异步已更新数据，无需再更新");
            return JsonResult.ok(map);
        }
        if ("TRADE_SUCCESS".equals(payStatus)) {
            order.setQueryNumber(queryNumber);
            order.setPayTime(new Date());
            order.setOrderStatus(2);
            order.setTargetOrderNo(targetOrderNo);
            order.setUmsOrderNo(umsOrderNo);
            orderService.save(order);
            System.out.println("幻想舞伴订单及明细更新完毕！");
        }
        return JsonResult.ok(map);
    }

    public JsonResult refund(Refund refund) {
        String orderNo = refund.getOrderNo();
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();

//        Business business = businessService.getById(1);
//        String merId = business.getMerId();
//        treeMap.put("mer_id", merId);
        String qrtype = "gzh";
        treeMap.put("qrtype", qrtype);
        String refundNo = JSONUtils.genOrderNo();
        treeMap.put("refund_no", refundNo);
        Integer refundId = refund.getRefundId();
        String refundName = refund.getRefundName();
        BigDecimal refundAmt = new BigDecimal(0);
        treeMap.put("refund_amt", refundAmt + "");
        System.out.println("=================================================================getByOtherNo:" + orderNo);
//        Order order = orderService.getByOtherNo(orderNo);
//        Integer productId = order.getProductId();
//        Integer customerId = order.getCustomerId();
//        String otherOrderNo = order.getOtherOrderNo();
//        String queryId = order.getQueryNumber();
//        treeMap.put("queryId", queryId);
//        order.setRefundNo(refundNo);
//        //退款金额
//        order.setAmountRefund(refundAmt);
//        //退款单号
//        order.setRefundNo(refundNo);
//        System.out.println("退款金额：" + refundAmt);
//        order.setAmountRefund(refundAmt);
//        order.setRefundTime(new Date());
//        order.setIsFinished(1);


//        Product product = productService.get(productId);
//        Integer paymentType = product.getPaymentType();
//        if (paymentType == 0) {
//            //不分账
//            String ret = sendRequest(treeMap, refundUrl);
//            System.out.println("收到的ret:" + ret);
//            System.out.println("发给大华的退款报文：" + treeMap.toString());
//            JSONObject jsonObject = JSONObject.fromObject(ret);
//            if (!checkSign(ret)) {
//                System.out.println("不分账交易退款接口同步返回:验签失败");
//            }
//            if (jsonObject.getString("refundStatus").equals("SUCCESS")) {
//                System.out.println("refund success");
//                //更改明细
//                updateGoodsDetail(list, refundId, refundName);
//                orderService.save(order);
//            }
//            System.out.println("不分账交易退款接口返回：" + ret);
//        }
//        //分账
//        if (paymentType == 1) {
//            subRefundAccount(treeMap, order, refundAmt);
//            String ret = sendRequest(treeMap, refundUrl);
//            JSONObject jsonObject = JSONObject.fromObject(ret);
//            if (!checkSign(ret)) {
//                System.out.println("分账交易退款接口返回:验签失败");
//            }
//            System.out.println("收到的ret:" + ret);
//            if (jsonObject.getString("refundStatus").equals("SUCCESS")) {
//                System.out.println("refund success");
//                //更改明细
//                updateGoodsDetail(list, refundId, refundName);
//                orderService.save(order);
//            }
//            System.out.println("分账交易退款接口返回：" + ret);
//        }
        return JsonResult.ok("退款成功");
    }


    public String sendRequest(TreeMap<String, Object> treeMap, String url) {
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            sb.append(treeMap.get(key));
        }
        System.out.println("发送给大华加密的串是：" + sb.toString());
        String macStr = "";
        try {
            macStr = SM3Util.sm3(sb.toString() + checkStr);
            treeMap.put("mac", macStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送给大华MAC的值是：" + macStr);
        try {
            String ret = HttpPost.http(url, "", 80, treeMap, "utf-8");
            System.out.println("ret :" + ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送错误！";
    }


//    private Boolean checkSign(String ret) {
//        JSONObject jsonObject = JSONObject.fromObject(ret);
//        Object mac = jsonObject.remove("mac");
//        try {
//            String local = SM3Util.sm3(jsonObject.toString() + checkStr);
//
//            if (mac.toString().equals(local)) {
//                System.out.println("验签成功");
//                return true;
//            } else {
//                System.out.println("验签失败");
//                return false;
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


    /**
     * 验签
     *
     * @param treeMap
     * @return
     */
    public Boolean checkSign(TreeMap<String, Object> treeMap) {
        String mac = treeMap.remove("mac").toString();

        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            sb.append(treeMap.get(key));
        }
        System.out.println("con:" + sb.toString() + checkStr);
        String localMac = null;
        try {
            localMac = SM3Util.sm3(sb.toString() + checkStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("localMac:" + localMac);
        if (localMac.equals(mac)) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
        TreeMap<String, Object> treeMap = new TreeMap<>();
//        treeMap.put("mac","94098e0c7220058a3d4b12ecb7ab36d8cfc7ab2717bdc7d559a4d15e44d35cb0");

        treeMap.put("code", "00");
        treeMap.put("orderId", "201910281112003306985548");
        treeMap.put("memo", "");
        treeMap.put("queryId", "20191028111202531916");
        treeMap.put("cod", "0.01");
        treeMap.put("mer_id", "7bb2ffd047fb40ccabd93667ad622d34");
        treeMap.put("msg", "TRADE_SUCCESS");
        treeMap.put("refId", "11076473576N");
        treeMap.put("status", "TRADE_SUCCESS");
        treeMap.put("payway", "97");
        treeMap.put("tracetime", "2019-10-28 11:12:11");
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            sb.append(treeMap.get(key));
        }

        String cs = "1111111111111111111111111111111111111111111111111111111111111111";
//        String cs ="r84lqr8RQFSQ2zP9QUExUOCB1f6ZTMJ88mi98XzxBvrfHgEVOILvJLihQfhJEnGN";
        System.out.println("sb:" + sb);
        String sm3 = SM3Util.sm3(sb.toString() + cs);
        System.out.println("sm3:" + sm3);
        System.out.println("abc:" + SM3Util.sm3("0.01007bb2ffd047fb40ccabd93667ad622d34TRADE_SUCCESS201910281421344685478298972019102814213653195311080718362NTRADE_SUCCESS2019-10-28 14:21:431111111111111111111111111111111111111111111111111111111111111111"));
    }


}

