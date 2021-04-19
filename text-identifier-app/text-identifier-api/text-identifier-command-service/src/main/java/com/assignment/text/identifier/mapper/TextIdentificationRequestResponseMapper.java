package com.assignment.text.identifier.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.assignment.text.identifier.api.common.enumeration.ProcessType;
import com.assignment.text.identifier.infra.mapper.CyclePreventiveContext;
import com.assignment.text.identifier.infra.mapper.TextIdentifierMapperConfig;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.assignment.text.identifier.model.TextIdentificationReqeustModel;
import com.assignment.text.identifier.model.TextIdentificationResponseModel;

@Mapper(config = TextIdentifierMapperConfig.class)
public interface TextIdentificationRequestResponseMapper {

	public TextIdentificationProcessModel reqeustModelToProcessModel(TextIdentificationReqeustModel requestModel,
			@Context CyclePreventiveContext context);

	@Mapping(source = "id", target = "processId")
	public TextIdentificationResponseModel processModelToResponseModel(TextIdentificationProcessModel processModel,
			@Context boolean state, @Context CyclePreventiveContext context);

	@AfterMapping
	default void convert(@MappingTarget TextIdentificationResponseModel target, @Context boolean state) {
		target.setResult(state ? ProcessType.IN_PROCESS : ProcessType.DENIED);
	}
}
