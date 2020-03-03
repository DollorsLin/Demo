package com.yun.utils.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MD5Utils {

    /**
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {

        String encodeStr = DigestUtils.md5Hex(strValue);
        return encodeStr;
    }

//    public static byte[] getMD5 (byte[] bytes){
//        return DigestUtils.md5(bytes);
//    }

    public static String getMd5(String path) throws Exception {
        String md5 = DigestUtils.md5Hex(new FileInputStream(path));
        return md5;
    }



    public static byte[] getMD5(String strValue) {
            return DigestUtils.md5(strValue);
    }


    public static void main(String[] args) {
        try {

            byte[] abcs = DigestUtils.md5("abc");

            System.out.println(abcs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
