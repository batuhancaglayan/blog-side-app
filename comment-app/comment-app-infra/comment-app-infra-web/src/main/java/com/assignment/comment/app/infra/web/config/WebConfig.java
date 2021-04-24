package com.assignment.comment.app.infra.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebConfig {

	@Bean
	public MessageSource messageSource()
	{
	    ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
	    bean.setBasenames("classpath:messages", "classpath:messages/exceptions");
	    bean.setDefaultEncoding("UTF-8");
	    return bean;
	}

	@Bean
	public LocalValidatorFactoryBean validator()
	{
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
}
