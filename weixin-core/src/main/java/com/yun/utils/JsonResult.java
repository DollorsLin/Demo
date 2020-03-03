package com.yun.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "JsonResult")
public class JsonResult<T> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "code")
    public Integer code;
    @ApiModelProperty(value = "msg")
    public String msg;
    @ApiModelProperty(value = "data")
    public T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public static JsonResult ok() {
        return new JsonResult();
    }

    public static <T> JsonResult ok(T t) {
        JsonResult ok = ok();
        ok.setData(t);
        return ok;
    }

    public static JsonResult error(int code, String msg) {
        JsonResult r = new JsonResult();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static JsonResult error(String msg) {
        return error(500, msg);
    }

    public void setData(T t) {
        this.data = t;
    }

    public JsonResult() {
        this.code = 0;
        this.msg = "";
    }

    public static JsonResult error() {
        return error(500, "未知异常，请联系管理员");
    }


}
