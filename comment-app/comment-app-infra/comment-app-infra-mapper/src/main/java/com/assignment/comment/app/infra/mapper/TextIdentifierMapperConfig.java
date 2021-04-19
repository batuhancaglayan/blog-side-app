package com.assignment.comment.app.infra.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig(uses = TimeMapper.class, componentModel = "spring")
public interface TextIdentifierMapperConfig {
}
