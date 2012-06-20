package com.qq.group;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import com.dao.ItemDao;
import com.model.Item;
import com.model.policy.QQGroupInfo;
import com.util.HttpUtil;
import com.util.SpringFactory;

public class PollGroupMessage extends Thread {
	private Map<String, String> param;
	private ItemDao itemDao = SpringFactory.getBean("itemDao");
	private Set<QQGroupInfo> groups;

	public PollGroupMessage(Map<String, String> param,Set<QQGroupInfo> groups) {
		this.param = param;
		this.groups = groups;
	}

	@Override
	public void run() {
	    Map<String,String> newParam = new HashMap<String,String>();
		String pollUrl = "http://d.web2.qq.com/channel/poll2";
		String clientid =param.get("clientid");
		String psessionid = param.get("psessionid");
		String r = "{\"clientid\":\"" + clientid + "\",\"psessionid\":\""+psessionid+"\",\"key\":0,\"ids\":[]}";
		newParam.put("clientid", clientid);
		newParam.put("psessionid", psessionid);
		
		System.out.println(r);
		while (true) {
			try {
			    r = URLEncoder.encode(r, "UTF-8");
			    newParam.put("r", r);
			    param.put("r", r);
				String ret = HttpUtil.doPost(pollUrl, "GBK", newParam, null);
				System.out.println(ret);
				JSONObject retJ = new JSONObject(ret);
				int retcode = retJ.getInt("retcode");
				if (retcode == 0) {
					JSONArray result = retJ.getJSONArray("result");
					String poll_type = result.getJSONObject(0).getString(
							"poll_type");
					JSONObject value = result.getJSONObject(0).getJSONObject(
							"value");
					if ("message".equals(poll_type)) {// 好友消息
					} else if ("buddies_status_change".equals(poll_type)) {// 好友上下线
					} else if ("group_message".equals(poll_type)) {// 群消息
						System.out.println(value);
						Item item = transToItem(value);
						if(item!=null){
						itemDao.insert(item);
						}
					}
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("Response PollMessage failure = "
						+ e.getMessage());
			}

		}
	}

	private Item transToItem(JSONObject jsonValue) throws JSONException {
	    String sourceId = jsonValue.get("group_code").toString();
	    for(QQGroupInfo group:groups){
	        if(StringUtils.equals(sourceId, group.getGroupCode())){
	            Item item = new Item();
	            String content = jsonValue.getJSONArray("content").get(1).toString();
	            item.setContent(content);
	            item.setType("QQ");
	            item.setSourceId(sourceId);
	            item.setCrawlTime(new Date());
	            return item;
	        }
	    }
		return null;
	}

    public Set<QQGroupInfo> getGroups() {
        return groups;
    }

    public void setGroups(Set<QQGroupInfo> groups) {
        this.groups = groups;
    }
}
