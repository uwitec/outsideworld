package com.download;

import java.util.List;
import com.dao.StoryDao;
import com.model.Story;
import com.thread.ThreadPool;

public class Engine {

    private DownLoad downLoad;
    private ThreadPool downloadPool;
    private StoryDao storyDao;

    public void excute() throws Exception {
        while (true) {
            List<Story> storys = storyDao.pollReadyToDownLoad(100);
            if (storys == null || storys.size()<= 0) {
            	Thread.sleep(1000*60*60);
            }
            for (Story story : storys) {
                downloadPool.run(new DownLoadThread(downLoad, story));
            }
            
        }
    }

    public DownLoad getDownLoad() {
        return downLoad;
    }

    public void setDownLoad(DownLoad downLoad) {
        this.downLoad = downLoad;
    }

    public ThreadPool getDownloadPool() {
        return downloadPool;
    }

    public void setDownloadPool(ThreadPool downloadPool) {
        this.downloadPool = downloadPool;
    }

    public StoryDao getStoryDao() {
        return storyDao;
    }

    public void setStoryDao(StoryDao storyDao) {
        this.storyDao = storyDao;
    }
}
