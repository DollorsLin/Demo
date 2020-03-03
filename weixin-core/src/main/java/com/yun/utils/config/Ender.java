package com.yun.utils.config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.util.ByteUtils;

import java.nio.charset.Charset;

public class Ender {
    int offset;            //尾部偏移
    int sign;            //尾部标签,用于验证
    int time;            //文件修改时间,UTC格式
    int dataType;            //Json,Zip, Bin, TXT, Other
    int originSize;        //加密前原始数据长度
    int codeSize;        //加密后的数据长度,4096对齐
    byte dataMd5[];        //16byte解密后的数据校验

    public Ender(int sign, int originSize, int codeSize, byte dataMd5[]) {

        int enderLength = 52;
        int length1 = 4096 - enderLength;
        int offset = RandomUtils.nextInt(0, length1);

        this.offset = offset;
        this.sign = sign;
        this.time = (int) System.currentTimeMillis() / 1000;
        this.dataType = 4;
        this.originSize = originSize;
        this.codeSize = codeSize;
        this.dataMd5 = dataMd5;

    }

    public Ender(byte[] data, int offset) {
        int remain = data.length - offset;
        byte[] aaaa = new byte[remain];
        System.arraycopy(data, offset, aaaa, 0, remain);

        ByteBuf byteBuf = Unpooled.wrappedBuffer(aaaa);
        int sign = byteBuf.readInt();
        int time = byteBuf.readInt();
        int dataType = byteBuf.readInt();
        int originSize = byteBuf.readInt();
        int codeSize = byteBuf.readInt();
        CharSequence charSequence = byteBuf.readCharSequence(32, Charset.forName("UTF-8"));
        String s = charSequence.toString();
        this.sign = sign;
        this.time = time;
        this.dataType = dataType;
        this.originSize = originSize;
        this.codeSize = codeSize;

        System.out.println("");
    }


    public byte[] getEnder() throws Exception {
        byte[] bytes1 = Util.intToByte(sign);
        byte[] bytes2 = Util.intToByte(time);
        byte[] bytes3 = Util.intToByte(dataType);
        byte[] bytes4 = Util.intToByte(originSize);
        byte[] bytes5 = Util.intToByte(codeSize);
        byte[] bytes = ByteUtils.concatAll(bytes1, bytes2, bytes3, bytes4, bytes5, this.dataMd5);
        byte[] ender = RandomUtils.nextBytes(4096);
        System.arraycopy(bytes, 0, ender, offset, bytes.length);
        byte[] encrypt = AES.encrypt(ender);
        return encrypt;
    }

}
