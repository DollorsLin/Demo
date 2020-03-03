package com.yun.utils;

import com.yun.pojo.UserToRole;

public class DanceUtils {


    public static void uplevelNeedExp(UserToRole userToRole, Integer exp, Integer level) {
        String uplevel = String.valueOf(Math.pow(level, 1.5));
        Double upLevel = 200 * Double.parseDouble(uplevel) + 100 * level;
//        String substring = uplevel.substring(0, uplevel.indexOf("."));
        String s = upLevel.toString();
        Integer i = Integer.valueOf(s.substring(0, s.indexOf(".")))/10 * 10;
        if (i > exp) {
            userToRole.setExp(exp);
        } else {
            userToRole.setExp(exp - i);
            userToRole.setLevel(level + 1);
        }
    }

    public static void main(String[] args) {
        Integer level = 5;
        String uplevel = String.valueOf(Math.pow(level, 1.5));
        String substring = uplevel.substring(0, uplevel.indexOf("."));
        Double upLevel = 200 * Double.parseDouble(uplevel) + 100 * level;
        String s = upLevel.toString();
        Integer a = Integer.valueOf(s.substring(0, s.indexOf(".")))/10 * 10;
        System.out.println("up:" + a);
    }
}
