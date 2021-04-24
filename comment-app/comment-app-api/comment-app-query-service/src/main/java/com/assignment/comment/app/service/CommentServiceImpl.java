package com.assignment.comment.app.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.dao.CommentDao;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.infra.util.hash.MD5Util;
import com.assignment.comment.app.mapper.CommentDtoDataModelMapper;
import com.assignment.comment.app.model.data.CommentDocument;
import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;

@Component
public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;

	private CommentDtoDataModelMapper commentDtoDataModelMapper;

	public CommentServiceImpl(CommentDao commentDao, CommentDtoDataModelMapper commentDtoDataModelMapper) {
		this.commentDao = commentDao;
		this.commentDtoDataModelMapper = commentDtoDataModelMapper;
	}

	@Override
	public List<CommentSearchResponseModel> findByEmail(CommentSearchRequestModel requestModel) {

		String email = requestModel.getEmail();
		String index = MD5Util.hash(email);

		List<CommentDocument> result = this.commentDao.findByEmail(index, email,
				requestModel.getFrom(), requestModel.getSize());

		return this.commentDtoDataModelMapper.dataModelToDto(result, new CyclePreventiveContext());
	}

	@Override
	public List<CommentSearchResponseModel> findByEmailAndSearchInComment(
			CommentSearchKeywordRequestModel requestModel) {

		String email = requestModel.getEmail();
		String index = MD5Util.hash(email);

		List<CommentDocument> result = this.commentDao.findByEmailAndSearchInComment(index, email,
				requestModel.getKeyword(), requestModel.getFrom(), requestModel.getSize());

		return this.commentDtoDataModelMapper.dataModelToDto(result, new CyclePreventiveContext());
	}

}
