package com.jeecms.cms;

import com.jeecms.cms.entity.CmsMember;
import com.jeecms.core.entity.Member;
import com.jeecms.core.entity.User;

/**
 * JEECMS会员action的基类。
 * <p>
 * 提供登录验证方法和系统类型
 * </p>
 * 
 * @author liufang
 * 
 */
public class CmsMemberAction extends CmsIndeAction {
	/**
	 * 检查是否登录和错误信息，并设置提示信息
	 * 
	 * @return 如果登录返回null，否则返回登录跳转或信息提示。
	 */
	protected String checkLoginAndError() {
		if (hasErrors()) {
			return showError();
		}
		User user = getUser();
		// 没有登录
		if (user == null) {
			if (contextPvd.isMethodPost()) {
				addActionError("对不起，您还没有登录，无法进行此操作");
				return showMessage();
			} else {
				return redirectLogin();
			}
		}
		Member member = getMember();
		// 不是本站会员
		if (member == null) {
			addActionError("您不是本站会员");
			return showMessage();
		}
		CmsMember cmsMember = getCmsMember();
		// 不是本系统会员
		if (cmsMember == null) {
			addActionError("您不是本系统会员");
			return showMessage();
		}
		return null;
	}

	@Override
	protected String getSysType() {
		return Constants.MEMBER_SYS;
	}
}
