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
import com.spider.filter.DuplicatedUrlFilter;

public class UrlExtractor implements Extractor {

    private HtmlCleaner htmlCleaner;
    private DuplicatedUrlFilter duplicatedUrlFilter;

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
                //2、这里将urls分类，如果是目录页，则使用interval Filter，查看是否过期，如果是抽取页面，则使用bloom过滤器进行
                for(String link:links){
                    for(Template template : templates){
                        if (template.match(link)){
                            //结果页面
                            if(duplicatedUrlFilter.filter(item)){
                                break;
                            }
                        }
                    
                    }
                    //目录页面，查看时间
                }
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
    
    public DuplicatedUrlFilter getDuplicatedUrlFilter() {
        return duplicatedUrlFilter;
    }

    
    public void setDuplicatedUrlFilter(DuplicatedUrlFilter duplicatedUrlFilter) {
        this.duplicatedUrlFilter = duplicatedUrlFilter;
    }
}
