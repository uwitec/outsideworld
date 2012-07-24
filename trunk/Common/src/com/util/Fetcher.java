package com.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import com.model.Item;

public class Fetcher {
	private boolean found = false;
	private String result = "";
	private int lang;

	@SuppressWarnings("deprecation")
	public void fetch(Item item) throws Exception {
		if (item == null || StringUtils.isBlank(item.getUrl())) {
			return;
		}
		HttpGet httpget = new HttpGet(item.getUrl());
		HttpContext context = new BasicHttpContext();
		// 执行get方法
		try {
			HttpResponse response = HttpUtil.getHttpClient().execute(httpget,
					context);
			// 获得字符串
			HttpEntity entity = response.getEntity();
			String contentType = entity.getContentType().getValue();
			if (contentType != null && !contentType.contains("text")
					&& !contentType.contains("html")) {
				return;
			}
			byte[] bytes = EntityUtils.toByteArray(entity);
			String encoding = getEncoding(entity, bytes);
			if (entity != null) {
				item.setRawData(bytes);
				item.setEncoding(encoding);
				entity.consumeContent();
			}

		} catch (Exception e) {
			httpget.abort();
			throw new Exception(e);
		}
	}

	/**
	 * 获取编码，根据content-type
	 * 
	 * @param entity
	 * @return
	 */
	private String getEncoding(HttpEntity entity, byte[] content)
			throws Exception {
		String charset = "";
		Header header = entity.getContentType();
		if (header != null) {
			String s = header.getValue();
			if (matcher(s, "(charset)\\s?=\\s?(utf-?8)")) {
				charset = "utf-8";
			} else if (matcher(s, "(charset)\\s?=\\s?(gbk)")) {
				charset = "gbk";

			} else if (matcher(s, "(charset)\\s?=\\s?(gb2312)")) {
				charset = "gb2312";
			}
		}
		// 如果根本没有返回编码，则启动编码的自动检测功能
		charset = guestCharSet(content)[0];
		return charset;

	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	private boolean matcher(String s, String pattern) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				+ Pattern.UNICODE_CASE);
		Matcher matcher = p.matcher(s);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 自动检测编码，使用jcharset
	 * 
	 * @param input
	 * @return
	 */
	private String[] guestCharSet(byte[] in) throws Exception {
		lang = nsPSMDetector.CHINESE;
		String[] prob;
		nsDetector det = new nsDetector(lang);
		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				found = true;
				result = charset;
			}
		});
		BufferedInputStream imp = new BufferedInputStream(
				new ByteArrayInputStream(in));
		byte[] buf = new byte[1024];
		int len;
		boolean isAscii = true;
		while ((len = imp.read(buf, 0, buf.length)) != -1) {
			if (isAscii)
				isAscii = det.isAscii(buf, len);
			if (!isAscii) {
				if (det.DoIt(buf, len, false))
					break;
			}
		}
		imp.close();
		// in.close();
		det.DataEnd();
		if (isAscii) {
			found = true;
			prob = new String[] { "ASCII" };
		} else if (found) {
			prob = new String[] { result };
		} else {
			prob = det.getProbableCharsets();
		}
		return prob;
	}
}
