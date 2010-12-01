package com.jeecms.core;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.common.struts2.ContextPvd;
import com.jeecms.common.struts2.interceptor.DomainNameAware;
import com.jeecms.core.entity.Website;
import com.jeecms.core.manager.WebsiteMng;
import com.opensymphony.xwork2.Action;

/**
 * 前台Action基类
 * 
 * @author liufang
 * 
 */
public class FrontAction implements Action {
	private static final Logger log = LoggerFactory
			.getLogger(FrontAction.class);

	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 获得根站点ID
	 * 
	 * @return
	 */
	public Long getRootWebId() {
		return getRootWeb().getId();
	}

	/**
	 * 获得根站点
	 * 
	 * @return
	 */
	public Website getRootWeb() {
		return getWeb().getRootWeb();
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
		String domain = (String) contextPvd
				.getRequestAttr(DomainNameAware.DOMAIN_NAME);
		Website website = websiteMng.getWebsite(domain);
		if (website == null) {
			// @ TODO 转发到一个友好的页面
			throw new RuntimeException("不存在与该域名匹配的站点："
					+ ServletActionContext.getRequest().getAttribute(
							DomainNameAware.DOMAIN_NAME));
		}
		return website;
	}

	/**
	 * /WEB-INF/user_base/ponyjava_com_www/template
	 * 
	 * @return
	 */
	public String getTplBase() {
		return getWeb().getTplRoot().toString();
	}

	/**
	 * /WEB-INF/user_base/
	 * 
	 * @return
	 */
	public String getUserBase() {
		return Website.USER_ROOT;
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public Date getCurrentTime() {
		if (currentTime == null) {
			currentTime = new Date();
		}
		return currentTime;
	}

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
	 * 直接输出字符串GBK编码.
	 */
	protected String renderHtmlGBK(String text) {
		return render(text, "text/html;charset=GBK");
	}

	/**
	 * 获得带.的后缀，如：.htm
	 * 
	 * @return
	 */
	public String getPointSuffix() {
		return "." + getWeb().getSuffix();
	}

	protected Date currentTime;
	protected String pageLink;
	protected String pageSuffix;
	protected String tplPath;
	@Autowired
	protected WebsiteMng websiteMng;
	@Autowired
	protected ContextPvd contextPvd;

	public String getTplPath() {
		return tplPath;
	}

	public void setTplPath(String tplPath) {
		this.tplPath = tplPath;
	}

	public String getPageLink() {
		return pageLink;
	}

	public String getPageSuffix() {
		return pageSuffix;
	}
}
