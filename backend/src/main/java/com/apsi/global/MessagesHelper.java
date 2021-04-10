package com.apsi.global;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessagesHelper {

	final private MessageSource messageSource;


	public String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, String arg0) {
		Object[] array = {arg0};
		return messageSource.getMessage(key, array, LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, String arg0, String arg1) {
		Object[] array = {arg0, arg1};
		return messageSource.getMessage(key, array, LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, String arg0, String arg1, String arg2) {
		Object[] array = {arg0, arg1, arg2};
		return messageSource.getMessage(key, array, LocaleContextHolder.getLocale());
	}

}
