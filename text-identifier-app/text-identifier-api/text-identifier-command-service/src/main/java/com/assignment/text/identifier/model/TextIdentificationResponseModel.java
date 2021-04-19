package com.assignment.text.identifier.model;

import com.assignment.text.identifier.api.common.enumeration.ProcessType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextIdentificationResponseModel {

	private String processId;

	private ProcessType result;
}
