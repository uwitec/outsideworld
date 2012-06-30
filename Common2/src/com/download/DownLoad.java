package com.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.model.FieldConstant;
import com.model.TableConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;

public class DownLoad {

    private MongoUtil mongoDB;

    

	public boolean download(DBObject object) {
        /**
         * 如果根本没有thubm
         */
        if(StringUtils.isBlank((String)object.get(FieldConstant.THUBM))){
            DBObject query = new BasicDBObject();
            query.put(FieldConstant.ID, new ObjectId(object.get(FieldConstant.ID).toString()));
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
    		System.out.print(directory.mkdirs());
    	}
        String fileName = fileName(object);
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        try {
        	String thubm = (String)object.get(FieldConstant.THUBM);
        	if(!StringUtils.startsWithIgnoreCase(thubm,"http")){
        		String url = (String)object.get(FieldConstant.URL);
        		String host = new URL(url).getHost();
        		thubm="http://"+host+"/"+thubm;
        	}
            download(thubm,fileName);
            DBObject query = new BasicDBObject();
            query.put(FieldConstant.ID, new ObjectId(object.get(FieldConstant.ID).toString()));
            DBObject value = new BasicDBObject();
            value.put("$set", new BasicDBObject().append(FieldConstant.ISDOWNLOAD, true).append(FieldConstant.PATH, fileName));
            mongoDB.update(query,value, TableConstant.TABLESTORY);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String fileName(DBObject object) {
        if (StringUtils.isBlank(object.get(FieldConstant.ID).toString()) || StringUtils.isBlank((String)object.get(FieldConstant.CHANNEL))||StringUtils.isBlank((String)object.get(FieldConstant.FORMAT))) {
            return "";
        }
        String postfix = "";
        int index = -1;
        if((index = StringUtils.lastIndexOf((String)object.get(FieldConstant.THUBM), "."))>0){
        	postfix = ((String)object.get(FieldConstant.THUBM)).substring(index);
        }
        return (String)object.get(FieldConstant.CHANNEL) + File.separator + (String)object.get(FieldConstant.FORMAT)+File.separator+object.get(FieldConstant.ID).toString()+postfix;
    }

    private void download(String urlstr, String fileName) throws Exception {
        URL url = new URL(urlstr);
        	HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        	//String cookie = connection.getHeaderField("Set-Cookie");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:13.0) Gecko/20100101 Firefox/13.0.1");
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream is = connection.getInputStream();
        File file = new File(fileName);
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        is.close();
        connection.disconnect();
    }
    
    public MongoUtil getMongoDB() {
		return mongoDB;
	}

	public void setMongoDB(MongoUtil mongoDB) {
		this.mongoDB = mongoDB;
	}
}
