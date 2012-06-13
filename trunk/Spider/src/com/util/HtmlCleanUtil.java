package com.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class HtmlCleanUtil {

    private static HtmlCleaner htmlCleaner = new HtmlCleaner();

    public static String parse(String html, String xpath, String regx) throws Exception {
        TagNode node = htmlCleaner.clean(html);
        Object[] nodes = node.evaluateXPath(xpath);
        if (nodes != null && nodes.length > 0) {
            return extractTxt((TagNode) nodes[0], regx);
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    public static String extractTxt(TagNode node, String regx) {
        if (!StringUtils.isBlank(regx)) {
            Pattern pattern = Pattern.compile(regx);
            String text = node.getText().toString();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()){
                return matcher.group();
            }
            else{
                return "";
            }
        } else {
            List<TagNode> children = node.getAllElementsList(true);
            for (TagNode child : children) {
                if (child.getName().equalsIgnoreCase("script")) {
                    child.removeAllChildren();
                    node.removeChild(child);
                }
            }
            return node.getText().toString();
        }
    }
}
