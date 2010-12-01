package com.jeecms.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.auxiliary.entity.AuxiConfig;
import com.jeecms.auxiliary.manager.AuxiConfigMng;
import com.jeecms.core.PartBaseAction;

/**
 * 辅助系统标签基类
 * 
 * 提供系统代号，辅助系统设置。
 * 
 * @author liufang
 * 
 */
public abstract class AuxiPartAction extends PartBaseAction {
	public AuxiConfig getConfig() {
		return auxiConfigMng.findById(getWebId());
	}

	protected String getSysType() {
		return Constants.AUXILIARY_SYS;
	}

	@Autowired
	protected AuxiConfigMng auxiConfigMng;
}
