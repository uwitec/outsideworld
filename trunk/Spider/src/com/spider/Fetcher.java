package com.spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.model.Page;

public class Fetcher {

	private static final Logger LOG = LoggerFactory.getLogger(Fetcher.class);

	private DefaultHttpClient httpclient;

	public Fetcher() {
		LOG.info("Init Fetcher...");
		httpclient = new DefaultHttpClient();

		/* GZip Intercepter */
		httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(final HttpResponse response,
					final HttpContext context) throws HttpException,
					IOException {
				HttpEntity entity = response.getEntity();
				Header ceheader = entity.getContentEncoding();
				if (ceheader != null) {
					HeaderElement[] codecs = ceheader.getElements();
					for (int i = 0; i < codecs.length; i++) {
						if (codecs[i].getName().equalsIgnoreCase("gzip")) {
							response.setEntity(new GzipDecompressingEntity(
									response.getEntity()));
							return;
						}
					}
				}
			}

		});
		LOG.info("Successfully init Fetcher...");
	}

	public void fetch(Page page) {
		String url = page.getUrl().toString();

		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (Exception e) {
			LOG.error("Get Response from {} error", url);
			return;
		}

		/* Status Filter */
		int statusCode = response.getStatusLine().getStatusCode();
		LOG.debug("Response Status {} from {}", statusCode, url);
		if (statusCode != 200) {
			LOG.debug("Illegal Status and Skip URL {}", url);
			return;
		}

		HttpEntity entity = response.getEntity();
		if (entity == null) {
			LOG.error("Get empty entity from {}", url);
			return;
		}
		try {
			String html = null;

			/* Detect Actual Encoding */
			String actualEncoding = null;
			InputStream ins = entity.getContent();
			byte[] b = new byte[500];
			int n = ins.read(b);
			actualEncoding = getMetaCharset(b);

			/* Get Content */
			if (n == -1) {
				html = "";
			} else if (n < 500 && actualEncoding == null) {
				html = new String(b, 0, n);
			} else if (n < 500 && actualEncoding != null) {
				html = new String(b, 0, n, actualEncoding);
			} else if (actualEncoding != null) {
				html = new String(b, actualEncoding)
						+ EntityUtils.toString(entity, actualEncoding);
			} else {
				html = new String(b) + EntityUtils.toString(entity);
			}
			page.setHtml(html);
			LOG.debug("Fetch content successfully from: {}", url);
		} catch (Exception e) {
			LOG.error("Fetch from {} error", url);
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				// Ignore
			}
		}
	}

	private String getMetaCharset(byte[] content) {
		String regEx = "<meta[^>]*?charset=['\"\\s]*(.*?)['\"\\s>]+";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(new String(content));
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
}
