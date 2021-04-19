package com.assignment.text.identifier.infra.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig(uses = TimeMapper.class, componentModel = "spring")
public interface TextIdentifierMapperConfig {
}
