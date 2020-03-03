package com.yun.controller;

import com.google.gson.Gson;
import com.yun.po.AccessToken;
import com.yun.po.WeixinUserInfo;
import com.yun.pojo.*;
import com.yun.service.*;
import com.yun.utils.*;
import com.yun.vo.QrcodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Api(description = "1.微信接口相关控制器")
@RestController
@CrossOrigin
@RequestMapping("/wxapi")
public class WxApiController {

    @Autowired
    private LdkjWxApiService ldkjWxApiService;

    @Autowired
    private ProductToUserService productToUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private LoginDetailService loginDetailService;

    @Autowired
    private UserToRoleService userToRoleService;

    @Autowired
    private PlayerAccountDetailService playerAccountDetailService;

//    /**
//     * @description 解密code得到openid
//     * @author: liyinlong
//     * @date 2019-05-05 13:58
//     * @param code
//     * @return
//     */
//    @ApiOperation("1.0：微信小程序解密code")
//    @GetMapping("/decryptCode")
//    public String decryptCode(@RequestParam String code){
//        log.info("1.0：微信小程序解密code===>code:" + code);
//        String result = MyHttpRequestUtil.sendGet(WxConstant.getDesryptCodeUri(code));
//        JSONObject ojb = JSONObject.parseObject(result);
//        System.out.println("返回结果:" + ojb);
//        Set<String> keys = ojb.keySet();
//        keys.forEach(item->{
//            System.out.println(item + ":" + ojb.get(item));
//        });
//        return ojb.getString("openid");
//    }

//    /**
//     * @description 小程序发送模板消息
//     * @author: liyinlong
//     * @date 2019-05-05 13:58
//     * @return
//     */
//    @ApiOperation("1.1：发送小程序模板消息")
//    @GetMapping("/sendMiniTemplateMessage")
//    public String sendMiniTemplateMessage(@RequestParam String formId,@RequestParam String openId){
//        log.info("1.1：发送小程序模板消息===>formId:" + formId + ",openId:" + openId);
//        ldkjWxApiService.sendMiniTemplate(formId,openId);
//        return "success";
//    }

    /**
     * @return
     * @description 微信公众号服务器配置校验token
     * @author: liyinlong
     * @date 2019-05-09 9:38
     */
    @ApiOperation("1.2：微信公众号服务器配置校验token")
//    @RequestMapping("/checkToken")
    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("1.2：微信公众号服务器配置校验token");
        //token验证代码段

        String tempSignature = request.getParameter("signature");
        if (null != tempSignature) {
            try {
                log.info("请求已到达，开始校验token");
                if (StringUtils.isNotBlank(request.getParameter("signature"))) {
                    String signature = request.getParameter("signature");
                    String timestamp = request.getParameter("timestamp");
                    String nonce = request.getParameter("nonce");
                    String echostr = request.getParameter("echostr");
                    log.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
                    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                        log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
                        response.getOutputStream().println(echostr);
                    }
                }
            } catch (IOException e) {
                log.error("校验出错");
                e.printStackTrace();
            }
        }

