package com.notice.plugin;

import java.util.List;

public class PluginChain {
    private List<ChannelPlugin> plugins;

	public List<ChannelPlugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<ChannelPlugin> plugins) {
		this.plugins = plugins;
	}
}
