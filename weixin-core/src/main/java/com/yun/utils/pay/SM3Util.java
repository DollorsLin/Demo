package com.yun.utils.pay;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;


/**
 * SM3 工具
 * 
 */
public class SM3Util {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private SM3Util() {
	}

	/**
	 * SM3 摘要结果
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static final String sm3(String input) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		MessageDigest digest = null;
		digest = MessageDigest.getInstance("SM3", "BC");
		byte[] result = digest.digest(input.getBytes("UTF-8"));
		return Hex.toHexString(result);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		System.out.println(sm3("123"));
	}
}
