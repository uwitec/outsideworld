package com.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import com.model.FieldConstant;
import com.model.TableConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;
import com.util.SpringFactory;

public class DownLoad {

    private MongoUtil mongoDB = SpringFactory.getBean("mongoDB");

    public boolean download(DBObject object) {
        /**
         * 如果根本没有thubm
         */
        if(StringUtils.isBlank((String)object.get(FieldConstant.THUBM))){
            DBObject query = new BasicDBObject();
            query.put(FieldConstant.ID, new ObjectId((String)object.get(FieldConstant.ID)));
            DBObject value = new BasicDBObject();
            value.put("$set", new BasicDBObject().append(FieldConstant.ISDOWNLOAD, true));
            mongoDB.update(query,value, TableConstant.TABLESTORY);
            return true;
        }
        /**
         * 如果有小样
         */
    	String dir = (String)object.get(FieldConstant.CHANNEL)+File.separator+(String)object.get(FieldConstant.FORMAT);
    	File directory = new File(dir);
    	if(!directory.exists()){
    		directory.mkdir();
    	}
        String fileName = fileName(object);
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        try {
            download((String)object.get(FieldConstant.THUBM),fileName);
            DBObject query = new BasicDBObject();
            query.put(FieldConstant.ID, new ObjectId((String)object.get(FieldConstant.ID)));
            DBObject value = new BasicDBObject();
            value.put("$set", new BasicDBObject().append(FieldConstant.ISDOWNLOAD, true).append(FieldConstant.PATH, fileName));
            mongoDB.update(query,value, TableConstant.TABLESTORY);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String fileName(DBObject object) {
        if (StringUtils.isBlank((String)object.get(FieldConstant.ID)) || StringUtils.isBlank((String)object.get(FieldConstant.CHANNEL))||StringUtils.isBlank((String)object.get(FieldConstant.FORMAT))) {
            return "";
        }
        String postfix = "";
        int index = -1;
        if((index = StringUtils.lastIndexOf((String)object.get(FieldConstant.THUBM), "."))>0){
        	postfix = ((String)object.get(FieldConstant.THUBM)).substring(index);
        }
        return (String)object.get(FieldConstant.CHANNEL) + File.separator + (String)object.get(FieldConstant.FORMAT)+File.separator+(String)object.get(FieldConstant.ID)+postfix;
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
}
