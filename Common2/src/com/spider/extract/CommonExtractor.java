package com.spider.extract;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import com.entity.Element;
import com.entity.Template;
import com.model.Item;
import com.model.Page;

public class CommonExtractor {

    private HtmlCleaner htmlCleaner;

    public Item extract(Page page, List<Template> templates) throws Exception {
        if (templates != null && templates.size() > 0) {
            String url = page.getUrl().toString();
            for (Template template : templates) {
                if (template.match(url)) {
                    return extract(page, template);
                }
            }
        }
        return null;
    }

    private Item extract(Page page, Template template) throws Exception {
        if (template.getElements() != null && template.getElements().size() > 0) {
            Item item = new Item();
            item.setUrl(page.getUrl().toString());
            item.setDate(new Date());
            item.setType(template.getType());
            for (Element element : template.getElements()) {
                String elementStr = extract(page, element);
                if (StringUtils.isBlank(elementStr)) {
                    item.addField(element.getName(), elementStr);
                }
            }
            return item;
        }
        return null;
    }

    private String extract(Page page, Element element) throws Exception {
        TagNode root = null;
        /**
         * 表明是第一次解析这个页面
         */
        if (page.getDoc() == null) {
            if (!StringUtils.isBlank(page.getHtml())) {
                root = htmlCleaner.clean(page.getHtml());
            }
        } else {
            root = page.getDoc();
        }
        if (root != null) {
            Object[] objs = root.evaluateXPath(element.getDefine());
            if (objs != null && objs.length > 0) {
                String text = "";
                for (Object obj : objs) {
                    if (obj instanceof TagNode) {
                        text = extractTxt((TagNode) obj);
                        if (!text.trim().isEmpty()) {
                            break;
                        }
                    } else if (obj instanceof String) {
                        text = obj.toString();
                        break;
                    }
                }
                return text;
            }
        }
        return "";
    }
    
    @SuppressWarnings("unchecked")
    private String extractTxt(TagNode node) {
        List<TagNode> children = node.getAllElementsList(true);
        for (TagNode child : children) {
            if (child.getName().equalsIgnoreCase("script")) {
                child.removeAllChildren();
                node.removeChild(child);
            }
        }
        return node.getText().toString();
    }

    public HtmlCleaner getHtmlCleaner() {
        return htmlCleaner;
    }

    public void setHtmlCleaner(HtmlCleaner htmlCleaner) {
        this.htmlCleaner = htmlCleaner;
    }
}
