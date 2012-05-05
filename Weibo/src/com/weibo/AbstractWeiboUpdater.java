package com.weibo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dao.ItemDao;
import com.dao.mongo.ItemDaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public abstract class AbstractWeiboUpdater implements Runnable {

	private static Logger log = Logger.getLogger(AbstractWeiboUpdater.class);

	private ItemDao itemDAO = new ItemDaoImpl();

	private String source;
	private int chunk;

	public AbstractWeiboUpdater(String source, int chunk, String params[]) {
		this.source = source;
		this.chunk = chunk;
	}

	@Override
	public void run() {
		log.info(source + " Weibo Updater start to running...");
		try {
			log.info("try to login " + source);
			login();
			log.info("successfully login " + source);
		} catch (Exception e) {
			log.error("can not login " + source, e);
		}
		while (true) {
			DBObject sample = new BasicDBObject();
			sample.put("type", "weibo");
			sample.put("source", source);
			// TODO 时间范围
			try {
				DBCursor cursor = itemDAO.find(sample);
				int i = 0;
				List<String> list = new ArrayList<String>(chunk);
				List<Object[]> result = null;
				while (cursor.hasNext()) {
					DBObject s = cursor.next();
					String id = s.get("url").toString();
					list.add(id);
					i++;
					if (i % chunk == 0) {
						result = getStatus(list);
						update(result);
						list.clear();
					}
				}
				cursor.close();
				result = getStatus(list);
				update(result);
				list.clear();
			} catch (NeedLoginException e) {
				try {
					log.info("try to login " + source);
					login();
					log.info("successfully login " + source);
				} catch (Exception ex) {
					log.error("can not login " + source, ex);
				}
			} catch (Exception e) {
				log.error("update " + source + " weibo error", e);
			}
		}
	}

	/* 更新微博评论和转发次数 */
	public void update(List<Object[]> list) throws Exception {
		DBObject object = new BasicDBObject();
		for (Object[] obj : list) {
			object.put("url", obj[0]);
			object.put("replyNum", obj[1]);
			object.put("transNum", obj[2]);
			itemDAO.update(object);
		}
	}

	/* 登录 */
	public abstract void login() throws Exception;

	/* 取得微博评论和转发次数 */
	public abstract List<Object[]> getStatus(List<String> list)
			throws NeedLoginException, Exception;
}
