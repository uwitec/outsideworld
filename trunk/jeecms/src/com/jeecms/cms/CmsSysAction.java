package com.jeecms.cms;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.entity.CmsAdmin;
import com.jeecms.cms.entity.CmsConfig;
import com.jeecms.cms.entity.CmsMember;
import com.jeecms.cms.manager.CmsAdminMng;
import com.jeecms.cms.manager.CmsConfigMng;
import com.jeecms.cms.manager.CmsMemberMng;
import com.jeecms.core.JeeCoreAction;
import com.jeecms.core.entity.Admin;
import com.jeecms.core.exception.AdminNotFoundException;

/**
 * jeecms的action祖先。
 * <p>
 * 处理cms管理员
 * </p>
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public abstract class CmsSysAction extends JeeCoreAction {
	/**
	 * 获得JEECMS管理员ID
	 * <p>
	 * 如果不存在则抛出异常
	 * </p>
	 * 
	 * @return
	 */
	public Long getCmsAdminId() throws AdminNotFoundException {
		// 经过访问控制，adminId总是正确的
		Long adminId = (Long) contextPvd.getSessionAttr(Admin.ADMIN_KEY);
		if (adminId == null) {
			throw new AdminNotFoundException("您不是JEECMS系统的管理员！");
		} else {
			return adminId;
		}
	}

	/**
	 * 获得JEECMS管理员对象
	 * 
	 * @return
	 */
	public CmsAdmin getCmsAdmin() throws AdminNotFoundException {
		return cmsAdminMng.findById(getCmsAdminId());
	}

	/**
	 * 获得JEECMS会员对象
	 * 
	 * @return
	 */
	public CmsMember getCmsMember() {
		Long memberId = getMemberId();
		if (memberId == null) {
			return null;
		} else {
			return cmsMemberMng.findById(memberId);
		}
	}

	/**
	 * 获得JEECMS会员对象ID
	 * 
	 * @return
	 */
	public Long getCmsMemberId() {
		CmsMember cmsMember = getCmsMember();
		if (cmsMember == null) {
			return null;
		} else {
			return cmsMember.getId();
		}
	}

	/**
	 * 获得JEECMS配置对象
	 * 
	 * @return
	 */
	public CmsConfig getConfig() {
		return cmsConfigMng.findById(getWebId());
	}

	@Autowired
	protected CmsAdminMng cmsAdminMng;
	@Autowired
	protected CmsConfigMng cmsConfigMng;
	@Autowired
	protected CmsMemberMng cmsMemberMng;
}
