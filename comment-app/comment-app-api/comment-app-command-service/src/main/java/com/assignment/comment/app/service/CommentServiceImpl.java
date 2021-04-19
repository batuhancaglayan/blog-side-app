package com.assignment.comment.app.service;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.dao.CommentDoa;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.mapper.CommentRequestResponseMapper;
import com.assignment.comment.app.model.CommentCreateRequestModel;
import com.assignment.comment.app.model.CommentCreateResponseModel;
import com.assignment.comment.app.model.CommentModel;

@Component
public class CommentServiceImpl implements CommentService {

	private CommentDoa commentDoa;

	private CommentRequestResponseMapper commentRequestResponseMapper;

	public CommentServiceImpl(CommentDoa commentDoa, CommentRequestResponseMapper commentRequestResponseMapper) {
		this.commentDoa = commentDoa;
		this.commentRequestResponseMapper = commentRequestResponseMapper;
	}

	@Override
	public CommentCreateResponseModel createComment(CommentCreateRequestModel commentCreateRequestModel) {
		
		CommentModel processModel = this.commentRequestResponseMapper
				.reqeustModelToProcessModel(commentCreateRequestModel, new CyclePreventiveContext());

		boolean result = this.commentDoa.createComment(processModel);

		return this.commentRequestResponseMapper.processModelToResponseModel(processModel, result,
				new CyclePreventiveContext());
	}

	@Override
	public void removeComment(String commentId) {
		this.commentDoa.softDeleteItem(commentId);
	}
}
