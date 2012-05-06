package com.notice.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.notice.model.Mes;

public class MesConvertor implements MessageConverter {

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		if(!(message instanceof ObjectMessage)){
			throw new MessageConversionException("Message isn't a BytesMessage");
		}
		ObjectMessage objMessage = (ObjectMessage)message;
		return objMessage.getObject();
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException,
			MessageConversionException {
		if(!(object instanceof Mes)){
			throw new MessageConversionException("Object isn't a Mes");
		}
		Mes event = (Mes)object;
		ObjectMessage message = session.createObjectMessage(event);
		return message;
	}

}
