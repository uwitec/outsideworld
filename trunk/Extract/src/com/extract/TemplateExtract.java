package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.model.policy.Template;
import com.util.TemplateCache;

public class TemplateExtract implements Extract {

	@Override
	public void process(Item item) throws Exception {
		if (StringUtils.isBlank(item.getUrl())) {
			item.setStatus(false);
			return;
		}
		Template template = TemplateCache.getTemplate(item.getUrl());
		if (template == null) {
			item.setStatus(false);
		} else {
			item.setTemplate(template);
			item.setSourceId(String.valueOf(template.getSource().getId()));
		}
	}
}
