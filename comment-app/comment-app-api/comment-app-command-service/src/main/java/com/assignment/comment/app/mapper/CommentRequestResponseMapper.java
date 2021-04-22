package com.assignment.comment.app.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.assignment.comment.app.api.common.enumeration.ProcessType;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.infra.mapper.CommentAppMapperConfig;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.model.dto.CommentCreateResponseModel;

@Mapper(config = CommentAppMapperConfig.class)
public interface CommentRequestResponseMapper {

	public CommentModel reqeustModelToProcessModel(CommentCreateRequestModel requestModel,
			@Context CyclePreventiveContext context);

	@Mapping(source = "id", target = "processId")
	public CommentCreateResponseModel processModelToResponseModel(CommentModel processModel,
			@Context boolean state, @Context CyclePreventiveContext context);

	@AfterMapping
	default void convert(@MappingTarget CommentCreateResponseModel target, @Context boolean state) {
		target.setResult(state ? ProcessType.IN_PROCESS : ProcessType.DENIED);
	}
}
