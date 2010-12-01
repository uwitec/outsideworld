package com.jeecms.download.action;

import static com.jeecms.cms.Constants.DOWNLOAD_SYS;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.jeecms.cms.entity.CmsChannel;
import com.jeecms.cms.entity.CmsMemberGroup;
import com.jeecms.cms.entity.ContentCtg;
import com.jeecms.cms.manager.CmsChannelMng;
import com.jeecms.cms.manager.CmsMemberGroupMng;
import com.jeecms.cms.manager.ContentCtgMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.util.BCConvert;
import com.jeecms.common.util.SelectTreeUtils;
import com.jeecms.core.util.UploadRule;
import com.jeecms.download.entity.DownType;
import com.jeecms.download.entity.Download;
import com.jeecms.download.manager.DownTypeMng;
import com.jeecms.download.manager.DownloadMng;

@SuppressWarnings("serial")
@Scope("prototype")
@Controller("download.downloadAct")
public class DownloadAct extends com.jeecms.cms.CmsSysAction {
	private static final Logger log = LoggerFactory
			.getLogger(DownloadAct.class);

	@SuppressWarnings("unchecked")
	public String left() {
		List<CmsChannel> chnlList = cmsChannelMng.getRightChnl(getWebId(),
				DOWNLOAD_SYS, getCmsAdminId(), true);
		chnlList = SelectTreeUtils.handleTreeChild(chnlList);
		if (chnlList.size() > 0) {
			treeRoot = chnlList.get(0);
		}
		return LEFT;
	}

	public String list() {
		if (chnlId == null) {
			chnl = cmsChannelMng.getRoot(getWebId(), DOWNLOAD_SYS, true);
			if (chnl != null) {
				chnlId = chnl.getId();
			}
		} else {
			chnl = cmsChannelMng.findById(chnlId);
		}
		selfOnly = getCmsAdmin().getSelfOnly();
		if (selfOnly) {
			queryMy = selfOnly;
		}
		if (chnlId != null) {
			pagination = downloadMng.getRightDownload(getWebId(), chnlId,
					getCmsAdminId(), queryMy, queryStatus, queryTitle,
					queryOrder, pageNo, getCookieCount());
		} else {
			pagination = new Pagination(1, getCookieCount(), 0, null);
		}
		return LIST;
	}

	@SuppressWarnings("unchecked")
	public String add() {
		contentCtgList = contentCtgMng.getList(getWebId(), false);
		CmsChannel channel = cmsChannelMng.findById(chnlId);
		tplContentList = channel.getModel().tplContentList(getConfig(),
				DOWNLOAD_SYS, contextPvd.getAppRoot());
		downTypeList = downTypeMng.getList(getWebId(), false);
		// 只能选择同一模型的栏目
		Long modelId;
		if (channel.getParent() == null
				&& getConfig().getDefDownloadModel() != null) {
			modelId = getConfig().getDefDownloadModel().getId();
		} else {
			modelId = channel.getModel().getId();
		}
		chnlList = cmsChannelMng.getRightChnl(getWebId(), DOWNLOAD_SYS, chnlId,
				getCmsAdminId(), modelId, true);
		chnlList = SelectTreeUtils.handleTreeChild(chnlList);
		chnlList = SelectTreeUtils.webTree(chnlList);
		// 设置上传规则
		addUploadRule();
		return ADD;
	}

	public String save() {
		downloadMng.saveDownload(bean, getCmsAdmin(), getCmsMember(),
				uploadRule, getWeb().getResUrl(), downloadAttch, topTime);

		// 清除上传规则
		removeUploadRule();

		log.info("添加 下载 成功:{}", bean.getTitle());
		addActionMessage("添加成功");
		return list();
	}

