package com.yun.utils.config;

import org.apache.commons.lang3.RandomUtils;

public class EncryptData {
    String data;
    public EncryptData(String data) {
        //加密数据填充
        this.data = data;

    }
    public byte[] getEncryptData() throws Exception {
        byte[] dataBytes = data.getBytes("UTF-8");
        int lengthEncrypt = dataBytes.length % 4096;
        int lengthEncrypt1 = 4096 - lengthEncrypt;
        byte[] bytes = RandomUtils.nextBytes(dataBytes.length + lengthEncrypt1);
        System.arraycopy(dataBytes,0,bytes,0, dataBytes.length);
        byte[] encrypt = AES.encrypt(bytes);
        return encrypt;
    }

}
