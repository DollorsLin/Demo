package com.yun.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toString(Object o){
        return gson.toJson(o);
    }

    public static <T> T parse(String jsonData,Class<T> type){
        return gson.fromJson(jsonData, type);
    }
}
