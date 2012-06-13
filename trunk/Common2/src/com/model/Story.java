package com.model;


public class Story {
    private String id;
    /**
     * 描述
     */
    private String description;
    /**
     * 源站点的url
     */
    private String refer;
    /**
     * 最终下载的url
     */
    private String downloadUrlFinal;
    /**
     * 缩略图下载的url
     */
    private String downloadUrl;
    /**
     * 分类
     */
    private String category;
    /**
     * 下载数据保存的路径
     */
    private String path;
    /**
     * 是否已经下载
     */
    private boolean isDownLoad = false;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDownloadUrl() {
        return downloadUrl;
    }
    
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    
    public boolean isDownLoad() {
        return isDownLoad;
    }
    
    public void setDownLoad(boolean isDownLoad) {
        this.isDownLoad = isDownLoad;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getDownloadUrlFinal() {
		return downloadUrlFinal;
	}

	public void setDownloadUrlFinal(String downloadUrlFinal) {
		this.downloadUrlFinal = downloadUrlFinal;
	}
}
