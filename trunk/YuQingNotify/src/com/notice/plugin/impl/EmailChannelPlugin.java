package com.notice.plugin.impl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.notice.model.Mes;
import com.notice.plugin.ChannelPlugin;

/**
 * @author zhdwang
 * 
 */
public class EmailChannelPlugin extends JavaMailSenderImpl implements
		ChannelPlugin {

	private final static Log logger = LogFactory
			.getLog(EmailChannelPlugin.class);

	private String from = null;

	public void setFrom(String from) {
		this.from = from;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.esbzone.notice.channelplugin.ChannelPlugin#accept(java.lang.String)
	 */
	public boolean accept(String protocal) {
		if (protocal != null && protocal.startsWith("email:")) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.esbzone.notice.channelplugin.ChannelPlugin#notify(java.lang.String)
	 */
	public boolean notify(Mes message) {
		if(message==null||StringUtils.isBlank(message.getTo())){
			return false;
		}
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(message.getTo());
		mail.setSubject(message.getTitle());
		mail.setText(message.getContent());
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
		send(mail);
		if (logger.isInfoEnabled()) {
			logger.info("Send Email sucess , to : " + message.getTo()
					+ " | title : " + message.getTitle());
		}
		return true;
	}
}
