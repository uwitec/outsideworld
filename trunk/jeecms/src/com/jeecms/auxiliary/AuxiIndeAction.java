package com.jeecms.auxiliary;

import static com.jeecms.cms.Constants.CMS_MEMBER_LOGIN;
import static com.jeecms.cms.Constants.MEMBER_SYS;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.auxiliary.entity.AuxiConfig;
import com.jeecms.auxiliary.manager.AuxiConfigMng;
import com.jeecms.core.IndeBaseAction;

/**
 * 辅助系统独立页面的action祖先。
 * <p>
 * 处理AuxiConfig配置
 * </p>
 * 
 * @author liufang
 * 
 */
// FIXME 辅助系统本不应依赖CMS系统，但是该类为了使用CMS的提示信息机制，依赖了CMS系统代码。可考虑所有系统使用相同的信息提示。
public class AuxiIndeAction extends IndeBaseAction {
	@Override
	protected String getSolution() {
		return getWeb().getSolutions().get(Constants.AUXILIARY_SYS);
	}

	@Override
	protected String getSysType() {
		return Constants.AUXILIARY_SYS;
	}

	public AuxiConfig getConfig() {
		return auxiConfigMng.findById(getWebId());
	}

	/**
	 * 返回信息提示页面，无返回按钮，用于已经登录但是没有权限等提示。
	 * 
	 * @return
	 */
	protected String showMessage() {
		return handleResult(SHOW_MESSAGE, MEMBER_SYS);
	}

	/**
	 * 显示错误信息，有返回按钮，但不自动跳转，如验证码错误等提示。
	 * 
	 * @return
	 */
	protected String showError() {
		return handleResult(SHOW_ERROR, MEMBER_SYS);
	}

	/**
	 * 显示出成功信息，有返回按钮，2秒后自动跳转，如修改密码成功。需要提供返回链接地址。
	 * 
	 * @return
	 */
	protected String showSuccess() {
		return handleResult(SHOW_SUCCESS, MEMBER_SYS);
	}

	/**
	 * 重定向到登录页面
	 * 
	 * @return
	 */
	protected String redirectLogin() {
		rootWebUrl = getWeb().getRootWeb().getWebUrl();
		return CMS_MEMBER_LOGIN;
	}

	@Autowired
	protected AuxiConfigMng auxiConfigMng;
}
