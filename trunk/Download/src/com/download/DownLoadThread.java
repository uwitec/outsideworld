package com.download;

import com.model.Story;


public class DownLoadThread implements Runnable {
    private DownLoad download;
    private Story story;
    @Override
    public void run() {
        download.download(story);
    }
    public DownLoad getDownload() {
        return download;
    }
    public void setDownload(DownLoad download) {
        this.download = download;
    }
    public Story getStory() {
        return story;
    }
    public void setStory(Story story) {
        this.story = story;
    }
}
