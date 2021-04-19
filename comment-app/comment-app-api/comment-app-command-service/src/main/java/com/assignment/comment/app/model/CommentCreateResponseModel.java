package com.assignment.comment.app.model;

import com.assignment.comment.app.api.common.enumeration.ProcessType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateResponseModel {

	private String processId;

	private ProcessType result;
}
