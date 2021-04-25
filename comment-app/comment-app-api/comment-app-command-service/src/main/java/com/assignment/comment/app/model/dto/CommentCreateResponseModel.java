package com.assignment.comment.app.model.dto;

import com.assignment.comment.app.api.common.enumeration.ProcessType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentCreateResponseModel {

	private String processId;

	private ProcessType result;
}
