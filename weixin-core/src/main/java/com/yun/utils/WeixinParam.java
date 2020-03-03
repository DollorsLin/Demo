package com.yun.utils;

import org.springframework.beans.factory.annotation.Autowired;

public enum WeixinParam {


    // 需要进行更改维护
    WEIXIN_APPID("APPID", "wxb491b133b0f62142"),


    // 需要进行更改维护
    WEIXIN_APPSECRET("SECRET", "7e2b02fc61d35830b39de40b2dd839a8"),


    // 拿到code
    WEIXIN_GET_CODE_URL("GET_CODE", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect"),

    
    // 回调地址   value：需要进行更改维护
    WEIXIN_REDIRECT_URL("REDIRECT_URI", "http://dollarsdl.free.idcfengye.com/wxapi/checkToken"),
    
//     (微信公众号开发)不弹出授权页面，直接跳转，只能获取用户openid
    WEIXIN_SNSAPI_BASE("SCOPE", "snsapi_base"),
    
    // (微信公众号开发)弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息
    WEIXIN_SNSAPI_USERINFO("SCOPE", "snsapi_userinfo"),
    
    // 获取到code之后换取AccessToken
    WEIXIN_GET_ACCESS_TOKEN_URL("ACCESS_TOKEN", "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"),
    
    // 获取到用户信息
    WEIXIN_GET_USERINFO_URL("GET_USERINFO", "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"),

    // 获取用户信息
    ATTENDTION_USERINFO_URL("ATTENTION_GET_USERINFO","https://api.weixin.qq.com//cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN");
    
    private String key;

    private String value;

    private WeixinParam(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

}
