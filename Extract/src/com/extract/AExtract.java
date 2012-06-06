package com.extract;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.model.ParsedHtml;
import com.model.policy.Element;
import com.model.policy.Element.ElementType;
import com.model.policy.Template;
import com.util.CssUtil;
import com.util.XPathUtil;

public abstract class AExtract implements Extract {

	protected String extract(String field, Item item) throws Exception {
		Template template = item.getTemplate();
		if (template == null) {
			return "";
		}
		Set<Element> elements = template.getElements();
		for (Element e : elements) {
			if (StringUtils.equals(field, e.getName())) {
				if (StringUtils.isEmpty(e.getRegex())) {
					return getString(item.getParsedHtml(), e);
				} else {
					return parse(getString(item.getParsedHtml(), e),
							e.getRegex());
				}
			}
		}
		return "";
	}

	private String parse(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return value;
		}
	}

	private String getString(ParsedHtml parsedHtml, Element e) throws Exception {
		if (ElementType.XPATH.equals(e.getType())) {
			return XPathUtil.getResult(parsedHtml.getNode(), e.getDefine());
		} else if (ElementType.CSS.equals(e.getType())) {
			return CssUtil.getResult(parsedHtml.getDoc(), e.getDefine());
		}
		return "";
	}

}
