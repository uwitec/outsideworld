package com.extract;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.model.Element;
import com.model.Item;
import com.model.ParsedHtml;
import com.model.Template;
import com.util.CssUtil;
import com.util.XPathUtil;

public abstract class AExtract implements Extract {

	protected String extract(String field, Item item) throws Exception {
		Template template = item.getTemplate();
		Set<Element> elements = template.getElements();
		for (Element e : elements) {
			if (StringUtils.equals(field, e.getName())) {
				return getString(item.getParsedHtml(), e);
			}
		}
		return "";
	}

	private String getString(ParsedHtml parsedHtml, Element e) throws Exception {
		if (StringUtils.endsWithIgnoreCase("xpath", e.getType())) {
			return XPathUtil.getResult(parsedHtml.getNode(), e.getDefine());
		} else if (StringUtils.equals("css", e.getType())) {
			return CssUtil.getResult(parsedHtml.getDoc(), e.getDefine());
		}
		return "";
	}

}
