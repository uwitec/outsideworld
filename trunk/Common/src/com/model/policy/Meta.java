package com.model.policy;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String pageName;
    private List<MetaParam> metaParam;
    
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
    
}
