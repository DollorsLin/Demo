package com.yun.utils.config;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.util.ByteUtils;

import java.io.File;

public class Config {
    byte[] header;
    byte[] encrypt;
    byte[] ender;


    public Config(String data) throws Exception {
        EncryptData encryptData = new EncryptData(data);
        int endSign = RandomUtils.nextInt(0, Integer.MAX_VALUE);

        byte[] md5 = MD5Utils.getMD5(data);

        this.encrypt = encryptData.getEncryptData();

        Ender ender1 = new Ender(endSign, data.getBytes().length, encrypt.length, md5);
        this.ender = ender1.getEnder();
        int offset = ender1.offset;
        Header header = new Header(offset, endSign);
        this.header = header.getHeader();

    }

    public byte[] getConfig() {
        byte[] bytes = ByteUtils.concatAll(this.header, this.encrypt, this.ender);
        return bytes;
    }

    public void readConfig(byte[] bytes) throws Exception {
        int blockLength = 4096;
        int length = bytes.length - blockLength - blockLength;

        this.header = new byte[blockLength];
        this.ender = new byte[blockLength];
        this.encrypt = new byte[length];
        for (int i = 0; i < blockLength; i++) {
            this.header[i] = bytes[i];
        }
        for (int i = 0; i < length; i++) {
            this.encrypt[i] = bytes[blockLength + i];
        }
        for (int i = 0; i < blockLength; i++) {
            this.ender[i] = bytes[blockLength + length + i];
        }

        Header header = new Header(this.header);

        Ender ender = new Ender(this.ender, header.offset);

        byte[] aaaa = new byte[ender.codeSize];
        System.arraycopy(this.encrypt, 0, aaaa, 0, ender.codeSize);
        byte[] decrypt = AES.decrypt(aaaa);
        String s = new String(decrypt);
        System.out.println(s);

    }

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        Config abc = new Config("abc");
        byte[] config = abc.getConfig();
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\T\\Desktop\\sh_71D77E7D_201907312304.shlic"), config);
        byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\T\\Desktop\\sh_71D77E7D_201907312304.shlic"));
        long end = System.currentTimeMillis();
        long a = (end - start);
        System.out.println(a);
    }

}
