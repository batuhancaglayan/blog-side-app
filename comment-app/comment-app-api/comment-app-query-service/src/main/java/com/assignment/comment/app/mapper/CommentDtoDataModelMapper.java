package com.assignment.comment.app.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.assignment.comment.app.infra.mapper.CommentAppMapperConfig;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.model.data.CommentDocument;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;

@Mapper(config = CommentAppMapperConfig.class)
public interface CommentDtoDataModelMapper {

	public CommentSearchResponseModel dataModelToDto(CommentDocument commentDocument, @Context CyclePreventiveContext context);
	
	public List<CommentSearchResponseModel> dataModelToDto(List<CommentDocument> commentDocument, @Context CyclePreventiveContext context);
}
