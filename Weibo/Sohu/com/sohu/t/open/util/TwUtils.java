package com.sohu.t.open.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TwUtils {
	public static String encode(String str) throws Exception {
		return encode(str, "utf-8");
	}

	public static String encode(String str, String charset) throws Exception {
		if (charset == null || charset.trim().equals("")) {
			charset = "utf-8";
		}
		return URLEncoder.encode(str, charset);
	}

	public static String escape(String str) {
		return str.replaceAll("\\+", "%20").replaceAll("\\*", "%2A")
				.replaceAll("%7E", "~");
	}

	public static String decode(String str) throws Exception {
		return decode(str, "utf-8");
	}

	public static String decode(String str, String charset) throws Exception {
		return URLDecoder.decode(str, charset);
	}

	public static String md5s(String plainText) {
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
			System.out.println("result: " + buf.toString());// 32位的加密
			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return str;

	}
}