	@SuppressWarnings("unchecked")
	public String edit() {
		this.bean = downloadMng.findById(id);
		contentCtgList = contentCtgMng.getList(getWebId(), false);
		tplContentList = bean.getChannel().getModel().tplContentList(
				getConfig(), DOWNLOAD_SYS, contextPvd.getAppRoot());
		downTypeList = downTypeMng.getList(getWebId(), false);
		List<CmsChannel> chnlList = cmsChannelMng.getRightChnl(bean
				.getWebsite().getId(), DOWNLOAD_SYS, getCmsAdminId(), true);
		chnlList = SelectTreeUtils.handleTreeChild(chnlList);
		this.list = SelectTreeUtils.webTree(chnlList);

		// 设置上传规则
		addUploadRule();
		return EDIT;
	}

	public String check() {
		this.bean = downloadMng.findById(id);
		if (bean.getCheck()) {
			bean.setAdminDisable(getAdmin());
			bean.setCheck(false);
		} else {
			bean.setAdminCheck(getAdmin());
			bean.setCheck(true);
		}
		log.info("审核操作 下载 成功:{}", bean.getTitle());
		addActionMessage("操作成功");
		return list();
	}

	public String update() {
		downloadMng.updateDownload(bean, getCmsAdmin(), getCmsMember(),
				uploadRule, getWeb().getResUrl(), downloadAttch, topTime);

		// 清除上传规则
		removeUploadRule();

		log.info("修改 下载 成功:{}", bean.getTitle());
		return list();
	}

	public String delete() {
		try {
			if (id != null) {
				bean = downloadMng.deleteById(id);
				log.info("删除 下载 成功:{}", bean.getTitle());
			} else {
				for (Download o : downloadMng.deleteById(ids)) {
					log.info("删除 下载 成功:{}", o.getTitle());
				}
			}
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			return SHOW_ERROR;
		}
		return list();
	}

	public boolean validateAdd() {
		if (hasErrors()) {
			return true;
		}
		if (vldChannel(chnlId, true, null)) {
			return true;
		}
		return false;
	}

	public boolean validateSave() {
		if (hasErrors()) {
			return true;
		}
		// 验证上传规则
		if (vldUploadRule()) {
			return true;
		}
		// 处理bean
		if (vldBean()) {
			return true;
		}
		bean.setWebsite(getWeb());
		// 验证栏目
		if (vldChannel(bean.getChannel().getId(), false, bean)) {
			return true;
		}
		// 验证内容属性
		if (vldContentCtg(bean.getContentCtg().getId(), bean)) {
			return true;
		}
		// 验证会员组
		if (vldMemberGroup(bean.getGroup(), bean, true)) {
			return true;
		}
		return false;
	}

