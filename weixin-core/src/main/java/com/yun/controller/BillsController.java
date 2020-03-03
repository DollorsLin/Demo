package com.yun.controller;


import com.yun.annotation.OperatorLogAnnotation;
import com.yun.pojo.Order;
import com.yun.pojo.ProductOrder;
import com.yun.service.*;
import com.yun.utils.JsonResult;
import com.yun.utils.PayJsonUtils;
import com.yun.utils.pay.Md5;
import com.yun.vo.Refund;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/bills")
public class BillsController {


    @Autowired
    private BillService billService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Value("${checkStr}")
    private String checkStr;


    @ResponseBody
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("幻想舞伴支付异步回调");
        response.reset();
        request.setCharacterEncoding("UTF-8");
        String context = request.getParameter("context");
        String mac = request.getParameter("mac");
        System.out.println("大华异步通知的报文context是：" + context);
        System.out.println("大华异步通知的签名mac是：" + mac);
        if (context == null || "".equals(context)) {
            response.getWriter().write("An empty message was received.");
            System.out.println("收到了空的报文");
            return;//有可能存在通知报文没有参数的情况，例如验证商户系统是否正常
        }
        //验证签名，对收到的原始报文和秘钥进行md5加密
//        System.out.println("checkStr:" + checkStr);
        String localMac = Md5.MD5(context + checkStr);
        //读取通知的报文内容
        JSONObject requestData = PayJsonUtils.getRequestParamStream(context);

        JSONObject ret = null;
        String orderNo = requestData.getString("orderno");
        String cod = requestData.getString("cod");
        //获取相应订单
        Order order = orderService.getByOtherNo(orderNo);
        Integer orderId = order.getId();
        Integer productId = order.getProductId();
        String serialNumber = order.getSerialNumber();
        String otherOrderNo = order.getOtherOrderNo();
        if (localMac.equals(mac)) {
            if ("00".equals(requestData.getString("code"))) {
                requestData.put("response_code", "00");
                requestData.put("response_msg", "交易成功");
                String transtype = requestData.getString("transtype");
                if ("P033".equals(transtype)) {//支付通知
                    try {
                        System.out.println("支付成功异步通知：");
                        //支付通知，商户自行处理,所需要的参数在map中取，参数key在repeustP033BodyNodes里面
                        //组装响应的报文
                        String umsOrderNo = requestData.getString("ums_order_no");
                        String targetOrderNo = requestData.getString("target_order_no");
                        //更改订单状态
                        String queryNumber = requestData.getString("queryId");
                        String payway = requestData.getString("payway");
                        String buyerId = requestData.getString("buyerId");
                        if (order.getOrderStatus() != 2) {
                            order.setOrderStatus(2);
                            order.setQueryNumber(queryNumber);
                            order.setPayTime(new Date());
                            order.setPayType(Integer.valueOf(payway));
                            order.setUmsOrderNo(umsOrderNo);
                            order.setTargetOrderNo(targetOrderNo);
                            order.setUserPayIdentifier(buyerId != null ? buyerId : "");
                            //TODO 更新机台订单关联明细
                            orderService.save(order);
                            ProductOrder productOrder = new ProductOrder();
                            productOrder.setStatus(1);
                            productOrder.setAddTime(new Date());
                            productOrder.setProductId(productId);
                            productOrder.setRelateId(orderId);
                            productOrder.setUserId(0);
                            productOrderService.save(productOrder);
                            //告知机台用户支付成功
                            Map<String, Object> m = new HashMap<>();
                            m.put("type", "1005");
                            m.put("orderId", orderId);
                            m.put("price", order.getAmountInFact());
                            commandService.addCommand(serialNumber,productId, m);
                        }

                        ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseP033BodyNodes);
                    } catch (Exception e) {
                        //如果操作数据库异常
                        requestData.put("response_code", "01");
                        requestData.put("response_msg", "系统内部异常:" + e.getMessage());
                        ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseErrorBodyNodes);
                    }
                } else if ("P036".equals(transtype)) {//退款通知
                    try {
                        //退款通知，商户自行处理,所需要的参数在map中取，参数key在repeustP036BodyNodes里面
                        //此处更新数据库的动作省略....
                        //组装响应的报文
                        //获取相应订单
                        System.out.println("退款异步通知：");
                        String refundAmt = requestData.getString("cod");

                        //更改订单状态
                        order.setRefundTime(new Date());
                        order.setAmountRefund(new BigDecimal(refundAmt));
                        System.out.println("refund_amt:" + refundAmt);
                        orderService.save(order);

                        ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseP036BodyNodes);
                    } catch (Exception e) {
                        //如果操作数据库异常
                        requestData.put("response_code", "01");
                        requestData.put("response_msg", "系统内部异常:" + e.getMessage());
                        ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseErrorBodyNodes);
                    }
                } else {
                    requestData.put("response_code", "01");
                    requestData.put("response_msg", "transtype错误");
                    ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseErrorBodyNodes);
                }
            } else {
                requestData.put("response_code", "01");
                requestData.put("response_msg", requestData.get("msg"));
                ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseErrorBodyNodes);
            }
        } else {
            requestData.put("response_code", "01");
            requestData.put("response_msg", "MAC签名不一致");
            ret = PayJsonUtils.getResponseParam(requestData, PayJsonUtils.responseErrorBodyNodes);
        }
        System.out.println("ret"+ ret.toString());
        System.out.println("待加密串：" + ret.toString() + checkStr);
        String responseMac = Md5.MD5(ret.toString() + checkStr);
        System.out.println("回写给大华的信息是：" + ret.toString() + "&mac=" + responseMac);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ret.toString() + "&mac=" + responseMac);
        response.getWriter().close();
    }

    @ResponseBody
    @RequestMapping(value = "/notifyByUrl", method = RequestMethod.POST)
    public JsonResult notifyByUrl(@RequestBody TreeMap<String, Object> params) {
        if (params == null) {
            JsonResult.error("参数为空！");
        }
        return  billService.notifyByUrl(params);
    }

    @ResponseBody
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public JsonResult refund(@RequestBody Refund refund) {
        return billService.refund(refund);
    }

    @ResponseBody
    @OperatorLogAnnotation("幻想舞伴创建订单")
    @RequestMapping(value = "/signOrderCreate", method = RequestMethod.POST)
    public JsonResult signOrderCreate(@RequestParam String serialNumber) {
        return billService.createOrder(serialNumber);
    }

}