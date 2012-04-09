/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nutch.parse.html;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.Outlink;
import org.apache.nutch.parse.ParseData;
import org.apache.nutch.parse.ParseImpl;
import org.apache.nutch.parse.ParseResult;
import org.apache.nutch.parse.ParseStatus;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.protocol.Content;
import org.apache.nutch.util.EncodingDetector;
import org.apache.nutch.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.extract.Extract;
import com.model.Item;
import com.model.OUrl;
import com.util.SpringFactory;

public class MyHtmlParser implements Parser {
	public static final Logger LOG = LoggerFactory
			.getLogger("org.apache.nutch.parse.html");

	// I used 1000 bytes at first, but found that some documents have
	// meta tag well past the first 1000 bytes.
	// (e.g. http://cn.promo.yahoo.com/customcare/music.html)
	private static final int CHUNK_SIZE = 2000;

	// NUTCH-1006 Meta equiv with single quotes not accepted
	private static Pattern metaPattern = Pattern.compile(
			"<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
			Pattern.CASE_INSENSITIVE);
	private static Pattern charsetPattern = Pattern.compile(
			"charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);

	/**
	 * Given a <code>byte[]</code> representing an html file of an
	 * <em>unknown</em> encoding, read out 'charset' parameter in the meta tag
	 * from the first <code>CHUNK_SIZE</code> bytes. If there's no meta tag for
	 * Content-Type or no charset is specified, <code>null</code> is returned. <br />
	 * FIXME: non-byte oriented character encodings (UTF-16, UTF-32) can't be
	 * handled with this. We need to do something similar to what's done by
	 * mozilla
	 * (http://lxr.mozilla.org/seamonkey/source/parser/htmlparser/src/nsParser
	 * .cpp#1993). See also http://www.w3.org/TR/REC-xml/#sec-guessing <br />
	 * 
	 * @param content
	 *            <code>byte[]</code> representation of an html file
	 */

	private static String sniffCharacterEncoding(byte[] content) {
		int length = content.length < CHUNK_SIZE ? content.length : CHUNK_SIZE;

		// We don't care about non-ASCII parts so that it's sufficient
		// to just inflate each byte to a 16-bit value by padding.
		// For instance, the sequence {0x41, 0x82, 0xb7} will be turned into
		// {U+0041, U+0082, U+00B7}.
		String str = "";
		try {
			str = new String(content, 0, length, Charset.forName("ASCII")
					.toString());
		} catch (UnsupportedEncodingException e) {
			// code should never come here, but just in case...
			return null;
		}

		Matcher metaMatcher = metaPattern.matcher(str);
		String encoding = null;
		if (metaMatcher.find()) {
			Matcher charsetMatcher = charsetPattern.matcher(metaMatcher
					.group(1));
			if (charsetMatcher.find())
				encoding = new String(charsetMatcher.group(1));
		}

		return encoding;
	}

	private String defaultCharEncoding;

	private Configuration conf;

	public ParseResult getParse(Content content) {
		Outlink[] outlinks = new Outlink[0];
		Metadata metadata = new Metadata();

		Item item = new Item();
		try {
			byte[] contentInOctets = content.getContent();
			EncodingDetector detector = new EncodingDetector(conf);
			detector.autoDetectClues(content, true);
			detector.addClue(sniffCharacterEncoding(contentInOctets), "sniffed");
			String encoding = detector.guessEncoding(content,
					defaultCharEncoding);

			metadata.set(Metadata.ORIGINAL_CHAR_ENCODING, encoding);
			metadata.set(Metadata.CHAR_ENCODING_FOR_CONVERSION, encoding);

			item.setRawData(content.getContent());
			item.setEncoding(encoding);
			item.setUrl(content.getUrl());
			if (LOG.isTraceEnabled()) {
				LOG.trace("Parsing...");
			}
			Extract extract = (Extract) SpringFactory.getBean("extractChain");
			extract.process(item);
			outlinks = new Outlink[item.getOurls().size()];
			int i = 0;
			for (OUrl ourl : item.getOurls()) {
				Outlink outlink = new Outlink(ourl.getUrl(), ourl.getAuthor());
				outlinks[i] = outlink;
				i++;
			}
			if (LOG.isTraceEnabled()) {
				LOG.trace("found " + outlinks.length + " outlinks in "
						+ content.getUrl());
			}
			// ItemDao itemDao = (ItemDao)SpringFactory.getBean("itemDao");
			// itemDao.insert(item);
		} catch (IOException e) {
			return new ParseStatus(e).getEmptyParseResult(content.getUrl(),
					getConf());
		} catch (DOMException e) {
			return new ParseStatus(e).getEmptyParseResult(content.getUrl(),
					getConf());
		} catch (SAXException e) {
			return new ParseStatus(e).getEmptyParseResult(content.getUrl(),
					getConf());
		} catch (Exception e) {
			e.printStackTrace(LogUtil.getWarnStream(LOG));
			return new ParseStatus(e).getEmptyParseResult(content.getUrl(),
					getConf());
		}

		ParseStatus status = new ParseStatus(ParseStatus.SUCCESS);
		ParseData parseData = new ParseData(status, item.getTitle(), outlinks,
				content.getMetadata(), metadata);
		ParseResult parseResult = ParseResult.createParseResult(
				content.getUrl(),
				new ParseImpl(item.getPageString(), parseData));
		return parseResult;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
		this.defaultCharEncoding = getConf().get(
				"parser.character.encoding.default", "windows-1252");
	}

	public Configuration getConf() {
		return this.conf;
	}
}