	public boolean validateEdit() {
		if (hasErrors()) {
			return true;
		}
		if (vldDownloadRight(id)) {
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
		// 验证上传规则
		if (vldUploadRule()) {
			return true;
		}
		// 处理bean
		if (vldBean()) {
			return true;
		}
		// 验证文章权限
		if (vldDownloadRight(bean.getId())) {
			return true;
		}
		// 验证栏目
		if (vldChannel(bean.getChannel().getId(), false, null)) {
			return true;
		}
		if (vldWebsite(bean.getId(), bean)) {
			return true;
		}
		// 验证内容属性
		if (vldContentCtg(bean.getContentCtg().getId(), null)) {
			return true;
		}
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			return true;
		}
		Download entity;
		if (id == null && (ids == null || ids.length <= 0)) {
			addActionError("ID不能为空");
			return true;
		} else {
			if (id != null) {
				ids = new Long[] { id };
			}
			for (Long id : ids) {
				entity = downloadMng.findById(id);
				if (!entity.getWebsite().getId().equals(getWebId())) {
					addActionError("不能删除其他站点文章");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 验证文章修改权限
	 * 
	 * @param entity
	 * @return
	 */
	private boolean vldDownloadRight(Long id) {
		Download entity = downloadMng.findById(id);
		if (entity == null) {
			addActionError("该文章不存在：" + id);
			return true;
		}
		// 只能管理自己的数据的管理员，不能管理其他数据。
		if (getCmsAdmin().getSelfOnly()
				&& !getCmsAdminId().equals(entity.getAdminInput().getId())) {
			addActionError("不是自己的数据不能修改：" + id);
			return true;
		}
		// 只能管理有权限的栏目的文章
		if (!entity.getChannel().adminsContain(getCmsAdminId())) {
			addActionError("您没有这个文章所在栏目的权限：" + id);
			return true;
		}
		return false;
	}

	private boolean vldUploadRule() {
		// 上传规则
		uploadRule = (UploadRule) contextPvd.getSessionAttr(UploadRule.KEY
				+ uploadRuleId);
		if (uploadRule == null) {
			addActionError("没有找到上传规则，不允许提交");
			return true;
		}
		return false;
	}

	private boolean vldChannel(Long chnlId, boolean allowLeaf, Download bean) {
		if (chnlId == null) {
			addActionError("栏目ID不能为空");
		}
		CmsChannel c = cmsChannelMng.findById(chnlId);
		if (c == null) {
			addActionError("该栏目不存在：" + chnlId);
			return true;
		}
		if (!c.getHasChild()) {
			addActionError("该栏目不允许有内容：" + c.getName());
			return true;
		}
		if (!allowLeaf && !c.isTreeLeaf()) {
			addActionError("只有末级栏目才能添加内容：" + c.getName());
			return true;
		}
		if (!c.getWebsite().getId().equals(getWebId())) {
			addActionError("不是本站点的栏目：" + chnlId);
			return true;
		}
		if (!c.getAdmins().contains(getCmsAdmin())) {
			addActionError("您没有该栏目的权限：" + chnlId);
			return true;
		}
		if (bean != null) {
			bean.setChannel(c);
		}
		return false;
	}

	private boolean vldContentCtg(Long ctgId, Download bean) {
		ContentCtg po = contentCtgMng.findById(ctgId);
		if (po == null) {
			addActionError("该文章属性不存在：" + ctgId);
			return true;
		}
		if (!po.getWebsite().getId().equals(getWeb().getRootWebId())) {
			addActionError("不是本系列网站的文章属性：" + ctgId);
			return true;
		}
		if (bean != null) {
			bean.setContentCtg(po);
		}
		return false;
	}

	private boolean vldMemberGroup(CmsMemberGroup group, Download bean,
			boolean onSave) {
		// 可为空字段
		if (group == null) {
			return false;
		}
		Long id = group.getId();
		if (id != null) {
			CmsMemberGroup po = cmsMemberGroupMng.findById(id);
			if (po == null) {
				addActionError("该会员组不存在：" + id);
				return true;
			}
			if (!po.getWebsite().getId().equals(getWeb().getRootWebId())) {
				addActionError("不是本系列网站的会员组：" + id);
				return true;
			}
			if (bean != null) {
				bean.setGroup(po);
			}
		} else {
			if (onSave) {
				bean.setGroup(null);
			}
		}
		return false;
	}

	private boolean vldWebsite(Long id, Download bean) {
		Download entity = downloadMng.findById(id);
		if (!entity.getWebsite().getId().equals(getWebId())) {
			addActionError("只能修改本站点数据：" + id);
			return true;
		}
		if (bean != null) {
			bean.setWebsite(getWeb());
		}
		return false;
	}

	/**
	 * 处理bean
	 * 
	 * @return
	 */
	private boolean vldBean() {
		// 处理checkbox
		if (bean.getBold() == null) {
			bean.setBold(false);
		}
		// 处理tag
		String tags = bean.getTags();
		if (!StringUtils.isBlank(tags)) {
			bean.setTags(BCConvert.qj2bj(tags));
		}
		// 处理timestamp
		Date d = bean.getReleaseDate();
		if (d != null) {
			bean.setReleaseDate(new Timestamp(d.getTime()));
		}
		return false;
	}

	private void addUploadRule() {
		UploadRule rule = new UploadRule(getWeb().getUploadRoot().toString(),
				Download.UPLOAD_PATH, true);
		Set<String> downloadset = new HashSet<String>();
		for (String s : UploadRule.DEF_IMG_ACCEPT) {
			downloadset.add(s);
		}
		downloadset.add("doc");
		downloadset.add("rar");
		downloadset.add("zip");
		downloadset.add("xls");
		rule.setAcceptImg(downloadset);
		uploadRuleId = rule.hashCode();
		contextPvd.setSessionAttr(UploadRule.KEY + uploadRuleId, rule);
	}

	private void removeUploadRule() {
		// 删除未被使用的图片
		//uploadRule.clearUploadFile();
		// 清除上传规则
		contextPvd.removeAttribute(UploadRule.KEY + uploadRuleId);
	}

	@Autowired
	private DownloadMng downloadMng;
	@Autowired
	private CmsChannelMng cmsChannelMng;
	@Autowired
	private DownTypeMng downTypeMng;
	@Autowired
	private ContentCtgMng contentCtgMng;
	@Autowired
	private CmsMemberGroupMng cmsMemberGroupMng;

	private Download bean;
	private CmsChannel treeRoot;
	private Long chnlId;

	private CmsChannel chnl;
	private List<ContentCtg> contentCtgList;
	private List<CmsMemberGroup> memberGroupList;
	private List<CmsChannel> chnlList;
	private List<String> tplContentList;
	private List<DownType> downTypeList;

	private int uploadRuleId;
	private UploadRule uploadRule;

	private long topTime = 0;

	private boolean selfOnly = false;
	private boolean queryMy = false;
	private int queryStatus = 0;
	private String queryTitle = "";
	private String downloadAttch;
	private int queryOrder = 0;

	public Download getBean() {
		return bean;
	}

	public void setBean(Download bean) {
		this.bean = bean;
	}

	public CmsChannel getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(CmsChannel treeRoot) {
		this.treeRoot = treeRoot;
	}

	public Long getChnlId() {
		return chnlId;
	}

	public void setChnlId(Long chnlId) {
		this.chnlId = chnlId;
	}

	public CmsChannel getChnl() {
		return chnl;
	}

	public void setChnl(CmsChannel chnl) {
		this.chnl = chnl;
	}

	public List<ContentCtg> getContentCtgList() {
		return contentCtgList;
	}

	public void setContentCtgList(List<ContentCtg> contentCtgList) {
		this.contentCtgList = contentCtgList;
	}

	public List<CmsMemberGroup> getMemberGroupList() {
		return memberGroupList;
	}

	public void setMemberGroupList(List<CmsMemberGroup> memberGroupList) {
		this.memberGroupList = memberGroupList;
	}

	public List<CmsChannel> getChnlList() {
		return chnlList;
	}

	public void setChnlList(List<CmsChannel> chnlList) {
		this.chnlList = chnlList;
	}

	public List<String> getTplContentList() {
		return tplContentList;
	}

	public void setTplContentList(List<String> tplContentList) {
		this.tplContentList = tplContentList;
	}

	public int getUploadRuleId() {
		return uploadRuleId;
	}

	public void setUploadRuleId(int uploadRuleId) {
		this.uploadRuleId = uploadRuleId;
	}

	public long getTopTime() {
		return topTime;
	}

	public void setTopTime(long topTime) {
		this.topTime = topTime;
	}

	public boolean isSelfOnly() {
		return selfOnly;
	}

	public void setSelfOnly(boolean selfOnly) {
		this.selfOnly = selfOnly;
	}

	public boolean isQueryMy() {
		return queryMy;
	}

	public void setQueryMy(boolean queryMy) {
		this.queryMy = queryMy;
	}

	public int getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(int queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryTitle() {
		return queryTitle;
	}

	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}

	public int getQueryOrder() {
		return queryOrder;
	}

	public void setQueryOrder(int queryOrder) {
		this.queryOrder = queryOrder;
	}

	public List<DownType> getDownTypeList() {
		return downTypeList;
	}

	public void setDownTypeList(List<DownType> downTypeList) {
		this.downTypeList = downTypeList;
	}

	public String getDownloadAttch() {
		return downloadAttch;
	}

	public void setDownloadAttch(String downloadAttch) {
		this.downloadAttch = downloadAttch;
	}
}