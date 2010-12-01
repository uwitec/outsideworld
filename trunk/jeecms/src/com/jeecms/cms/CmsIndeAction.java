package com.jeecms.cms;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.entity.CmsConfig;
import com.jeecms.cms.entity.CmsMember;
import com.jeecms.cms.manager.CmsConfigMng;
import com.jeecms.cms.manager.CmsMemberMng;
import com.jeecms.core.IndeBaseAction;

/**
 * JEECMS独立页面的action祖先。
 * <p>
 * 处理CmsConfig配置和当前系统模板方案
 * </p>
 * 
 * @author liufang
 * 
 */
public abstract class CmsIndeAction extends IndeBaseAction {
	@Override
	protected String getSolution() {
		return getConfig().getSolution(getSysType());
	}

	@Override
	protected String getSysType() {
		return Constants.COMMON_SYS;
	}

	/**
	 * 获得CMS配置对象
	 * 
	 * @return
	 */
	public CmsConfig getConfig() {
		return cmsConfigMng.findById(getWebId());
	}

	/**
	 * 获得CMS会员
	 * 
	 * @return
	 */
	public CmsMember getCmsMember() {
		Long memberId = getMemberId();
		if (memberId == null) {
			return null;
		} else {
			CmsMember cmsMember = cmsMemberMng.findById(memberId);
			if (cmsMember != null && cmsMember.getMemberDisabled()) {
				throw new RuntimeException("您的帐号已经被禁用！");
			}
			return cmsMemberMng.findById(memberId);
		}
	}

	/**
	 * 返回信息提示页面，无返回按钮，用于已经登录但是没有权限等提示。
	 * 
	 * @return
	 */
	protected String showMessage() {
		return handleResult(SHOW_MESSAGE, Constants.MEMBER_SYS);
	}

	/**
	 * 显示错误信息，有返回按钮，但不自动跳转，如验证码错误等提示。
	 * 
	 * @return
	 */
	protected String showError() {
		return handleResult(SHOW_ERROR, Constants.MEMBER_SYS);
	}

	/**
	 * 显示出成功信息，有返回按钮，2秒后自动跳转，如修改密码成功。需要提供返回链接地址。
	 * 
	 * @return
	 */
	protected String showSuccess() {
		return handleResult(SHOW_SUCCESS, Constants.MEMBER_SYS);
	}

	/**
	 * 重定向到登录页面
	 * 
	 * @return
	 */
	protected String redirectLogin() {
		rootWebUrl = getWeb().getRootWeb().getWebUrl();
		return Constants.CMS_MEMBER_LOGIN;
	}

	@Autowired
	protected CmsConfigMng cmsConfigMng;

	@Autowired
	protected CmsMemberMng cmsMemberMng;
}
