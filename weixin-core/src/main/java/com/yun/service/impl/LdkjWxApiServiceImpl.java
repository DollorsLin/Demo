package com.yun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yun.po.WeixinUserInfo;
import com.yun.pojo.ProductToUser;
import com.yun.service.LdkjWxApiService;
import com.yun.service.ProductToUserService;
import com.yun.service.UserToRoleService;
import com.yun.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Transactional
@Service
public class LdkjWxApiServiceImpl implements LdkjWxApiService {

    @Autowired
    private ProductToUserService productToUserService;


    @Value("${WEIXIN_APPID}")
    private String WEIXIN_APPID;

    @Value("${WEIXIN_APPSECRET}")
    private String WEIXIN_APPSECRET;

    @Value("${USERINFO_URL}")
    private String USERINFO_URL;

    @Override
    public void sendMiniTemplate(String formid, String openid) {
        Token minitoken = CommonUtil.getToken(WxConstant.MINI_APPID, WxConstant.MINI_SECRET);

        log.info("开始发送服务通知");
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTemplate_id("模板id");
        wxMssVo.setTouser(openid);
        wxMssVo.setForm_id(formid);
        wxMssVo.setPage("pages/login/login");
        wxMssVo.setAccess_token(minitoken.getAccessToken());
        wxMssVo.setRequest_url(CommonUtil.SEND_MINI_MESSAGE_REQUEST_URL + minitoken.getAccessToken());
        List<TemplateData> list = new ArrayList<>();

        TemplateData td0 = new TemplateData("内容1");
        TemplateData td1 = new TemplateData("内容2");
        TemplateData td2 = new TemplateData("内容3");

        list.add(td0);
        list.add(td1);
        list.add(td2);

        wxMssVo.setParams(list);
        CommonUtil.sendTemplateMessage(wxMssVo);
    }

    @Override
    public void sendPubTemplateMessage(String pubOpenid) {
        Token pubtoken = CommonUtil.getToken(WxConstant.FUWUHAO__APPID, WxConstant.FUWUHAO__SECRET);
        WxMssVo wxMssVo = new WxMssVo();

        wxMssVo.setTemplate_id("模板id");
        wxMssVo.setTouser(pubOpenid);
        wxMssVo.setMessageType(2);
        wxMssVo.setRequest_url("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + pubtoken.getAccessToken());
        List<TemplateData> list = new ArrayList<>();

        TemplateData td0 = new TemplateData("first", "xxx");
        TemplateData td1 = new TemplateData("keyword1", "xxx");
        TemplateData td2 = new TemplateData("keyword2", "xxx");
        TemplateData td3 = new TemplateData("remark", "xxx");

        list.add(td0);
        list.add(td1);
        list.add(td2);
        list.add(td3);

        wxMssVo.setParams(list);
        CommonUtil.sendTemplateMessage(wxMssVo);
    }

    @Override
    public void createQrCode(String strInfo) {
        Token minitoken = CommonUtil.getToken(WxConstant.MINI_APPID, WxConstant.MINI_SECRET);
        String accessToken = minitoken.getAccessToken();
        log.info("token: " + accessToken);
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = CommonUtil.QRCODE_URL + accessToken;
            Map<String, Object> param = new HashMap<>();
            param.put("scene", strInfo);//不可超过32位
//            param.put("page", "pages/login/login");
            param.put("width", 800);
            param.put("auto_color", false);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            log.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class,
                    new Object[0]);
            log.info(entity.toString());
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);

            File file = new File("E://file/images/" + "demo" + ".jpg");

