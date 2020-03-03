package com.yun.utils.config;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.util.ByteUtils;

import java.nio.ByteBuffer;

/**
 * 4096 字节
 */
public class Header {
    int sign;            //头部标签,用于验证
    int type;            //文件类型,0未知,1配置文件,2,授权文件,3,升级包
    int time;            //文件创建时间,UTC格式
    int offset;            //尾部结构体在尾部内的偏移
    int endSign;        //尾部标签,用于验证
    byte[] key;            //RSA2048加密好的文件AES秘钥,RSA_PKCS1_PADDING 填充模式

    public Header(int offset, int endSign) throws Exception {
        this.sign = 0x5A2D3F4C;
        this.type = 2;
        this.time = (int) (System.currentTimeMillis() / 1000);
        this.offset = offset;
        this.endSign = endSign;
        byte[] aesKey = new AESKey().getAESKey();
        byte[] bytes = RSA.encrypt3(aesKey);
        this.key = bytes;
    }

    public Header(byte[] data) throws Exception {

        int randDataLength = 3820;
        byte[] signByte = new byte[4];
        System.arraycopy(data, randDataLength, signByte, 0, 4);
        byte[] typeByte = new byte[4];
        System.arraycopy(data, 3819 + 4, typeByte, 0, 4);
        byte[] timeByte = new byte[4];
        System.arraycopy(data, 3819 + 4 + 4, timeByte, 0, 4);
        byte[] offsetByte = new byte[4];
        System.arraycopy(data, 3819 + 4 + 4 + 4, offsetByte, 0, 4);
        byte[] endSignByte = new byte[4];
        System.arraycopy(data, 3819 + 4 + 4 + 4 + 4, endSignByte, 0, 4);
        byte[] keyByte = new byte[256];
        System.arraycopy(data, 3819 + 4 + 4 + 4 + 4 + 4, keyByte, 0, 256);
        ByteBuffer signBuffer = ByteBuffer.wrap(signByte);
        long sign = signBuffer.getInt();
        ByteBuffer typeBuffer = ByteBuffer.wrap(typeByte);
        int type = typeBuffer.getInt();
        ByteBuffer timeBuffer = ByteBuffer.wrap(timeByte);

        int time = timeBuffer.getInt();


        ByteBuffer offsetBuffer = ByteBuffer.wrap(offsetByte);
        int offset = offsetBuffer.getInt();
        ByteBuffer endSignBuffer = ByteBuffer.wrap(endSignByte);
        int endSign = endSignBuffer.getInt();
        System.out.println(endSign);

        byte[] bytes = RSA.decrypt3(keyByte);
        this.type = type;
        this.time = time;
        this.offset = offset;
        this.endSign = endSign;
    }

    public byte[] getHeader() {
        int randDataLength = 3820;

        byte[] bytes1 = Util.intToByte(sign);
        byte[] bytes2 = Util.intToByte(type);
        byte[] bytes3 = Util.intToByte(time);
        byte[] bytes4 = Util.intToByte(offset);
        byte[] bytes5 = Util.intToByte(endSign);

//        System.arraycopy(bytes1, 0, bytes,randDataLength - 1, 4);
//        System.arraycopy(bytes2, 0, bytes,randDataLength - 1 + 4, 4);
//        System.arraycopy(bytes3, 0, bytes,randDataLength - 1 + 8, 4);
//        System.arraycopy(bytes4, 0, bytes,randDataLength - 1  + 12, 4);
//        System.arraycopy(bytes5, 0, bytes,randDataLength - 1  + 16, 4);
//        System.arraycopy(this.key, 0, bytes,randDataLength - 1  + 20, 256);

        byte[] bytes = ByteUtils.concatAll(bytes1, bytes2, bytes3, bytes4, bytes5, this.key);
        byte[] header = RandomUtils.nextBytes(4096);
        System.arraycopy(bytes, 0, header, randDataLength, bytes.length);
        return header;
    }

}
