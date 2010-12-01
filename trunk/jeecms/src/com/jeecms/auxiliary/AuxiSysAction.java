package com.jeecms.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.auxiliary.entity.AuxiConfig;
import com.jeecms.auxiliary.manager.AuxiConfigMng;
import com.jeecms.core.JeeCoreAction;

/**
 * 辅助系统的action祖先。
 * <p>
 * 处理AuxiConfig配置AuxiConfig配置
 * </p>
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public abstract class AuxiSysAction extends JeeCoreAction {
	public AuxiConfig getConfig() {
		return auxiConfigMng.findById(getWebId());
	}

	@Autowired
	protected AuxiConfigMng auxiConfigMng;
}
