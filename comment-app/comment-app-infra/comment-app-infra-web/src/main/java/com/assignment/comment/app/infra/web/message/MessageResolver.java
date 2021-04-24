package com.assignment.comment.app.infra.web.message;

public interface MessageResolver {

	public static final String UNKNOWN_EXCEPTION_TEXT = "An Error Occured. Please Contact With System Administrator.";
	
	public String getMessageText(String messageKey);
}
