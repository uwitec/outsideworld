package com.jeecms.core.entity;

import static com.jeecms.core.Constants.RES_BASE;
import static com.jeecms.core.Constants.RES_SYS;
import static com.jeecms.core.Constants.SPT;
import static com.jeecms.core.Constants.TEMPLATE;
import static com.jeecms.core.Constants.UPLOAD_PATH;
import static com.jeecms.core.Constants.USER_BASE;
import static com.jeecms.core.Constants.WEBINF;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jeecms.common.hibernate3.HibernateTree;
import com.jeecms.common.util.SelectTree;
import com.jeecms.core.entity.base.BaseWebsite;

public class Website extends BaseWebsite implements SelectTree, HibernateTree {
	/**
	 * 用户相对根路径。/WEB-INF/user_base/
	 */
	public static final String USER_ROOT = SPT + WEBINF + SPT + USER_BASE + SPT;
	/**
	 * 页面访问默认的后缀
	 */
	public static final String DEF_SUFFIX = "htm";

	/**
	 * 获得站点的URL。如：http://www.nc138.com 或 http://www.nc138.com:8080/CmsSys
	 * 
	 * @return
	 */
	public StringBuilder getWebUrlBuf() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(getDomain());
		if (getPort() != null && !getPort().equals(80)) {
			sb.append(":").append(getPort());
		}
		if (getContextPath() != null) {
			sb.append(getContextPath());
		}
		return sb;
	}

	/**
	 * 获得站点的URL。如：http://www.nc138.com 或 http://www.nc138.com:8080/CmsSys
	 * 
	 * @return
	 */
	public String getWebUrl() {
		return getWebUrlBuf().toString();
	}

	/**
	 * 获得资源站点的URL。如：http://res.nc138.com 或 http://res.nc138.com:8080/CmsSys
	 * 
	 * 如没有指定资源域名，则和网站访问地址一样。为远程附件做准备。
	 * 
	 * @return
	 */
	public StringBuilder getResUrlBuf() {
		if (StringUtils.isBlank(getResDomain())) {
			return getWebUrlBuf();
		} else {
			return new StringBuilder(getResDomain());
		}
	}

	/**
	 * 获得资源站点的URL。如：http://res.nc138.com 或 http://res.nc138.com:8080/CmsSys
	 * 
	 * 如没有指定资源域名，则和网站访问地址一样。为远程附件做准备。
	 * 
	 * @return
	 */
	public String getResUrl() {
		return getResUrlBuf().toString();
	}

	/**
	 * 获得站点的资源URL。如：http://www.sina.com/res_base/sina_com_www
	 * 
	 * @return
	 */
	public StringBuilder getUserResUrlBuf() {
		return getResUrlBuf().append(SPT).append(RES_BASE).append(SPT).append(
				getResPath());
	}

	/**
	 * 获得站点的资源URL。如：http://www.sina.com/res_base/sina_com_www
	 * 
	 * @return
	 */
	public String getUserResUrl() {
		return getUserResUrlBuf().toString();
	}

	/**
	 * 获得系统资源URL。如：http://www.sian.com/front_res
	 * 
	 * 主要供前台模板使用的一些样式表、图片。
	 * 
	 * @return
	 */
	public String getSysResUrl() {
		return getResUrlBuf().append(SPT).append(RES_SYS).toString();
	}

	/**
	 * 获得上传根路径。如：http://www.sina.com/res_base/sina_com_www/upload
	 * 
	 * @return
	 */
	public StringBuilder getUploadUrlBuf() {
		return getUserResUrlBuf().append(SPT).append(UPLOAD_PATH);
	}

	/**
	 * 获得上传根路径。如：http://www.sina.com/res_base/sina_com_www/upload
	 * 
	 * @return
	 */
	public String getUploadUrl() {
		return getUploadUrlBuf().toString();
	}

	/**
	 * 获得用户相对根路径。如：/WEB-INF/user_base/ponyjava_com_www
	 * 
	 * @return
	 */
	public StringBuilder getUserRoot() {
		StringBuilder sb = new StringBuilder(USER_ROOT);
		sb.append(getResPath());
		return sb;
	}

	/**
	 * 获得模板相对路径。如：/WEB-INF/user_base/ponyjava_com_www/template
	 * 
	 * @return
	 */
	public StringBuilder getTplRoot() {
		return getUserRoot().append(SPT).append(TEMPLATE);
	}

	/**
	 * 获得模板绝对路径。如：f:/wangzhan/sina/WEB-INF/user_base/ponyjava_com_www/template
	 * 
	 * @param realRoot
	 * @return
	 */
	public StringBuilder getTplRootReal(String realRoot) {
		StringBuilder sb = new StringBuilder(realRoot);
		sb.append(getTplRoot());
		return sb;
	}

	/**
	 * 获得资源根路径。如：/res_base/sina_com_www
	 * 
	 * @return
	 */
	public StringBuilder getResRootBuf() {
		StringBuilder sb = new StringBuilder();
		sb.append(SPT).append(RES_BASE).append(SPT).append(getResPath());
		return sb;
	}

	/**
	 * 获得资源根路径。如：/res_base/sina_com_www
	 * 
	 * @return
	 */
	public String getResRoot() {
		return getResRootBuf().toString();
	}

	/**
	 * 获得上传根路径。如：/res_base/sina_com_www/upload
	 * 
	 * @return
	 */
	public StringBuilder getUploadRoot() {
		return getResRootBuf().append(SPT).append(UPLOAD_PATH);
	}

	/**
	 * 获得根站点。用于站群管理
	 * 
	 * @return
	 */
	public Website getRootWeb() {
		Website parentWeb = getParent();
		if (parentWeb == null) {
			return this;
		} else {
			return parentWeb.getRootWeb();
		}
	}

	/**
	 * 获得根站点ID
	 * 
	 * @return
	 */
	public Long getRootWebId() {
		Website root = getRootWeb();
		if (root != null) {
			return root.getId();
		} else {
			return null;
		}
	}

	/**
	 * 获取顶级域名。用于单点登录，如：.jeecms.com
	 * 
	 * @param withPoint
	 *            是否带点号 是：.jeecms.com；否：jeecms.com
	 * @return
	 */
	public String getTopDomain(boolean withPoint) {
		String topDomain = getBaseDomain();
		if (StringUtils.isBlank(topDomain)) {
			return getDomain();
		}
		if (withPoint) {
			return "." + topDomain;
		} else {
			return topDomain;
		}
	}

	/**
	 * 获得站点别名数组
	 * 
	 * @return
	 */
	public String[] getAlias() {
		return StringUtils.split(getDomainAlias(), ',');
	}

	public String getTreeName() {
		return getName();
	}

	public String getSelectTree() {
		return selectTree;
	}

	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}

	public Set<? extends SelectTree> getTreeChild() {
		if (treeChild != null) {
			return treeChild;
		} else {
			return getChild();
		}
	}

	public Set<? extends SelectTree> getTreeChildRaw() {
		return treeChild;
	}

	public SelectTree getTreeParent() {
		return getParent();
	}

	public Long getParentId() {
		Website parent = getParent();
		if (parent == null) {
			return null;
		} else {
			return parent.getId();
		}
	}

	public String getTreeCondition() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void setTreeChild(Set treeChild) {
		this.treeChild = treeChild;
	}

	/**
	 * 网站简称。
	 */
	public String getShortName() {
		String s = super.getShortName();
		if (StringUtils.isBlank(s)) {
			return getName();
		} else {
			return s;
		}
	}

	public Integer getPort() {
		return getGlobal().getPort();
	}

	public String getContextPath() {
		return getGlobal().getContextPath();
	}

	/**
	 * 下拉列表树
	 */
	private String selectTree;
	/**
	 * 树子节点
	 */
	private Set<Website> treeChild;
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Website () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Website (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Website (
		java.lang.Long id,
		com.jeecms.core.entity.Global global,
		java.lang.String domain,
		java.lang.String resPath,
		java.lang.Integer lft,
		java.lang.Integer rgt,
		java.lang.String name,
		java.util.Date createTime,
		java.lang.Boolean close) {

		super (
			id,
			global,
			domain,
			resPath,
			lft,
			rgt,
			name,
			createTime,
			close);
	}

	/* [CONSTRUCTOR MARKER END] */
}