            if (!file.getParentFile().exists() && !file.isDirectory()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            log.info("upload url:" + file.getAbsolutePath());
            String path = file.getAbsolutePath();
            log.info("file: " + path.substring(0, path.lastIndexOf(File.separator)));
        } catch (Exception e) {
            log.error("调用小程序生成微信永久小程序码URL接口异常", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ServerResponse getPubQrCode(Integer type, Integer productId, Integer position) {
        //此处getToken方法内的参数需换成自己的服务号appid和secret
        Token pubtoken = CommonUtil.getToken(WxConstant.FUWUHAO__APPID, WxConstant.FUWUHAO__SECRET);
        JSONObject params = new JSONObject();

        //字符串参数
        params.put("action_name", "QR_STR_SCENE");
        //整型参数
//        params.put("action_name", "QR_SCENE");

        params.put("expire_seconds", 24 * 60 * 60);

        JSONObject action_info = new JSONObject();
        JSONObject action_info_result = new JSONObject();
        //对应整型参数
//        action_info_result.put("scene_id", 3);

        //保存uuid
//        String uuid = UUID.randomUUID().toString();
//        ProductToUser pt = productToUserService.getByProductIdAndPosition(productId, position);
//        if (pt == null) {
//            //新增
//            ProductToUser productToUser = new ProductToUser();
//            productToUser.setProductId(productId);
//            productToUser.setPosition(position);
////            productToUser.setUuid(uuid);
//            productToUserService.save(productToUser);
//        } else {
////            pt.setUuid(uuid);
//            productToUserService.save(pt);
//        }


        //对应字符串参数
        action_info_result.put("scene_str", type + "_" + productId + "_" + position );
        action_info.put("scene", action_info_result);
        params.put("action_info", action_info);



        Map<String, Object> result = new HashMap<>();
        try {
            JSONObject res = JSON.parseObject(MyHttpRequestUtil.sendPost(CommonUtil.CONTAIN_PARAMS_QRCODE + pubtoken.getAccessToken(), params.toString()));
            String ticket = URLEncoder.encode(res.getString("ticket"), "UTF-8");
            String picUrl = CommonUtil.MP_QRCODE_PRE_URL + ticket;
            log.info("二维码地址:picUrl:" + picUrl);
            result.put("qrCodeUrl", picUrl);
            result.put("ticket", ticket);
            return ServerResponse.createBySuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByError();
    }

    //处理微信扫码登录
    @Override
    public void handleScanQrcode(String pubOpenid, String ticket) {
        try {
            log.info("处理微信扫码登录:");
            WebSocketServer.sendInfo(200, "访问成功", ticket);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void getCode(HttpServletResponse response) throws IOException {
        // 组装获取到code的url
        String getCodeUrl = WeixinParam.WEIXIN_GET_CODE_URL.getValue()
                .replace(WeixinParam.WEIXIN_APPID.getKey(), WEIXIN_APPID)
                // 这里替换微信获取code之后的回调路径地址
                .replace(WeixinParam.WEIXIN_REDIRECT_URL.getKey(), URLEncoder.encode(WeixinParam.WEIXIN_REDIRECT_URL.getValue()))
                .replace(WeixinParam.WEIXIN_SNSAPI_USERINFO.getKey(), WeixinParam.WEIXIN_SNSAPI_USERINFO.getValue());
//                .replace(WeixinParam.WEIXIN_SNSAPI_BASE.getKey(), WeixinParam.WEIXIN_SNSAPI_BASE.getValue());

        System.out.println("gg:" + getCodeUrl);
        // 进行转发
        response.sendRedirect(getCodeUrl);
    }

    @Override
    public WeixinUserInfo getUserInfo(String code, String state) throws ParseException, IOException {
        // 组装获取Token的url
        String getTokenUrl = WeixinParam.WEIXIN_GET_ACCESS_TOKEN_URL.getValue()
                .replace(WeixinParam.WEIXIN_APPID.getKey(), WEIXIN_APPID)
                .replace(WeixinParam.WEIXIN_APPSECRET.getKey(), WEIXIN_APPSECRET)
                .replace(Weixin.CODE, code);
        log.info("getTokenUrl:" + getTokenUrl);
        // 进行Get请求，拿到token和openID
        net.sf.json.JSONObject doGetStr = WeixinUtil.doGetStr(getTokenUrl);

        log.info("doGetStr:" + doGetStr);

        String openid = doGetStr.getString(Weixin.OPENID_RESULT);
        String access_token = doGetStr.getString(Weixin.ACCESS_TOKEN_RESULT);

        // 组装获取用户的信息的url
//        String userInfoUrl = WeixinParam.WEIXIN_GET_USERINFO_URL.getValue()
        String userInfoUrl = USERINFO_URL
                .replace(Weixin.ACCESS_TOKEN, access_token)
                .replace(Weixin.OPENID, openid);

        // 进行get请求，拿到登录人员的信息
        net.sf.json.JSONObject userInfo = WeixinUtil.doGetStr(userInfoUrl);

        // 对那到的人员信息进行解析，得到一个类对象
        WeixinUserInfo parse = JsonUtils.parse(userInfo.toString(), WeixinUserInfo.class);
        return parse;
    }


}
