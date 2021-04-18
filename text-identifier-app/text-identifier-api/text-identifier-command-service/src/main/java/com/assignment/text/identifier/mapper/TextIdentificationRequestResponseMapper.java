package com.assignment.text.identifier.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.assignment.text.identifier.infra.mapper.CyclePreventiveContext;
import com.assignment.text.identifier.infra.mapper.TextIdentifierMapperConfig;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.assignment.text.identifier.model.TextIdentificationReqeustModel;

@Mapper(config = TextIdentifierMapperConfig.class)
public interface TextIdentificationRequestResponseMapper {

	public TextIdentificationProcessModel reqeustModelToProcessModel(TextIdentificationReqeustModel requestModel,
			@Context CyclePreventiveContext context);
}
