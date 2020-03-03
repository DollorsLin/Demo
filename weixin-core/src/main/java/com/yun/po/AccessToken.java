package com.yun.po;

public class AccessToken{

    private String token;

    private int expiresIn;

    private String openid;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public int getExpiresIn(){
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn){
        this.expiresIn = expiresIn;
    }

    public String getOpenid(){
        return openid;
    }

    public void setOpenid(String openid){
        this.openid = openid;
    }

}
