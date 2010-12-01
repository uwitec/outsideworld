package com.jeecms.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.auxiliary.entity.AuxiConfig;
import com.jeecms.auxiliary.manager.AuxiConfigMng;
import com.jeecms.core.PartBaseAction;

/**
 * ����ϵͳ��ǩ����
 * 
 * �ṩϵͳ���ţ�����ϵͳ���á�
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
