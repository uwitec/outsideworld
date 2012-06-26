package com.download;

import java.util.List;
import com.model.FieldConstant;
import com.model.TableConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.thread.ThreadPool;
import com.util.MongoUtil;

public class Engine {

    private DownLoad downLoad;
    private ThreadPool downloadPool;
    private MongoUtil mongoDB;

    public void excute() throws Exception {
        while (true) {
            List<BasicDBObject> storys = pollReadyToDownLoad(100);
            if (storys == null || storys.size() <= 0) {
                Thread.sleep(1000 * 60 * 60);
            }
            for (BasicDBObject story : storys) {
                downloadPool.run(new DownLoadThread(downLoad, story));
            }
        }
    }

    private List<BasicDBObject> pollReadyToDownLoad(int num) throws Exception {
        DBObject query = new BasicDBObject();
        query.put(FieldConstant.ISDOWNLOAD, false);
        List<BasicDBObject> objects = mongoDB.pollByPage(TableConstant.TABLESTORY, num, query);
        return objects;
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

    public MongoUtil getMongoDB() {
        return mongoDB;
    }

    public void setMongoDB(MongoUtil mongoDB) {
        this.mongoDB = mongoDB;
    }
}
