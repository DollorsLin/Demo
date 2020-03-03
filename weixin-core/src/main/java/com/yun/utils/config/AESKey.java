package com.yun.utils.config;


import org.springframework.data.redis.util.ByteUtils;

public class AESKey {

    public String DataKey;
    public String DataIv;
    public String EnderKey;
    public String EnderIv;

    public AESKey() {
        this.DataKey = AES.cKey;
        this.DataIv = AES.iv;
        this.EnderKey = AES.cKey;
        this.EnderIv = AES.iv;
    }

    public byte[] getAESKey() {
        byte[] bytes = ByteUtils.concatAll(this.DataKey.getBytes(), this.DataIv.getBytes(), this.EnderKey.getBytes(), this.EnderIv.getBytes());
        return bytes;
    }


//    public i(byte[] data) {
//        this.DataKey = new byte[32];
//        this.DataIv = new byte[16];
//        this.EnderKey = new byte[32];
//        this.EnderIv = new byte[16];
//
//        ByteUtils.concatAll(bytes1, bytes2, bytes3, bytes4, bytes5, this.key);
//
//        System.arraycopy(data, 0, this.DataKey, 0, 32);
//        System.arraycopy(data, 32, this.DataIv, 0, 16);
//        System.arraycopy(data, 48, this.EnderKey, 0, 32);
//        System.arraycopy(data, 80, this.EnderIv, 0, 16);
//    }

}
