package com.jeecms.core;

import static com.jeecms.core.Constants.SPT;
import static com.jeecms.core.Constants.TPL_DEF_SOLUTION;
import static com.jeecms.core.Constants.TPL_SUFFIX;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.common.struts2.interceptor.ProcessingStartInterceptor;
import com.jeecms.core.entity.Member;
import com.jeecms.core.entity.User;
import com.jeecms.core.manager.MemberMng;
import com.jeecms.core.manager.UserMng;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;

/**
 * 前台完整页面基类
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("unchecked")
public abstract class IntegrityAction extends FrontAction implements
		ValidationAware {
	/**
	 * 独立页模板前缀
	 */
	public static final String INDE_PRIFIX = "sys_";
	/**
	 * 根目录重定向调整
	 */
	public static final String INDEX_PAGE = "indexPage";
	/**
	 * 错误提示页面
	 */
	public static final String SHOW_ERROR = "ShowError";
	/**
	 * 信息提示页面
	 */
	public static final String SHOW_MESSAGE = "ShowMessage";
	/**
	 * 成功提示页面
	 */
	public static final String SHOW_SUCCESS = "ShowSuccess";
	/**
	 * 页面找不到提示页面
	 */
	public static final String PAGE_NOT_FOUND = "PageNotFound";

	/**
	 * 先查找方案模板，如果不存在则使用默认模板
	 * 
	 * @param tplName
	 * @return
	 */
	protected String handleResult(String tplName) {
		return handleResult(tplName, getSysType());
	}

	protected String handleResult(String tplName, String sysType) {
		return handleResult(tplName, sysType, getSolution());
	}

	protected String handleResult(String tplName, String sysType,
			String solution) {
		tplPath = getSolutionTpl(solution, tplName, sysType);
		String real = contextPvd.getAppRealPath(tplPath);
		// @ TODO 是否使用缓存，以免每次都检查模板是否存在？
		if (!new File(real).exists()) {
			tplPath = getSolutionTpl(TPL_DEF_SOLUTION, tplName, sysType);
		}
		return SUCCESS;
	}

	private String getSolutionTpl(String solution, String tplName,
			String sysType) {
		StringBuilder sb = getWeb().getTplRoot().append(SPT).append(sysType)
				.append(SPT).append(solution).append(SPT).append(INDE_PRIFIX)
				.append(tplName).append(TPL_SUFFIX);
		return sb.toString();
	}

	protected abstract String getSolution();

	protected abstract String getSysType();

	/**
	 * 用户资源根地址。如：http://www.sina.com/res_base/sina_com_www
	 * 
	 * @return
	 */
	public String getRoot() {
		if (root == null) {
			root = getWeb().getUserResUrl();
		}
		return root;
	}

	/**
	 * 系统资源根地址。如：http://www.sina.com/front_res
	 * 
	 * @return
	 */
	public String getSysResRoot() {
		if (sysResRoot == null) {
			sysResRoot = getWeb().getSysResUrl();
		}
		return sysResRoot;
	}

	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 获得会员ID
	 * 
	 * @return
	 */
	public Long getMemberId() {
		Member m = getMember();
		if (m == null) {
			return null;
		} else {
			return m.getId();
		}
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
		Long userId = getUserId();
		if (userId == null) {
			return null;
		} else {
			return userMng.findById(userId);
		}
	}

	/**
	 * 获得页面执行时间ms
	 * 
	 * @return 返回页面执行时间。-1代表没有找到页面开始执行时间。
	 */
	public float getProcessedIn() {
		Long time = (Long) contextPvd
				.getRequestAttr(ProcessingStartInterceptor.PROCESSING_START);
		if (time != null) {
			return (System.nanoTime() - time) / 1000 / 1000000F;
		} else {
			return -1;
		}
	}

	public String[] getPrefix() {
		if (prefix == null && otherParams != null && otherParams.length != 0) {
			prefix = new String[otherParams.length];
			int len = 1;
			prefix[0] = pageLink + "-";
			for (int i = 1; i < otherParams.length; i++) {
				len += otherParams[i - 1].length() + 1;
				prefix[i] = pageLink + pageSuffix.substring(len);
			}
		}
		return prefix;
	}

	private String[] prefix;

	public String[] suffix() {
		if (suffix == null && otherParams != null && otherParams.length != 0) {
			suffix = new String[otherParams.length];
			int len = 0;
			for (int i = 0; i < otherParams.length; i++) {
				len += otherParams[i].length() + 1;
				suffix[i] = pageLink + pageSuffix.substring(len);
			}
		}
		return suffix;
	}

	private String[] suffix;

	/**
	 * 页面重定向
	 * 
	 * @param url
	 * @return
	 */
	protected String redirect(String url) {
		this.redirectUrl = url;
		return Constants.REDIRECT;
	}

	public void setActionErrors(Collection errorMessages) {
		validationAware.setActionErrors(errorMessages);
	}

	public Collection getActionErrors() {
		return validationAware.getActionErrors();
	}

	public void setActionMessages(Collection messages) {
		validationAware.setActionMessages(messages);
	}

	public Collection getActionMessages() {
		return validationAware.getActionMessages();
	}

	public void setFieldErrors(Map errorMap) {
		validationAware.setFieldErrors(errorMap);
	}

	public Map getFieldErrors() {
		return validationAware.getFieldErrors();
	}

	public boolean hasActionErrors() {
		return validationAware.hasActionErrors();
	}

	public boolean hasActionMessages() {
		return validationAware.hasActionMessages();
	}

	public boolean hasErrors() {
		return validationAware.hasErrors();
	}

	public boolean hasFieldErrors() {
		return validationAware.hasFieldErrors();
	}

	public void addActionError(String anErrorMessage) {
		validationAware.addActionError(anErrorMessage);
	}

	public void addActionMessage(String aMessage) {
		validationAware.addActionMessage(aMessage);
	}

	public void addFieldError(String fieldName, String errorMessage) {
		validationAware.addFieldError(fieldName, errorMessage);
	}

	private final ValidationAwareSupport validationAware = new ValidationAwareSupport();
	/**
	 * 站点资源的根地址
	 */
	private String root;
	/**
	 * 系统前台资源根地址
	 */
	private String sysResRoot;
	/**
	 * 根站点的url地址
	 */
	protected String rootWebUrl;
	/**
	 * 当前的完整的url地址
	 */
	protected String wholeUrl;
	/**
	 * 页面重定向
	 */
	private String redirectUrl;
	/**
	 * 当前页数
	 */
	protected int pageNo = 0;

	protected String[] otherParams;

	@Autowired
	protected MemberMng memberMng;
	@Autowired
	protected UserMng userMng;

	public String getWholeUrl() {
		return wholeUrl;
	}

	public void setWholeUrl(String wholeUrl) {
		this.wholeUrl = wholeUrl;
	}

	public String getRootWebUrl() {
		return rootWebUrl;
	}

	public void setRootWebUrl(String rootWebUrl) {
		this.rootWebUrl = rootWebUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String[] getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(String[] otherParams) {
		this.otherParams = otherParams;
	}

}
