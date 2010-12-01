package com.jeecms.cms.action.front;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jeecms.article.entity.Article;
import com.jeecms.article.manager.ArticleMng;
import com.jeecms.cms.CmsIndeAction;
import com.jeecms.cms.entity.CmsComment;
import com.jeecms.cms.manager.CmsCommentMng;
import com.jeecms.common.page.Pagination;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 评论独立模板
 * 
 * @author liufang
 * 
 */
@Scope("prototype")
@Controller("cms.commentIndeAct")
public class CommentIndeAct extends CmsIndeAction {
	private static final Logger log = LoggerFactory
			.getLogger(CommentIndeAct.class);

	public String comment() {
		String err = validateCommentList();
		if (err != null) {
			return err;
		}
		doc = articleMng.findById(articleId);
		return handleResult("Comment");
	}

	public String commentSubmit() {
		String err = validateCommentSubmit();
		if (err != null) {
			jsonRoot.put("success", false);
			jsonRoot.put("msg", err);
			return SUCCESS;
		}
		cmsCommentMng.save(bean);
		String msg;
		if (getConfig().getCommentNeedCheck()) {
			msg = "评论发表成功，请等待审核";
		} else {
			msg = "评论发表成功";
		}
		jsonRoot.put("success", true);
		jsonRoot.put("msg", msg);
		log.info("评论成功：{}", bean.getContentMember());
		return SUCCESS;
	}

	private String validateCommentList() {
		if (hasErrors()) {
			return showMessage();
		}
		Article entity = articleMng.findById(articleId);
		if (entity == null) {
			addActionError("文章不存在：" + articleId);
			return showMessage();
		}
		if (!entity.getAllowComment()) {
			addActionError("该文章不允许评论：" + entity.getTitle());
			return showMessage();
		}
		return null;
	}

	private String validateCommentSubmit() {
		if (hasErrors()) {
			return "输入不合法";
		}
		if (!imageCaptchaService.validateResponseForID(contextPvd
				.getSessionId(false), checkCode)) {
			return "验证码错误";
		}
		bean = preparedBean();
		String err;
		err = vldArticle(articleId, bean);
		if (err != null) {
			return err;
		}
		return null;
	}

	private String vldArticle(Long id, CmsComment bean) {
		Article entity = articleMng.findById(id);
		if (entity == null) {
			return "评论的文章不存在：" + id;
		}
		if (!entity.getAllowComment()) {
			return "文章不允许评论：" + id;
		}
		if (getConfig().getCommentNeedLogin() && getMember() == null) {
			return "需要登录才能评论";
		}
		if (bean != null) {
			bean.setDoc(entity);
			bean.setWebsite(entity.getWebsite());
			bean.setMember(getMember());
		}
		return null;
	}

	private CmsComment preparedBean() {
		CmsComment bean = new CmsComment();
		bean.setTitle(title);
		bean.setContentMember(content);
		return bean;
	}

	@Autowired
	private ImageCaptchaService imageCaptchaService;
	private String checkCode;
	@Autowired
	private ArticleMng articleMng;
	@Autowired
	private CmsCommentMng cmsCommentMng;
	private Map<String, Object> jsonRoot = new HashMap<String, Object>();

	private Pagination pagination;

	private String title;
	private String content;
	private Long articleId;

	private CmsComment bean;
	private Object doc;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Map<String, Object> getJsonRoot() {
		return jsonRoot;
	}

	public void setJsonRoot(Map<String, Object> jsonRoot) {
		this.jsonRoot = jsonRoot;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Object getDoc() {
		return doc;
	}

	public void setDoc(Object doc) {
		this.doc = doc;
	}
}