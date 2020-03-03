package com.yun.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yun.pojo.Score;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AES {

    private final static Logger log = LoggerFactory.getLogger(AES.class);

    public static String cKey = "F8D84468VF06LJ2VF66R86L4HV480F20";
    public static String iv = "20R2440R4RRP4088";
    static SecretKeySpec secretKeySpec;
    static IvParameterSpec ivParameterSpec;
    static {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            byte[] raw = cKey.getBytes("utf-8");
            secretKeySpec = new SecretKeySpec(raw, "AES");
            ivParameterSpec = new IvParameterSpec(AES.iv.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }


    // 加密
    public static byte[] encrypt(byte[] data) throws Exception {

        Cipher encryptCipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        int remainder = data.length % 16;
        if(remainder != 0){
            int increase = 16 - remainder;
            int newLength = data.length + increase;
            byte[] bytes = new byte[newLength];
            System.arraycopy(data,0, bytes, 0, data.length);
            byte[] encrypted = encryptCipher.doFinal(bytes);
            return encrypted;
        } else {
            byte[] encrypted = encryptCipher.doFinal(data);
            return encrypted;
        }
    }

    private static int zeroIndex(byte[] data){
        int length = data.length;
        for(int i = length -1; i >= 0; i--){
            if(data[i] != 0){
                return i;
            }
        }
        return -1;
    }
    // 解密
    public static byte[] decrypt(byte[] data) throws Exception {
//        byte[] raw = cKey.getBytes("utf-8");
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        IvParameterSpec iv = new IvParameterSpec(AES.iv.getBytes());
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/NoPadding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = decryptCipher.doFinal(data);
        int index = zeroIndex(original);
        int newLength = index + 1;
        byte[] newData = new byte[newLength];
        System.arraycopy(original,0, newData, 0, newLength);
        return newData;
    }


    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        HeartbeatLog log = new HeartbeatLog();
//        log.setAddTime(new Date());
//        log.setCreateTime(new Date());
//        log.setProductId(1);
//        log.setSerialNumber("D22BA56C");
//        String s = gson.toJson(log);
//        System.out.println(s);
//
//        Score score = new Score();
//
//        score.setSerialNumber("D22BA56C");
//        score.setGameIdentifier("1");
//        score.setPosition(1L);
//        score.setCreateTime(new Date());
//        score.setCoin(1);
//        score.setRefundCoin(1);
//        score.setTicket(1);
//
//        Score score1 = new Score();
//        score1.setSerialNumber("D22BA56C");
//        score1.setGameIdentifier("1");
//        score1.setPosition(1L);
//        score1.setCreateTime(new Date());
//        score1.setCoin(1);
//        score1.setRefundCoin(1);
//        score1.setTicket(1);
//
//        Score score2 = new Score();
//        score2.setSerialNumber("D22BA56C");
//        score2.setGameIdentifier("1");
//        score2.setPosition(1L);
//        score2.setCreateTime(new Date());
//        score2.setCoin(1);
//        score2.setRefundCoin(1);
//        score2.setTicket(1);
//
//        List<Score> list = new ArrayList<>();
//        list.add(score);
//        list.add(score1);
//        list.add(score2);
//
//        String s1 = gson.toJson(list);
//        System.out.println(s1);

//        UpdatePackageToProduct u = new UpdatePackageToProduct();
//        u.setAddTime(new Date());
//        u.setCategoryIdUpdate(1);
//        u.setFilePathUpdate("http://");
//        u.setProductId(1);
//        u.setProgress("60.50");
//        u.setSerialNumber("serialNumber");
//        u.setUpdatePackageId(1);
//        u.setVersionUpdate("100");
//        String s1 = gson.toJson(u);
//        System.out.println(s1);
    }

    public static void main11(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "[{\"serialNumber\":\"D22BA56C\",\"position\":1,\"point\":10,\"scoreType\":1,\"gameIdentifier\":\"1\",\"createTime\":\"2019-08-08 13:58:21\"},{\"serialNumber\":\"D22BA56C\",\"position\":1,\"scoreType\":1,\"gameIdentifier\":\"1\",\"createTime\":\"2019-08-08 13:58:21\"},{\"serialNumber\":\"D22BA56C\",\"position\":1,\"point\":10,\"scoreType\":1,\"gameIdentifier\":\"1\",\"createTime\":\"2019-08-08 13:58:21\"}]";
//        String cSrc = "abc";
//        System.out.println(cSrc);
////

        long start = System.currentTimeMillis();
        byte[] encrypt = AES.encrypt(cSrc.getBytes("utf-8"));
//        // 解密
        byte[] bytes = Base64.encodeBase64(encrypt);

        String ss = "Cxa87zO3fBtVBtzeVb2+jw==";
//        String decode = URLDecoder.decode(ss, "UTF-8");

        byte[] encrypt11 = Base64.decodeBase64(ss);
        byte[] decrypt = AES.decrypt(encrypt11);
        long end = System.currentTimeMillis();
        long a = (end - start);
        System.out.println(a);
        System.out.println("加密后的字符串为:" + Base64.encodeBase64String(encrypt));
        System.out.println("还原后的字符串为:" + new String(decrypt, "UTF-8"));

        long start1 = System.currentTimeMillis();
        byte[] encrypt1 = AES.encrypt(cSrc.getBytes("utf-8"));
//        // 解密
        byte[] bytes1 = Base64.encodeBase64(encrypt1);

        String ss1 = "Cxa87zO3fBtVBtzeVb2+jw==";
//        String decode = URLDecoder.decode(ss, "UTF-8");

        byte[] encrypt111 = Base64.decodeBase64(ss1);
        byte[] decrypt1 = AES.decrypt(encrypt111);
        long end1 = System.currentTimeMillis();
        long a1 = (end1 - start1);

        System.out.println(a1);
        System.out.println("加密后的字符串为:" + Base64.encodeBase64String(encrypt1));
        System.out.println("还原后的字符串为:" + new String(decrypt1, "UTF-8"));

//        String a = "FD C3 FE 1D 29 E0 8E 45 E4 4B 20 39 B8 A0 DD 14 00";
//        String[] s = a.split(" ");
//
//        String sss = "";
//        for(String ss :s){
//            sss = (sss +", 0x" + ss + "");
//        }
//        System.out.println(sss);

//        int[] aaaa = {0xFD, 0xC3, 0xFE, 0x1D, 0x29, 0xE0, 0x8E, 0x45, 0xE4, 0x4B, 0x20, 0x39, 0xB8, 0xA0, 0xDD, 0x14, 0x00};
//        String aaaaaa = "";
//        List<Byte> b = new ArrayList<>();
//        for (int i : aaaa) {
//            byte[] bytes = IntToByte(i);
//            b.add(bytes[3]);
//        }
//
//        byte[] abc = new byte[b.size()];
//        for (int i = 0; i < b.size(); i++) {
//            abc[i] = b.get(i);
//        }
//
//        byte[] decrypt = decrypt(abc);
//        String s = new String(decrypt, "UTF-8");
//        System.out.printf(s);
//
//
//
//        String abcccc = "{\n\f\"CategoryID\" : 24,\n\f\"Sno\" : \"D22BA56C\",\n\f\"Timestamp\" : 1562745581,\n\f\"VersionCurrent\" : 187,\n\f\"VersionToBeUpdated\" : 0\n}\n";
//        Gson g = new Gson();
//        A a = g.fromJson(s, A.class);
//
//        System.out.println(a);


    }


    class A{
        Integer CategoryID;
        String Sno;
        Long Timestamp;
        Integer VersionCurrent;
        Integer VersionToBeUpdated;

        public Integer getCategoryID() {
            return CategoryID;
        }

        public void setCategoryID(Integer categoryID) {
            CategoryID = categoryID;
        }

        public String getSno() {
            return Sno;
        }

        public void setSno(String sno) {
            Sno = sno;
        }

        public Long getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(Long timestamp) {
            Timestamp = timestamp;
        }

        public Integer getVersionCurrent() {
            return VersionCurrent;
        }

        public void setVersionCurrent(Integer versionCurrent) {
            VersionCurrent = versionCurrent;
        }

        public Integer getVersionToBeUpdated() {
            return VersionToBeUpdated;
        }

        public void setVersionToBeUpdated(Integer versionToBeUpdated) {
            VersionToBeUpdated = versionToBeUpdated;
        }
    }

    public static byte[] IntToByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;
    }
}
