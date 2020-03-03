package com.yun.service;

import com.yun.po.WeixinUserInfo;
import com.yun.utils.ServerResponse;
import org.apache.http.ParseException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LdkjWxApiService {
    void sendMiniTemplate(String formid, String openid);
    void sendPubTemplateMessage(String pubOpenid);
    void createQrCode(String strInfo);
    ServerResponse getPubQrCode(Integer type, Integer productId, Integer position);
    public void handleScanQrcode(String pubOpenid, String ticket);


    void getCode(HttpServletResponse response) throws IOException;

    WeixinUserInfo getUserInfo(String code, String state) throws ParseException, IOException;
}
