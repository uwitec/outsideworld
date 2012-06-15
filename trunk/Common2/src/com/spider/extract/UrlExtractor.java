package com.spider.extract;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import com.entity.Template;
import com.model.Item;
import com.model.Page;

public class UrlExtractor implements Extractor {

    private HtmlCleaner htmlCleaner;

    @Override
    public Item extract(Page page, List<Template> templates) throws Exception {
        TagNode root;
        if (page.getDoc() == null) {
            if (!StringUtils.isBlank(page.getHtml())) {
                root = htmlCleaner.clean(page.getHtml());
                page.setDoc(root);
                Object[] hrefs = root.evaluateXPath("//a/@href");
                // 1、如果domain匹配的话，则进行提取，否则不进行提取
                List<String> links = new ArrayList<String>(); 
                for (Object href : hrefs) {
                    /* filter invalid link */
                    String link = href.toString();
                    URL url = new URL(link);
                    for (Template template : templates) {
                        if (StringUtils.equals(url.getHost(),template.getDomain())) {
                            links.add(link);
                            break;
                        }
                    }
                }
                //2、因为这里是目录页，所以较验host抓取时间是否已经过了，如果没有过的话，再进行抓取
            }
        }
        return null;
    }

    public HtmlCleaner getHtmlCleaner() {
        return htmlCleaner;
    }

    public void setHtmlCleaner(HtmlCleaner htmlCleaner) {
        this.htmlCleaner = htmlCleaner;
    }
}