//        response.reset();
////        ldkjWxApiService.getCode(response);
//        String getCodeUrl = WeixinParam.WEIXIN_GET_CODE_URL.getValue()
//                .replace(WeixinParam.WEIXIN_APPID.getKey(), WeixinParam.WEIXIN_APPID.getValue())
//                // 这里替换微信获取code之后的回调路径地址
//                .replace(WeixinParam.WEIXIN_REDIRECT_URL.getKey(), URLEncoder.encode(WeixinParam.WEIXIN_REDIRECT_URL.getValue()))
//                .replace(WeixinParam.WEIXIN_SNSAPI_USERINFO.getKey(), WeixinParam.WEIXIN_SNSAPI_USERINFO.getValue());
//        JSONObject jsonObject = WeixinUtil.doGetStr(getCodeUrl);
//        System.out.println("js:"+jsonObject);


    }


    // 微信成功获取到code之后会执行此回调方法
    @RequestMapping("/callBack")
    public JsonResult login(HttpServletRequest request, String code, String state) throws ParseException, IOException {
        // 获取到登录人员信息
        WeixinUserInfo userInfo = ldkjWxApiService.getUserInfo(code, state);
        request.setAttribute("userInfo", userInfo);
        log.info("userInfo:" + userInfo);


        // 返回index页面
        return JsonResult.ok("123");
    }


    @RequestMapping("/registerUser")
    public JsonResult registerUser(@RequestParam(value = "code") String code, @RequestParam(value = "state") String state) throws ParseException, IOException {
        // 获取到登录人员信息
        WeixinUserInfo userInfo = ldkjWxApiService.getUserInfo(code, state);
        log.info("userInfo:" + userInfo);
        String openid = userInfo.getOpenid();
        User byOpenId = userService.getByOpenId(openid);
        if (byOpenId == null) {
            //注册新用户
            User user = new User();
            user.setCity(userInfo.getCity());
            user.setCountry(userInfo.getCountry());
            user.setProvince(userInfo.getProvince());
            user.setHeadImgUrl(userInfo.getHeadimgurl());
            user.setOpenId(openid);
            user.setWxNickName(userInfo.getNickname());
            user.setSex((userInfo.getSex()=="0")?0:1);
            user.setAddTime(new Date());
            user.setPlayNum(0);
            user.setLoginNum(0);
            user.setLevel(0);
            user.setCoin(0);
            user.setHeight(0);
            byOpenId = userService.save(user);
        }
        return JsonResult.ok(byOpenId);
    }


    /**
     * @param request
     * @param response
     * @return
     * @description 该接口url应与服务器配置中填写的URL保持一致，因校验token与处理用户校验事件都只
     * 能用一个URL，所以可以在token验证之后把1.2接口注释掉，把本接口名称改为1.2接口名称
     * @author: liyinlong
     * @date 2019-05-09 12:10
     */
    @ApiOperation("1.3：用户与公众号交互事件处理")
