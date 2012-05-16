package com.model.policy;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "meta")
public class Meta {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private String id;
    @Column(nullable = false, length = 200)
    private String url;
    @Column(nullable = false, length = 200)
    private int pageNum;
    @Column(nullable = false, length = 200)
    private int pageFrom = 0;
    @Column(nullable = false, length = 200)
    private String pageName;
    @Column(nullable = false, length = 200)
    private String wordsName;
    private List<MetaParam> metaParam;

    /**
     * 利用meta的信息构建合适的url
     * @param queryWord 输入的查询参数
     * @return
     */
    public List<String> constructUrl(String queryWord) {
        List<String> results = new ArrayList<String>();
        StringBuffer result = new StringBuffer();
        result.append(url).append("?");
        for (MetaParam m : metaParam) {
            result.append(m.getName() + "=");
            String value = m.getValue();
            // 将空白转换成＋
            value.replaceAll("\\s", "+");
            result.append(value);
            result.append("&");
        }
        if (!StringUtils.isBlank(queryWord)) {
            queryWord.replaceAll("\\s", "+");
            result.append(wordsName).append("=").append(queryWord);
        }
        if (!StringUtils.isBlank(pageName)) {
            for (int i = pageFrom; i < pageFrom + pageNum; i++) {
                String temp = result.toString();
                temp += pageName + "=" + i;
                results.add(temp);
            }
        }
        else{
            results.add(result.toString());
        }
        return results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<MetaParam> getMetaParam() {
        return metaParam;
    }

    public void setMetaParam(List<MetaParam> metaParam) {
        this.metaParam = metaParam;
    }

    public String getWordsName() {
        return wordsName;
    }

    public void setWordsName(String wordsName) {
        this.wordsName = wordsName;
    }

    public int getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(int pageFrom) {
        this.pageFrom = pageFrom;
    }
}
