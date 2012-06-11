package com.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import com.dao.StoryDao;
import com.model.Story;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DownLoad {

    private StoryDao storyDao;

    public boolean download(Story story) {
        String fileName = fileName(story);
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        try {
            download(story.getDownloadUrl(),fileName);
            DBObject query = new BasicDBObject();
            query.put("_id", story.getId());
            DBObject value = new BasicDBObject();
            value.put("$set", new BasicDBObject().append("isDownLoad", true));
            storyDao.update(query, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String fileName(Story story) {
        if (StringUtils.isBlank(story.getId()) || StringUtils.isBlank(story.getCategory())) {
            return "";
        }
        return story.getCategory() + File.pathSeparator + story.getId();
    }

    private void download(String urlstr, String fileName) throws Exception {
        URL url = new URL(urlstr);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        is.close();
    }

    public StoryDao getStoryDao() {
        return storyDao;
    }

    public void setStoryDao(StoryDao storyDao) {
        this.storyDao = storyDao;
    }
}
