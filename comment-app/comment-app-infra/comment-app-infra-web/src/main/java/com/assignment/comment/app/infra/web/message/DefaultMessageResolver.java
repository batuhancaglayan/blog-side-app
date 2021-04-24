package com.assignment.comment.app.infra.web.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefaultMessageResolver implements MessageResolver {

	private MessageSource messageSource;

	public DefaultMessageResolver(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public String getMessageText(String messageKey) {

		String message = MessageResolver.UNKNOWN_EXCEPTION_TEXT;

		try {
			// TODO: can be take locale from request LOCALE. (next)
			message = this.messageSource.getMessage(messageKey, null, Locale.ENGLISH);
		} catch (Exception e) {
			log.warn("Message: " + messageKey + " not founded");
		}

		return message;
	}

}
