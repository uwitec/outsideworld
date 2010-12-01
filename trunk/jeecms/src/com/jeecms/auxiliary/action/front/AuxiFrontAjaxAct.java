package com.jeecms.auxiliary.action.front;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeecms.auxiliary.AuxiAjaxAction;
import com.jeecms.auxiliary.entity.Msg;
import com.jeecms.auxiliary.entity.MsgCtg;
import com.jeecms.auxiliary.manager.MsgMng;
import com.jeecms.common.util.ComUtils;
import com.jeecms.common.util.StrUtils;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 辅助系统前台AJAX
 * 
 * @author liufang
 * 
 */
@Scope("prototype")
@Controller("auxiliary.auxiFrontAjaxAct")
public class AuxiFrontAjaxAct extends AuxiAjaxAction {
	private static final Logger log = LoggerFactory
			.getLogger(AuxiFrontAjaxAct.class);

	public String msgSave() {
		boolean isHuman = imageCaptchaService.validateResponseForID(contextPvd
				.getSessionId(false), checkCode);
		if (!isHuman) {
			jsonRoot.put("success", false);
			jsonRoot.put("msg", "验证码错误！");
			return SUCCESS;
		}
		Msg msg = new Msg();
		msg.setCtg(ctg);
		log.debug("留言标题：{}", title);
		msg.setTitle(StringEscapeUtils.escapeHtml(title));
		log.debug("留言标题escape：{}", msg.getTitle());
		content = StrUtils.getCn(content, getConfig().getMsgMaxSize());
		log.debug("留言内容：{}", content);
		msg.setContentMember(StrUtils.txt2htm(content));
		log.debug("留言内容html转换：{}", msg.getContentMember());

		msg.setWebsite(getWeb());
		msg.setMember(getMember());
		msg.setCreateTime(ComUtils.now());
		msg.setCheck(false);
		msg.setRecommend(false);
		msg.setDisabled(false);
		msg.setIp(contextPvd.getRemoteIp());
		msgMng.save(msg);
		boolean check = getConfig().getMsgNeedCheck();
		jsonRoot.put("success", true);
		jsonRoot.put("isNeedCheck", check);
		if (check) {
			jsonRoot.put("msg", "留言成功，但需要管理员审核才能显示。");
		} else {
			jsonRoot.put("msg", "留言成功！");
		}
		return SUCCESS;
	}

	private String title;
	private String content;
	private MsgCtg ctg;

	private String checkCode;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private MsgMng msgMng;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgCtg getCtg() {
		return ctg;
	}

	public void setCtg(MsgCtg ctg) {
		this.ctg = ctg;
	}
}