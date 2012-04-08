package test.extract;

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

import com.model.Item;

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

	public void fetch(Item item) throws Exception {
		String url = item.getUrl();
		LOG.debug("Get Content from {}",url);

		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);

		/* Status Filter */
		int statusCode = response.getStatusLine().getStatusCode();
		LOG.debug("Response Status {} from {}", statusCode, url);
		if (statusCode != 200) {
			LOG.debug("Illegal Status and Skip URL {}", url);
		}

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String content = null;

			/* Detect Actual Encoding */
			String detectContent = null;
			String actualEncoding = null;
			InputStream ins = entity.getContent();
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[10];
			int length = 0;
			int n = -1;
			boolean detected = false;
			for (; (n = ins.read(b)) != -1;) {
				length += n;
				out.append(new String(b, 0, n));
				detectContent = out.toString();
				actualEncoding = getMetaCharset(detectContent);
				if (detected) {
					break;
				}
				if (detected || actualEncoding != null || length > 500) {
					detected = true;
				}
			}
			/* Get Content */
			if (n == -1) {
				content = detectContent;
			} else if (actualEncoding != null) {
				content = detectContent
						+ EntityUtils.toString(entity, actualEncoding);
			} else {
				content = detectContent + EntityUtils.toString(entity);
			}
			item.setEncoding(actualEncoding);
			item.setRawData(content.getBytes(actualEncoding));
			LOG.debug("Fetch Content Successfully from: {} ", url);
		} else {
			throw new Exception();
		}
	}

	private String getMetaCharset(String content) {
		String regEx = "<meta[^>]*?charset=['\"\\s]*(.*?)['\"\\s>]+";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
}
