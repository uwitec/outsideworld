package com.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jms.core.JmsTemplate;

import com.notice.model.Mes;
import com.notice.plugin.ChannelPlugin;
import com.notice.plugin.PluginChain;
import com.util.SpringFactory;

/**
 * 定时启动查看active-mq中有没有消息，如果有则进行发送
 * 
 * @author fangxia722
 * 
 */
public class NotifyService {
	private PluginChain pluginChains;
	private JmsTemplate jmsTemplate;

	public PluginChain getPluginChains() {
		return pluginChains;
	}

	public void setPluginChains(PluginChain pluginChains) {
		this.pluginChains = pluginChains;
	}

	public void process(Mes m) {
			for (ChannelPlugin plugin : pluginChains.getPlugins()) {
				if (plugin.accept(m.getProtocal())) {
					plugin.notify(m);
				}
			}
	}

	public void notify(Mes message) {
		jmsTemplate.convertAndSend(message);
	}
	
	public void receive() {
		Mes result = null;
		do{
		result = (Mes)jmsTemplate.receiveAndConvert();
		System.out.println(result);
		if(result!=null){
			process(result);
		}
		}while(result!=null);
	}

	/**
	 * 定时启动程序
	 */
	public void run() {

	}

	public static void main(String[] args) {
		NotifyService service = SpringFactory.getBean("notifyService");
//		for (int i = 0; i < 15; i++) {
//			Mes m = new Mes();
//			m.setTitle("我是王振东！");
//			m.setContent("我是人！");
//			m.setProtocal("email:");
//			m.setTo("fangxia722@163.com");
//			service.notify(m);
//		}
		service.receive();
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
