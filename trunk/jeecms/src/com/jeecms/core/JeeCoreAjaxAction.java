package com.jeecms.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.common.struts2.ContextPvd;
import com.jeecms.common.struts2.interceptor.DomainNameAware;
import com.jeecms.core.entity.Admin;
import com.jeecms.core.entity.Member;
import com.jeecms.core.entity.User;
import com.jeecms.core.entity.Website;
import com.jeecms.core.manager.AdminMng;
import com.jeecms.core.manager.MemberMng;
import com.jeecms.core.manager.UserMng;
import com.jeecms.core.manager.WebsiteMng;
import com.opensymphony.xwork2.Action;

public class JeeCoreAjaxAction implements Action, DomainNameAware {
	private static final Logger log = LoggerFactory
			.getLogger(JeeCoreAjaxAction.class);

	/**
	 * 绕过Template,直接输出内容的简便函数.
	 */
	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 直接输出字符串.
	 */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出HTML.
	 */
	protected String renderHtml(String html) {
		return render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出XML.
	 */
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=UTF-8");
	}

	/**
	 * 获得站点ID
	 * 
	 * @return
	 */
	public Long getWebId() {
		return getWeb().getId();
	}

	/**
	 * 获得站点对象
	 * 
	 * @return
	 */
	public Website getWeb() {
		Website website = websiteMng.getWebsite(domainName);
		if (website == null) {
			// @ TODO 转发到一个友好的页面
			throw new RuntimeException("不存在与该域名匹配的站点："
					+ ServletActionContext.getRequest().getAttribute(
							DomainNameAware.DOMAIN_NAME));
		}
		return website;
	}

	/**
	 * 获得用户ID
	 * 
	 * @return
	 */
	public Long getUserId() {
		return (Long) contextPvd.getSessionAttr(User.USER_KEY);
	}

	/**
	 * 获得用户对象
	 * 
	 * @return
	 */
	public User getUser() {
		return userMng.findById(getUserId());
	}

	/**
	 * 获得管理员ID
	 * 
	 * @return
	 */
	public Long getAdminId() {
		return (Long) contextPvd.getSessionAttr(Admin.ADMIN_KEY);
	}

	/**
	 * 获得管理员对象
	 * 
	 * @return
	 */
	public Admin getAdmin() {
		return adminMng.findById(getAdminId());
	}

	/**
	 * 获得会员ID
	 * 
	 * @return
	 */
	public Long getMemberId() {
		return getMember().getId();
	}

	/**
	 * 获得会员对象
	 * 
	 * @return
	 */
	public Member getMember() {
		Long memberId = (Long) contextPvd.getSessionAttr(Member.MEMBER_KEY);
		return memberMng.getLoginMember(getWebId(), getUserId(), memberId);
	}

	protected Map<String, Object> jsonRoot = new HashMap<String, Object>();
	protected String domainName;

	@Autowired
	protected WebsiteMng websiteMng;
	@Autowired
	protected ContextPvd contextPvd;
	@Autowired
	protected UserMng userMng;
	@Autowired
	protected AdminMng adminMng;
	@Autowired
	protected MemberMng memberMng;

	public String execute() throws Exception {
		return SUCCESS;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Map<String, Object> getJsonRoot() {
		return jsonRoot;
	}

	public void setJsonRoot(Map<String, Object> jsonRoot) {
		this.jsonRoot = jsonRoot;
	}
}
