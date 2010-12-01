package com.jeecms.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.auxiliary.entity.AuxiConfig;
import com.jeecms.auxiliary.manager.AuxiConfigMng;
import com.jeecms.core.JeeCoreAjaxAction;

/**
 * 辅助系统AJAX的action祖先。
 * <p>
 * 处理AuxiConfig配置
 * </p>
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class AuxiAjaxAction extends JeeCoreAjaxAction {
	public AuxiConfig getConfig() {
		return auxiConfigMng.findById(getWebId());
	}

	@Autowired
	protected AuxiConfigMng auxiConfigMng;
}