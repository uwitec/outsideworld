package com.jeecms.cms.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.jeecms.cms.entity.CmsComment;
import com.jeecms.cms.manager.CmsCommentMng;
import com.jeecms.common.hibernate3.OrderBy;

@SuppressWarnings("serial")
@Scope("prototype")
@Controller("cms.cmsCommentAct")
public class CmsCommentAct extends com.jeecms.cms.CmsSysAction {
	private static final Logger log = LoggerFactory.getLogger(CmsComment.class);

	public String list() {
		this.pagination = cmsCommentMng.findAll(pageNo, getCookieCount(),
				OrderBy.desc("id"));
		return LIST;
	}

	public String edit() {
		this.bean = cmsCommentMng.findById(id);
		return EDIT;
	}

	public String update() {
		cmsCommentMng.updateDefault(bean);
		log.info("修改  评论 成功:{}", bean.getContentMember());
		return list();
	}

	public String delete() {
		try {
			for (CmsComment o : cmsCommentMng.deleteById(ids)) {
				log.info("删除  评论 成功:{}", o.getContentMember());
			}
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			return SHOW_ERROR;
		}
		return list();
	}

	public String check() {
		for (CmsComment o : cmsCommentMng.check(ids)) {
			log.info("审核  评论 成功:{}", o.getContentMember());
		}
		return list();
	}

	public boolean validateEdit() {
		if (hasErrors()) {
			return true;
		}
		if (vldExist(id)) {
			return true;
		}
		if (vldWebsite(id, null)) {
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			return true;
		}
		if (vldExist(bean.getId())) {
			return true;
		}
		if (vldWebsite(bean.getId(), null)) {
			return true;
		}
		bean.setWebsite(getWeb());
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			return true;
		}
		if (vldBatch()) {
			return true;
		}
		for (Long id : ids) {
			if (vldExist(id)) {
				return true;
			}
			if (vldWebsite(id, null)) {
				return true;
			}
		}
		return false;
	}

	public boolean validateCheck() {
		return validateDelete();
	}

	private boolean vldExist(Long id) {
		CmsComment entity = cmsCommentMng.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	private boolean vldWebsite(Long id, CmsComment bean) {
		CmsComment entity = cmsCommentMng.findById(id);
		if (!entity.getWebsite().equals(getWeb())) {
			addActionError("只能管理本站点数据：" + id);
			return true;
		}
		if (bean != null) {
			bean.setWebsite(getWeb());
		}
		return false;
	}

	@Autowired
	private CmsCommentMng cmsCommentMng;
	private CmsComment bean;

	public CmsComment getBean() {
		return bean;
	}

	public void setBean(CmsComment bean) {
		this.bean = bean;
	}
}