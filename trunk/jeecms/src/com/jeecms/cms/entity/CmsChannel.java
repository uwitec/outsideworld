package com.jeecms.cms.entity;

import static com.jeecms.core.Constants.SPT;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.base.BaseCmsChannel;
import com.jeecms.common.hibernate3.HibernateTree;
import com.jeecms.common.util.SelectTree;
import com.jeecms.core.PageBaseAction;
import com.jeecms.core.entity.Attachment;
import com.jeecms.core.util.PriorityComparator;
import com.jeecms.core.util.PriorityInterface;

public class CmsChannel extends BaseCmsChannel implements SelectTree,
		PriorityInterface, HibernateTree {
	/**
	 * 上传栏目图片相对地址
	 */
	public static final String UPLOAD_PATH = SPT + "channel";

	/**
	 * 在附件表中的类别
	 */
	public static final String ATTACHMENT_CTG = "栏目";

	/**
	 * 获得标题图片URL地址
	 * 
	 * @return
	 */
	public String getImgUrl() {
		String img = getTitleImg();
		if (StringUtils.isBlank(img)) {
			// TODO 链接到图片默认的提示图片
			return "";
		} else {
			return getWebsite().getUploadUrlBuf().append(img).toString();
		}
	}

	/**
	 * 获得内容图片的URL地址
	 * 
	 * @return
	 */
	public String getCttImgUrl() {
		String img = getContentImg();
		if (StringUtils.isBlank(img)) {
			// TODO 链接到图片默认的提示图片
			return "";
		} else {
			return getWebsite().getUploadUrlBuf().append(img).toString();
		}
	}

	/**
	 * 获得链接地址
	 * 
	 * @return
	 */
	public String getUrl() {
		if (getParent() == null) {
			// 首页
			return getWebsite().getWebUrl();
		} else if (!StringUtils.isBlank(getOuterUrl())) {
			// 外部链接
			if (getOuterUrl().startsWith("http")) {
				return getOuterUrl();
			} else {
				return getWebsite().getWebUrl() + getOuterUrl();
			}
		} else if (getModel().getHasChild()) {
			// 正常栏目
			StringBuilder sb = getWebsite().getWebUrlBuf().append(SPT).append(
					getPath()).append(SPT).append(PageBaseAction.INDEX).append(
					".").append(getWebsite().getSuffix());
			return sb.toString();
		} else {
			// 单页
			StringBuilder sb = new StringBuilder();
			sb.append(getWebsite().getWebUrl()).append(SPT).append(getPath())
					.append(".").append(getWebsite().getSuffix());
			return sb.toString();
		}
	}

	/**
	 * 选择栏目模板地址
	 * 
	 * @return
	 */
	public String chooseTplChannel() {
		if (!StringUtils.isBlank(getTplIndex())) {
			return getWebsite().getTplRoot().append(getTplIndex()).toString();
		} else {
			return getModel().defIndexTpl(getWebsite(), getSysType());
		}
	}

	/**
	 * 选择内容模板地址
	 * 
	 * @return
	 */
	public String chooseTplContent() {
		if (!StringUtils.isBlank(getTplContent())) {
			return getWebsite().getTplRoot().append(getTplContent()).toString();
		} else {
			return getModel().defContentTpl(getWebsite(), getSysType());
		}
	}

	public void addToAdmins(CmsAdmin admin) {
		Set<CmsAdmin> set = getAdmins();
		if (set == null) {
			set = new HashSet<CmsAdmin>();
			setAdmins(set);
		}
		set.add(admin);
	}

	/**
	 * 管理员ID是否有本栏目的权限
	 * 
	 * @param adminId
	 * @return
	 */
	public boolean adminsContain(Long adminId) {
		if (adminId == null) {
			return false;
		}
		Set<CmsAdmin> admins = getAdmins();
		if (admins == null) {
			return false;
		}
		for (CmsAdmin admin : admins) {
			if (adminId.equals(admin.getId())) {
				return true;
			}
		}
		return false;
	}

	public void addToChild(CmsChannel chnl) {
		if (chnl == null) {
			return;
		}
		chnl.setParent(this);
		Set<CmsChannel> child = getChild();
		if (child == null) {
			child = new TreeSet<CmsChannel>(new PriorityComparator());
			setChild(child);
		}
		chnl.setParent(this);
		child.add(chnl);
	}

	/**
	 * 下拉列表树
	 */
	private String selectTree;
	/**
	 * div树，是否为叶子节点
	 */
	private Boolean treeLeaf;

	public String getSelectTree() {
		return selectTree;
	}

	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}

	public String getTreeName() {
		return getName();
	}

	public Long getParentId() {
		CmsChannel parent = getParent();
		if (parent == null) {
			return null;
		} else {
			return parent.getId();
		}
	}

	public String getTreeCondition() {
		return "b.sysType='" + getSysType() + "' and b.website.id="
				+ getWebsite().getId();
	}

	public Set<CmsChannel> getTreeChild() {
		if (treeChild != null) {
			return treeChild;
		} else {
			return getChild();
		}
	}

	public Set<? extends SelectTree> getTreeChildRaw() {
		return treeChild;
	}

	public CmsChannel getTreeParent() {
		return getParent();
	}

	public boolean isTreeLeaf() {
		if (treeLeaf != null) {
			return treeLeaf;
		}
		Set<CmsChannel> child = getChild();
		if (child != null && child.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void addToAttachments(Attachment attachment) {
		Set<Attachment> attachments = getAttachments();
		if (attachments == null) {
			attachments = new HashSet<Attachment>();
			setAttachments(attachments);
		}
		attachments.add(attachment);
	}

	private static final long serialVersionUID = 1L;
	private Set<CmsChannel> treeChild;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsChannel() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsChannel(java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsChannel(java.lang.Long id, com.jeecms.cms.entity.ChnlModel model,
			com.jeecms.cms.entity.CmsConfig config,
			com.jeecms.core.entity.Website website, java.lang.String sysType,
			java.lang.Integer lft, java.lang.Integer rgt,
			java.lang.Integer docCount, java.lang.Integer priority,
			java.lang.Boolean hasTitleImg, java.lang.Boolean hasChild,
			java.lang.Boolean display) {

		super(id, model, config, website, sysType, lft, rgt, docCount,
				priority, hasTitleImg, hasChild, display);
	}

	/* [CONSTRUCTOR MARKER END] */

	public void setTreeLeaf(Boolean treeLeaf) {
		this.treeLeaf = treeLeaf;
	}

	@SuppressWarnings("unchecked")
	public void setTreeChild(Set treeChild) {
		this.treeChild = (Set<CmsChannel>) treeChild;
	}
}