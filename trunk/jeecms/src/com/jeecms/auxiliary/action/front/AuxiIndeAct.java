package com.jeecms.auxiliary.action.front;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeecms.auxiliary.AuxiIndeAction;
import com.jeecms.auxiliary.entity.MsgCtg;
import com.jeecms.auxiliary.entity.VoteTopic;
import com.jeecms.auxiliary.exception.VoteException;
import com.jeecms.auxiliary.manager.MsgCtgMng;
import com.jeecms.auxiliary.manager.VoteTopicMng;

/**
 * 留言板独立模板
 * 
 * @author liufang
 * 
 */
@Scope("prototype")
@Controller("auxiliary.auxiIndeAct")
public class AuxiIndeAct extends AuxiIndeAction {
	public String guestbook() {
		if (!getConfig().getMsgIsOpen()) {
			addActionMessage("留言板已关闭");
			return showMessage();
		}
		// TODO 使用标签获得留言列表，以便在任何页面制作留言板。(比较困难，甚至不可能)
		ctgList = msgCtgMng.getList(getWebId());
		return handleResult("Guestbook");
	}

	public String voteResult() {
		Cookie cookie = contextPvd.getCookie(getWeb().getCookieKey());
		String cookieValue = null;
		if (cookie != null) {
			cookieValue = cookie.getValue();
		}
		try {
			bean = voteTopicMng.vote(topicId, voteItems, getMemberId(),
					contextPvd.getRemoteIp(), cookieValue);
		} catch (VoteException e) {
			error = e.getMessage();
		}
		return handleResult("VoteResult");
	}

	@Autowired
	private MsgCtgMng msgCtgMng;
	private List<MsgCtg> ctgList;

	@Autowired
	private VoteTopicMng voteTopicMng;
	private VoteTopic bean;
	private Long topicId;
	private Long[] voteItems;
	private String error;

	public List<MsgCtg> getCtgList() {
		return ctgList;
	}

	public VoteTopic getBean() {
		return bean;
	}

	public void setBean(VoteTopic bean) {
		this.bean = bean;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long[] getVoteItems() {
		return voteItems;
	}

	public void setVoteItems(Long[] voteItems) {
		this.voteItems = voteItems;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}