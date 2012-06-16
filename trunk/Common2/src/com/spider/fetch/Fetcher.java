package com.spider.fetch;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
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

import com.model.Page;
import com.util.HttpUtil;

public class Fetcher {
	private ConcurrentLinkedQueue<Page> extractQueue;
	private Map<String,Long> lastUpdateMap;

	private boolean found = false;
	private String result = "";

	/**
	 * 获取网页信息，填充item中的content字段
	 * 
	 * @param item
	 * @param queue
	 * @throws Exception 
	 * @throws Exception
	 */
	public void fetch(Page page) throws Exception {
		//先要记录一下当前抓取的主机信息是多少小时以前抓取的
		if(page.getSource()!=null){
			String host = new URL(page.getSource().getUrl()).getHost();
			if(lastUpdateMap.get(host)==null){
				lastUpdateMap.put(host, new Date().getTime());
			}
		}
		HttpGet httpget = new HttpGet(page.getUrl().toString());
		HttpContext context = new BasicHttpContext();
		try {
			// 执行get方法
			HttpResponse response = HttpUtil.getHttpClient().execute(httpget,
					context);
			// 获得字符串
			HttpEntity entity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(entity);
			String encoding = getEncoding(entity, bytes);
			if (entity != null) {
				page.setHtml(new String(bytes, encoding));
				extractQueue.add(page);
				entity.consumeContent();
			}

		} catch (Exception e) {
			httpget.abort();
			e.printStackTrace();
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
		if (StringUtils.isBlank(charset)) {
			charset = guestCharSet(content)[0];
		}
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
	private synchronized String[] guestCharSet(byte[] in) throws Exception {
		found = false;
		result = "";
		int lang = nsPSMDetector.CHINESE;
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

	public ConcurrentLinkedQueue<Page> getExtractQueue() {
		return extractQueue;
	}

	public void setExtractQueue(ConcurrentLinkedQueue<Page> extractQueue) {
		this.extractQueue = extractQueue;
	}

	public Map<String,Long> getLastUpdateMap() {
		return lastUpdateMap;
	}

	public void setLastUpdateMap(Map<String,Long> lastUpdateMap) {
		this.lastUpdateMap = lastUpdateMap;
	}
}
