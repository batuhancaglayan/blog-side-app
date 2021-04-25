package com.assignment.comment.app.service;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.mapper.CommentRequestResponseMapper;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.model.dto.CommentCreateResponseModel;
import com.assignment.comment.app.repository.CommentRepository;

@Component
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;

	private CommentRequestResponseMapper commentRequestResponseMapper;

	public CommentServiceImpl(CommentRepository commentRepository, CommentRequestResponseMapper commentRequestResponseMapper) {
		this.commentRepository = commentRepository;
		this.commentRequestResponseMapper = commentRequestResponseMapper;
	}

	@Override
	public CommentCreateResponseModel createComment(CommentCreateRequestModel commentCreateRequestModel) {
		
		CommentModel processModel = this.commentRequestResponseMapper
				.reqeustModelToProcessModel(commentCreateRequestModel, new CyclePreventiveContext());

		boolean result = this.commentRepository.createComment(processModel);

		return this.commentRequestResponseMapper.processModelToResponseModel(processModel, result,
				new CyclePreventiveContext());
	}

	@Override
	public void removeComment(String commentId) {
		this.commentRepository.softDeleteItem(commentId);
	}
}
