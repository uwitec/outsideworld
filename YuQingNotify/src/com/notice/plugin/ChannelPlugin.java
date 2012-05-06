package com.notice.plugin;

import com.notice.model.Mes;
/**
 * @author fangxia722
 * 
 */
public interface ChannelPlugin {

	public boolean accept(String protocal);

	public boolean notify(Mes message);

}