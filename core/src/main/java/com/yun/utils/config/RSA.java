package com.yun.utils.config;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {

    private final static Logger log = LoggerFactory.getLogger(RSA.class);

    public static String publicKey1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArvH2SK7TraTEzeNqFUbHTTtY0N72Oz2k3bozdXqrgXPSk30Vr6mdhkw0FCJoYxIAf3FhHe9H+mx287zJuRXMN3XId312cgfvU2fDz546XGldPoUcaSjtk1P3DyTFE0Q+SZMu3mSr24WPS4z2x6DOjldNfYzAYjjPYr15SpzSopJ9c9YNhZbuD4cCC8aeydjwQ0gUmCzQhsoFU9+sAMeY61GsbP9lcqKZHf/231Yplr3YVrCnu5l546hFp5rc4Oh1f4uwqdGYQI742m/GU7PwgUzddjJA68SHil/inuZXMZucgJGfx1Hav1luefbsBzheak7DXIgmJnExX1g/pvJCCwIDAQAB";
    public static String privateKey1 = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCu8fZIrtOtpMTN42oVRsdNO1jQ3vY7PaTdujN1equBc9KTfRWvqZ2GTDQUImhjEgB/cWEd70f6bHbzvMm5Fcw3dch3fXZyB+9TZ8PPnjpcaV0+hRxpKO2TU/cPJMUTRD5Jky7eZKvbhY9LjPbHoM6OV019jMBiOM9ivXlKnNKikn1z1g2Flu4PhwILxp7J2PBDSBSYLNCGygVT36wAx5jrUaxs/2Vyopkd//bfVimWvdhWsKe7mXnjqEWnmtzg6HV/i7Cp0ZhAjvjab8ZTs/CBTN12MkDrxIeKX+Ke5lcxm5yAkZ/HUdq/WW559uwHOF5qTsNciCYmcTFfWD+m8kILAgMBAAECggEADAeaWlwfNSkH2WqCYG0qBj8G9/Hk96ThAdgscq8ZPQEUYUzwIVCmAtaZnCrTh8B0pU7MEU1jYy5zLYZ1TpNXpBamzsSjUOsHw3H2rn+gXrtmDIcFsZt9Xqye5/0sygrex5tl1SgSAznXZSgPziFaIsKREwmLLxXd+NmzjfRjrYcNFePtD8IDtrqhL0O9dTEvFze8HPyapdJuv8bGUOck4mmIOqSVAD5Ipix4bzfcj2ZBcRVKSXhRm5rGHktgAYhaas7/TPbR/Kp2JuejVHWrMVUoPWmOi7VsKMA+4E7ONvCVBaBJaSnnD2auEUSko+oHJ7x+6psWzokdYzwsK+JnxQKBgQDLgSC//O6eYrtJh7NbBKvkkW/kHmUJdbmzF9xkd4McgF83ZCyUmCpMwguoxhCt/8w0t+q+TREpkLtIJgP1kAixFl9ARBWue7Km32pgDdJ1HAjvgl+921jB1IGGQXd3UrVJVt8juHuV9Rc6gO9Pj5Ctp2+1Fx1lxXFacLDueDcafQKBgQDcEtw34UWVIsrfoiijzlebOQa5SEoOctom4QHE0EdA0v5ZpMTckMZ4eNy5XiIE/ZNvHIe+f9pA0eyUd2viPKiq3fHktipnvSqNuHSV5mN5zg3YY+nrNFMgKls+T6Wp1V4h9NIm/hbuOkSefRC0m2fHYjv7qUEzxmAo8QH1Lj5tJwKBgGlVgyAcyV4N+KcMqFdaSabbaLEI0n9dAiujFB6khabBA4fmP/UB9hMY9XP8YTHPD2HSOJ0bwuBi261yj+YREY6n59GZ7CcOO+CID8V8Ig0f37gpIWliGcTqO8jEJhQLKW62mnUgzkdGPym3ByeFRfu8iOOQ/VRqKEwJvGsSqmL5AoGAEjUFPyJGDXaZbjMORa8WlGL9aWrPpa5e2gSXjt7kZjqOjIATQWRA50qoiDV/fwYzomer21jch6xWdFoE9Oyrz6cZEiD4FSkTUjJGgvhSloszngl2Gt1zWx/l+JvCSvC6HvkQQeWXpBHyB2K1dRu+VEKOT93K5SuuXPTDgrNWutUCgYAiwP7ltU1ujHbulDm1ttwj8gnNK1VmXp97UQO17zD4URlw9witPF+3qrUi10f5FegynQx9+VdOdvIfbSOkCqc7XIUWXGfFzFRxYDh7M67MBlScZvMXsW9uqa2kyuIn4hsl4CXnIQczEKTx9nz+xrzIR9fM5C8Dz9HbTqw8bOe7vQ==";;
    public static String publicKey2 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyIs7/jiqN0MKc//fAAjh3JYP0sZKz1oBYYAvcT8OSCpMrkpuWwt1VdTqdf9iVzNK0HHk0Tox4HK6t6XtTzxPiiPOl7GG/LEpKYEDc6mXu+3aap0O9qW7guXy9Wo5qEo1E3hik1KV5z+yHlYgCw4VjWOH11LJf1FOnumZYaTXQhOxdpORz/LDWsLXuEO/o1fTMMbjCFt3QfuYTHndnMR8NPvkirEGnxsPFP+q3HHMMMJO0AYLrVmeuqZoPFgC7nhjGd7r+jAm4yx2p9CzaNdG/0Xat7sWYUuQ1b6rZuXnZZTHmVQpjxE4mSPCh7rGGU5KzZJWcRBKvUmqYArjtQeCFQIDAQAB";
    public static String privateKey2 = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDIizv+OKo3Qwpz/98ACOHclg/SxkrPWgFhgC9xPw5IKkyuSm5bC3VV1Op1/2JXM0rQceTROjHgcrq3pe1PPE+KI86XsYb8sSkpgQNzqZe77dpqnQ72pbuC5fL1ajmoSjUTeGKTUpXnP7IeViALDhWNY4fXUsl/UU6e6ZlhpNdCE7F2k5HP8sNawte4Q7+jV9MwxuMIW3dB+5hMed2cxHw0++SKsQafGw8U/6rcccwwwk7QBgutWZ66pmg8WALueGMZ3uv6MCbjLHan0LNo10b/Rdq3uxZhS5DVvqtm5edllMeZVCmPETiZI8KHusYZTkrNklZxEEq9SapgCuO1B4IVAgMBAAECggEAEeUavVyhE71PuXojlAU24lAhqVU4Z7hIV0Lw+NH/nEDqBsNthYJIPhh5s33OcKmLC36eRG1E9hpGvwx3WJKxJkp7rN2Shgno+mMgw7lmaMaYrtrE8w1m5g9O1sdbzcCRa+NaKHs+FRyB8GMPCO2AztUK0hP5FvtSYKvHnAbxjlu68t8i2j+7fhP3DxZtcsdyDvynnJrPS4fDX8nFBZHgk1N2Im2A0fip9S9h9t8W/3/FHX3PiCzQklSY+LDDsVv9vRBCeL/kG90ZQTGg6wxPNS0fdrpMKcN+EzHcyQzpm8aScF8i6IbF+J8mML1TUAtbAFVodPrpDHWvMpzG9EyMAQKBgQDRnTtvXuEDLDqTHauYHxDK8I88np05kQMVC6NYHDRvD2Vpf4oA1s+bNYj3b7KcoKBqKqB4gGfhJSrbOKsrPnm7uCMD9sNX3KWbn/lc45zPbwrOcLlOTwHehN+WRPtobey9lfgvN/bih887T6LzNVVpKG2T+mhu3de8sGc+ye+r9QKBgQD07CoBz9yeTsZhL+RM75OD8SCZDNUvDiqj140eOHf36CAMHvRza2zme/JcTNT95UlDq+wvGuTAEqE6fBHcJa1yGrrgQ8Jlcwmbt94/5w6Xt51Ao7v20jckik7MgzLfPmPTxw4WuLyMBpmSFJGEj0fk/FOnCv7Lynz5lH/azYXJoQKBgDSCH55hdRpXXQD3YMHFnm5H3om/7gC6q7v841kyD4x2nd/UnBxjszPdMEdTirmjnyuZLAOjnuIObnLl+jLl/pagpNzcVsjOtAmNBGlZdzGbTmiyYikUr5IvVNcOxq+9QZ2oULh7W3QZszbXVyl3hcLG5tQ32StUlTNmOSbIbSp1AoGAAxJrPHaU989ZIaLuJCDlIsSFeiX1DpAI7OEpPfXLW2qn7D7/Jet1BDcNxSaZjU2w645eegDGWbpiF0+zEaXPVR0Mtv9iks8ska1utjJ5tmFxMXxcQS5FzsmFT39dxfsMWX2nqZVLmB4FExX69USuzCp2CiRTKc8yXhcEu4PUZ8ECgYBTgF8QNKFMsL+r7RySMtX+vCVnr5DUl8XgSR3yj44778eQaNkZKaPbQJFknKkQuvY0/rhs7y27sFtCoUn6XsjaYztfPJ5L9qzQbOPYIGfeoJ333RzI2KwjMI4lAZGQiPSyEumPy1IDSTlfPfGhaZDlBJyV1eWTAnf3N0VL5+chJQ==";

    public static String publicKey3 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwoHyokgKgs/yGegEsIX0nkrVs37q9j2Wa/5UbuB1ChkSWykPPO1awSlODSIhEuusH76Jo1LotKnj2SgHjaReTTiCcQyYlrVpbbkGl8LDLnsla8+vKNhrk+mB0OaV2OMZDElcELmXwUaYVPxqNH5T4b7dKkUTjhZI3ioT5Kmoy5Zn1mXfTC0nzDwcWDQN2jj0/hJ2W8AcdSsjtiOQ6Dp2pxdoEJBauWKNZ081XpGjNeb3MRBH74tGAfeejH+1q7c24kI1tmXbymggdqzrpdJ2VgAMzsa4mzyeqm0/+0iTTH6sl1ydxs+GxMPF4SHt3SDBVuVvmwX4+rif0tmjl1WybwIDAQAB";
    public static String privateKey3 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCgfKiSAqCz/IZ6ASwhfSeStWzfur2PZZr/lRu4HUKGRJbKQ887VrBKU4NIiES66wfvomjUui0qePZKAeNpF5NOIJxDJiWtWltuQaXwsMueyVrz68o2GuT6YHQ5pXY4xkMSVwQuZfBRphU/Go0flPhvt0qRROOFkjeKhPkqajLlmfWZd9MLSfMPBxYNA3aOPT+EnZbwBx1KyO2I5DoOnanF2gQkFq5Yo1nTzVekaM15vcxEEfvi0YB956Mf7WrtzbiQjW2ZdvKaCB2rOul0nZWAAzOxribPJ6qbT/7SJNMfqyXXJ3Gz4bEw8XhIe3dIMFW5W+bBfj6uJ/S2aOXVbJvAgMBAAECggEAFFqY01aKkwnDoeZfp0E9Sds25aOgXsgvF3nzx+6BEN9yxktTpuKHnrL90e4CXAKBboj3yjmA1Hb6utyvfaj3nUkOjVnuFcdCExNLl1HwLssALXn8MGAFUeMbjIq0n4+ULW73zWA+eyEyC6KmUnvtty/L4QJ9e17lnSn2DvWaDkOuM0AGhOD/QfBJWEw94/DNWdkGgYg8K5qZIANC3V8K0mg7SveGJY15rQZcOWv7nn65vVSxvltIgYyWMGoGRPQGeCkVbd2IZ9qvIf+ngJSiBhYOF9/WacDGOEZbzoiZ/zIEvXbsdhpMgV/VFmwQM70Gosbj+MYC/l9trM33ufNhEQKBgQDqoRbLwFYMI5fOOOE+JeolbdpseDnGlcyuC9uUvR3LK8zJtPCWpFgj2J/+5EwOgPkkzuX+z4kqL969iozAX4goPZNSELsw15vCOyzszMzN0jdexySzvjqD6/FRJ1MDlDBVLTlrQI6iBZgVroguEGG5tYhc/0G2Lh1q4cBlZIwXlQKBgQDUOVT7n0Tsg9pPJU++TWd5YnCdnWTTTYjWeRFNmJAoVEfxLsyX62VfrxWYleMkxw7PxLJYijURQ0/DCFySo4WbSnBql5hd2aw6hU6ELU4gwsqwWMm52RTrTCmCF/TxwKrFB+anLO9YVBl0Yzv00FvL1lA0JGPjEv4NJAc7ENoQ8wKBgCMFRFfJL47ESUZYqxYRpq9LhEyobUc82L4puu6qg6XttbVtsnNpuFYxYUA0Zz3K9mUOFLpB0+FeH8r+wR99ot8Owcx//tC9fdwx5PS7ZN5bwDP/WXUSsb07HFvWmMdx+PzdsTJXAnMgjqC92km13EBR3jazSuWcYHuxknwP0/E5AoGBAMtIf5bm4OaNBjh5zQDEtDEZkjXn5gRhaInIPkLNuIho8uCbq256FQiqdU1LheBcrFtoVk1pVZvLlkODZukvqXMuwq/XtiNvtgH4X1VM8GGBNRXmdpSG0/rvYXMz73UC0JEbGF1aNYmuRROvWmcOjl+aUgkgMm34a1G7CRIS+k7xAoGBALSMFEAQoUahUj9hU9Nk69QDCYlKAbR1IYvHNLTngYpzsbwlyj7mxZxdpoIlK+eCXaT1ezCCWCwmEjbOB9opANMqtwXu0fR55Q7tB2ZqXcnAzHufKgLjaA1EfHuXhcNmO6YMql45gzr+9hiHSJmN5whaftqjVBauUi1DxlYedvxO";

    public static void genKeyPair() throws Exception {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(2014, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map

        System.out.println(publicKeyString);
        System.out.println(privateKeyString);

        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
        System.out.println("");
    }

    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    public static void main(String[] args) throws Exception {

//        HeartbeatLog h = new HeartbeatLog();
//        h.setProductName("a");
//        h.setProductId(1);
//        Gson gson = new Gson();
//        String message = gson.toJson(h);

        String message = "serialNumber=867732039241278";
//        genKeyPair();
//        //加密字符串
//        System.out.println("随机生成的公钥为:" + keyMap.get(0));
//        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        long start = System.currentTimeMillis();
        byte[] encrypt = encrypt3(message.getBytes("UTF-8"));

        String s = Base64.encodeBase64String(encrypt);
        System.out.println( s);

        String abc = "X7PGk5jkyO7FQuxnpSh1GvaqrhK2Ewc3wxHwszLBpCSsqAsFkhsvp%2B6d0rJAXq9OeacWjP7JDjIfqrBEeCk3YrbRlqcFBlJC5CoK3xonSHGEJ2sVvwkfsFpdEKWZ1Nj3vCzX%2F2pmlQzLQWv54f4G%2BPcjQxEmqTvVENHSdaM1SQ1TgKrqrRTHgLEtJVY6PIovvQTkwQ4Im61D5xFKngMr9RlUvw8prMeIdlCSqJotda0C%2Bl2XoOO%2Bx9dgWY%2BgECGb%2BAkM0RihvI8LHeaUU9eox1QXoA7Tu9i%2BSj1wx8ith0qizJZSY2DXkaG33ZGUmzacEIOTb0uttjIp9PYvRHOFSQ%3D%3D";
        String decode = URLDecoder.decode(abc, "UTF-8");

        byte[] bbbbb = decode.getBytes("UTF-8");
        byte[] bytes1 = Base64.decodeBase64(bbbbb);

//        byte[] bytes = Base64.decodeBase64(aaa);
//        String string = new String(bytes);
//        System.out.println(string);
//        byte[] bytes1 = aaa.getBytes("UTF-8");

        byte[] decrypt = decrypt3(bytes1);
        long end = System.currentTimeMillis();
        long a = (end - start);
        System.out.println(a);
        System.out.println("加密后的字符串为:" + Base64.encodeBase64String(encrypt));
        System.out.println("还原后的字符串为:" + new String(decrypt, "UTF-8"));
    }

    /**
     * RSA加密
     *
     * @param data base64编码过
     * @return
     * @throws Exception
     */
    private static byte[] encrypt1(byte[] data) throws Exception {
        byte[] publicKeyEncoded1 = Base64.decodeBase64(publicKey1);
        RSAPublicKey pubKey1 = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyEncoded1));
        Cipher encryptCipher1 = Cipher.getInstance("RSA");
        encryptCipher1.init(Cipher.ENCRYPT_MODE, pubKey1);
        byte[] bytes = encryptCipher1.doFinal(data);
        return bytes;
    }

    /**
     * RSA解密
     */
    public static byte[] decrypt1(byte[] data) throws Exception {
        byte[] privateKeyDecoded1 = Base64.decodeBase64(privateKey1);
        RSAPrivateKey priKey1 = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyDecoded1));
        Cipher decryptCipher1 = Cipher.getInstance("RSA");
        decryptCipher1.init(Cipher.DECRYPT_MODE, priKey1);

        byte[] bytes = decryptCipher1.doFinal(data);
        return bytes;
    }


    /**
     * RSA加密
     *
     * @param data base64编码过
     * @return
     * @throws Exception
     */
    public static byte[] encrypt2(byte[] data) throws Exception {
        byte[] publicKeyEncoded2 = Base64.decodeBase64(publicKey2);
        RSAPublicKey pubKey2 = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyEncoded2));
        Cipher encryptCipher2 = Cipher.getInstance("RSA");
        encryptCipher2.init(Cipher.ENCRYPT_MODE, pubKey2);
        byte[] bytes = encryptCipher2.doFinal(data);
        return bytes;
    }

    /**
     * RSA解密
     */
    private static byte[] decrypt2(byte[] data) throws Exception {
        byte[] privateKeyDecoded2 = Base64.decodeBase64(privateKey2);
        RSAPrivateKey priKey2 = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyDecoded2));
        Cipher decryptCipher2 = Cipher.getInstance("RSA");
        decryptCipher2.init(Cipher.DECRYPT_MODE, priKey2);
        byte[] bytes = decryptCipher2.doFinal(data);
        return bytes;
    }

    /**
     * RSA加密
     *
     * @param data base64编码过
     * @return
     * @throws Exception
     */
    public static byte[] encrypt3(byte[] data) throws Exception {
        byte[] publicKeyEncoded3 = Base64.decodeBase64(publicKey3);
        RSAPublicKey pubKey3 = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyEncoded3));
        Cipher encryptCipher3 = Cipher.getInstance("RSA");
        encryptCipher3.init(Cipher.ENCRYPT_MODE, pubKey3);
        byte[] bytes = encryptCipher3.doFinal(data);
        return bytes;
    }

    /**
     * RSA解密
     */
    public static byte[] decrypt3(byte[] data) throws Exception {
        byte[] privateKeyDecoded3 = Base64.decodeBase64(privateKey3);
        RSAPrivateKey priKey3 = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyDecoded3));
        Cipher decryptCipher3 = Cipher.getInstance("RSA");
        decryptCipher3.init(Cipher.DECRYPT_MODE, priKey3);
        byte[] bytes = decryptCipher3.doFinal(data);
        return bytes;
    }
}