//    @RequestMapping("/getUserFocus")
    @RequestMapping("/checkToken")
    public String handlePubFocus(HttpServletRequest request, HttpServletResponse response) {
        log.info("1.3：用户与公众号交互事件处理");
        try {
            Map<String, String> requestMap = WxMessageUtil.parseXml(request);
//            String encrypt = requestMap.get("Encrypt");

            Set<String> keys = requestMap.keySet();


            String messageType = requestMap.get("MsgType");
            String eventType = requestMap.get("Event");
            String eventKey = requestMap.get("EventKey");
//            String[] s = eventType.split("_");
            System.out.println("eventKey:" + eventKey);
            Integer productId = null;
            Integer position = null;
            String[] s = eventKey.split("_");
            System.out.println("size:" + s.length);
            if (s.length == 4) {
                productId = Integer.valueOf(s[2]);
                position = Integer.valueOf(s[3]);
            }
            if (s.length == 3) {
                productId = Integer.valueOf(s[1]);
                position = Integer.valueOf(s[2]);
            }
//            String type = s[1];
//            Integer productId = Integer.valueOf(s[2]);
//            Integer position = Integer.valueOf(s[3]);

            String openid = requestMap.get("FromUserName");
            if (messageType.equals("event")) {
                //判断消息类型是否是事件消息类型
                log.info("公众号====>事件消息");
                log.info("openid:" + openid);
                log.info("Event:" + eventType);
                if (eventType.equals("subscribe")) {
                    log.info("公众号====>新用户关注");
                } else if (eventType.equals("unsubscribe")) {
                    log.info("公众号====>用户取消关注");
                } else if (eventType.equals("SCAN")) {
                    log.info("公众号===>用户扫码动态二维码");
                } else {
                    log.info("公众号===>其他");
                }
            }

            ProductToUser byProductIdAndPosition = productToUserService.getByProductIdAndPosition(productId, position);
            System.out.println("p_t_u:" + byProductIdAndPosition);
            //表示当前机台有用户在占用
            if (byProductIdAndPosition.getUserId() != null) {
                log.info("当前机台有用户在占用");
                return "success";
            }


            AccessToken accessToken = WeixinUtil.getAccessToken(WeixinParam.WEIXIN_APPID.getValue(), WeixinParam.WEIXIN_APPSECRET.getValue());
            String userInfoUrl = WeixinParam.ATTENDTION_USERINFO_URL.getValue()
                    .replace(Weixin.ACCESS_TOKEN, accessToken.getToken())
                    .replace(Weixin.OPENID, openid);

            JSONObject userInfo = null;

            userInfo = WeixinUtil.doGetStr(userInfoUrl);
            // 对那到的人员信息进行解析，得到一个类对象
            WeixinUserInfo user = JsonUtils.parse(userInfo.toString(), WeixinUserInfo.class);
            log.info("user:" + user.getNickname() + "," + user.getHeadimgurl() + "," + user.getUnionid());

            String headimgurl = user.getHeadimgurl();


            User u = userService.getByOpenId(openid);
            User nowUser = null;
            //新用户
            if (u == null) {
                //可以保存用户信息
                User user1 = new User();
                user1.setCity(user.getCity());
                user1.setCountry(user.getCountry());
                user1.setProvince(user.getProvince());
                user1.setHeadImgUrl(headimgurl);
                user1.setOpenId(user.getOpenid());
                user1.setWxNickName(user.getNickname());
                user1.setSex(Integer.valueOf(user.getSex()));
                user1.setAddTime(new Date());
                user1.setPlayNum(0);
                user1.setLoginNum(1);
                user1.setCoin(0);
                user1.setLevel(0);
                user1.setHeight(171);
                User save = userService.save(user1);
                nowUser = save;
            } else {
                if (headimgurl.equals(u.getHeadImgUrl())) {
                    u.setHeadImgUrl(headimgurl);
                    u.setLoginNum(u.getLoginNum() + 1);
                    userService.save(u);
                }
                nowUser = u;
            }

            ProductToUser byOpenId = productToUserService.getByOpenId(openid);

            if (byOpenId == null) {
                byProductIdAndPosition.setUserId(nowUser.getId());
                productToUserService.save(byProductIdAndPosition);
            } else if (byOpenId.getProductId().equals(byProductIdAndPosition.getProductId()) &&
                    byOpenId.getPosition().equals(byProductIdAndPosition.getPosition())) {
                //同一用户重复扫码
                log.info("登录：同一用户在同一机台同一点位重复扫码");
            } else {
                //同一用户在另外的地方扫码
                log.info("登录：同一用户扫另外一台机子的二维码，清理原来的机位的用户信息，登录现在的机位");
                //清理原来的机位的用户信息
                productToUserService.clearByProductId(productId);

                //登录现在的机位
                byProductIdAndPosition.setUserId(nowUser.getId());
                productToUserService.save(byProductIdAndPosition);

            }


            //下发用户信息给机台
            Integer userId = nowUser.getId();
            ProductToUser productToUser = productToUserService.getByOpenId(openid);
            Integer proId = productToUser.getProductId();
            List<SerialNumber> byProductId = serialNumberService.findByProductId(proId);
            SerialNumber sn = byProductId.get(0);
            String serialNumber = sn.getSerialNumber();
            Map<String, Object> map = new HashMap<>();
            map.put("type", "1003");
            map.put("wxNickName", nowUser.getWxNickName());
            map.put("userId", nowUser.getId());
            map.put("headImgUrl", nowUser.getHeadImgUrl());
            map.put("coin", nowUser.getCoin());
            map.put("height", nowUser.getHeight());
            Integer loginTimes = loginDetailService.loginTimes(userId);
            map.put("todayLogin", loginTimes);
            map.put("totalLogin", nowUser.getLoginNum());

            //该用户下的所有角色的信息
            List<UserToRole> listByUserId = userToRoleService.getListByUserId(userId);
            Date addTime = new Date();
            if (listByUserId.size() > 0) {
                if (loginTimes == 0) {
                    for (UserToRole userToRole : listByUserId) {
                        //每天第一次登陆所有角色加亲密度，数值待定
                        Integer exp = userToRole.getExp() + 50;
                        Integer level = userToRole.getLevel();
                        userToRole.setExp(exp);
                        PlayerAccountDetail playerAccountDetail = new PlayerAccountDetail();
                        playerAccountDetail.setUserId(userToRole.getUserId());
                        playerAccountDetail.setAddTime(addTime);
                        playerAccountDetail.setOrigin(4);
                        playerAccountDetail.setValue(50);
                        playerAccountDetail.setItem(2);
                        playerAccountDetailService.save(playerAccountDetail);

                        //升级公式计算
                        DanceUtils.uplevelNeedExp(userToRole, exp, level);
                        userToRoleService.save(userToRole);
                    }
                }
                if (loginTimes > 0) {
                    for (UserToRole userToRole : listByUserId) {
                        //第二次及之后登陆所有角色加亲密度
                        Integer exp = userToRole.getExp() + 20;
                        Integer level = userToRole.getLevel();
                        userToRole.setExp(exp);
                        PlayerAccountDetail playerAccountDetail = new PlayerAccountDetail();
                        playerAccountDetail.setUserId(userToRole.getUserId());
                        playerAccountDetail.setOrigin(4);
                        playerAccountDetail.setAddTime(addTime);
                        playerAccountDetail.setItem(2);
                        playerAccountDetail.setValue(20);
                        playerAccountDetailService.save(playerAccountDetail);

                        //升级公式计算
                        DanceUtils.uplevelNeedExp(userToRole, exp, level);
                        userToRoleService.save(userToRole);
                    }
                }
            }
            map.put("userToRoleList", listByUserId);
            commandService.addCommand(serialNumber, proId, map);


            LoginDetail loginDetail = new LoginDetail();
            loginDetail.setLoginTime(new Date());
            loginDetail.setProductId(productId);
            loginDetail.setUserId(userId);
            loginDetailService.save(loginDetail);

            keys.forEach(item -> {
                String value = requestMap.get(item);
                log.info(item + "===>" + value);
                if (item.equals("Ticket")) {
                    //当前用户进行了扫描动态二维码登录pc管理端操作
                    log.info("==>开始处理扫码登录");
                    log.info("当前用户openid:" + openid);
                    log.info("当前用户Ticket:" + value);
                    ldkjWxApiService.handleScanQrcode(openid, value);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }



//    /**
//     * @description 公众号发送模板消息
//     * @author: liyinlong
//     * @date 2019-05-05 13:58
//     * @return
//     */
//    @ApiOperation("1.4：发送公众号模板消息")
//    @GetMapping("/sendPubTemplateMessage")
//    public String sendPubTemplateMessage(@RequestParam String openId){
//        log.info("1.4：发送公众号模板消息===>openId:" + openId);
//        ldkjWxApiService.sendPubTemplateMessage(openId);
//        return "success";
//    }

//    /**
//     * @description 生成小程序二维码 官方文档：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
//     * @author: liyinlong
//     * @date 2019-05-09 15:00
//     * @param strinfo
//     * @return
//     */
//    @ApiOperation("1.5：生成小程序二维码")
//    @GetMapping("/createQrCode")
//    public String createQrCode(String strinfo){
//        log.info("1.5：生成小程序二维码===>strinfo:"+strinfo);
//        ldkjWxApiService.createQrCode(strinfo);
//        return "success";
//    }


    /**
     * 官方文档 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542
     * 注意！！！此接口仅认证的服务号才能使用！！！
     *
     * @return
     * @description 生成公众号带参数二维码
     */
    @ApiOperation("1.6：生成公众号带参二维码")
    @GetMapping("/getPubQrCode")
    public void getPubQrCode(@RequestParam(value = "type") Integer type,
                             @RequestParam(value = "serialNumber") String serialNumber,
                             @RequestParam(value = "position") Integer position) throws java.text.ParseException, UnsupportedEncodingException {
        log.info("1.6：生成公众号带参二维码");
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        Integer productId = bySerialNumber.getProductId();
        ProductToUser pt = productToUserService.getByProductIdAndPosition(productId, position);
        Date date = new Date();
        long time = date.getTime();
        long expireTime = time + 24 * 60 * 60 * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(expireTime);
        Date parse = sdf.parse(format);

//        if (pt != null) {
//            //判断当前机位是否有用户在使用
//            if (pt.getUserId() == null) {
//                Date time1 = pt.getExpireTime();
//                long time2 = time1.getTime();
//                long time3 = new Date().getTime();
//                //判断二维码是否超时
//                if (time2 > time3) {
//                    //未超时
//                    QrcodeVO qrcodeVO = new QrcodeVO();
//                    qrcodeVO.setQrcode(pt.getQrcode());
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("type", "1002");
//                    System.out.println("qrc:" + qrcodeVO.getQrcode());
//                    map.put("qrcode", qrcodeVO.getQrcode());
//                    map.put("expireTime", sdf.format(time1));
//                    addCommand(serialNumber, productId, map);
//                    return;
//                }
//                ServerResponse pubQrCode = ldkjWxApiService.getPubQrCode(2, productId, position);
//                JSONObject jsonObject = JSONObject.fromObject(pubQrCode);
//                JSONObject data = jsonObject.getJSONObject("data");
//                String qrCodeUrl = data.getString("qrCodeUrl");
//                Map<String, Object> map = new HashMap<>();
//                map.put("type", "1002");
//                map.put("qrcode", qrCodeUrl);
//                map.put("expireTime", format);
//                addCommand(serialNumber, productId, map);
//                pt.setExpireTime(parse);
//                pt.setQrcode(qrCodeUrl);
//                productToUserService.save(pt);
//                return;
//            } else {
//                log.info(productId + "-" + position + "该机位正被用户使用");
//                return;
//            }
//        }

        //--------------超时或新增机位-----------------
        //需要生成带参数的二维码
        ServerResponse pubQrCode = ldkjWxApiService.getPubQrCode(type, productId, position);


        JSONObject jsonObject = JSONObject.fromObject(pubQrCode);
        JSONObject data = jsonObject.getJSONObject("data");
        String qrCodeUrl = data.getString("qrCodeUrl");
        if (pt == null) {
            //新增
            ProductToUser productToUser = new ProductToUser();
            productToUser.setProductId(productId);
            productToUser.setPosition(position);
            productToUser.setQrcode(qrCodeUrl);
//            productToUser.setUserId();
            productToUser.setExpireTime(parse);
            productToUserService.save(productToUser);
        } else {
            //超时
            pt.setExpireTime(parse);
            pt.setQrcode(qrCodeUrl);
//            pt.setUserId(null);
            productToUserService.save(pt);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", "1002");
        map.put("qrcode", qrCodeUrl);
        map.put("expireTime", format);
        addCommand(serialNumber, productId, map);
    }

    @ApiOperation("1.6：生成公众号带参二维码")
    @RequestMapping("/mk")
    public String mkTest() {
        log.info("mkTest");
        return "hello";
    }

    private void addCommand(String serialNumber, Integer productId, Map<String, Object> map) {
        Command command = new Command();
        command.setSerialNumber(serialNumber);
        command.setIsDeleted(0);
        command.setProductId(productId);
        command.setAddTime(new Date());
        command.setCommand(100);
        command.setParameters(JsonUtils.toString(map));
        commandService.add(command);
    }


}
