package com.download;

import com.mongodb.DBObject;


public class DownLoadThread implements Runnable {
    private DownLoad download;
    private DBObject story;
    
    
    public DownLoadThread(DownLoad download, DBObject story) {
        this.download = download;
        this.story = story;
    }
    
    @Override
    public void run() {
        download.download(story);
    }
}
