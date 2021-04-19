package com.assignment.text.identifier.service;

import org.springframework.stereotype.Component;

import com.assignment.text.identifier.dao.TextIdentificationProcess;
import com.assignment.text.identifier.infra.mapper.CyclePreventiveContext;
import com.assignment.text.identifier.mapper.TextIdentificationRequestResponseMapper;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.assignment.text.identifier.model.TextIdentificationReqeustModel;
import com.assignment.text.identifier.model.TextIdentificationResponseModel;

@Component
public class TextIdentificationServiceImpl implements TextIdentificationService {

	private TextIdentificationProcess textIdentificationProcess;

	private TextIdentificationRequestResponseMapper textIdentificationMapper;

	public TextIdentificationServiceImpl(TextIdentificationProcess textIdentificationProcess,
			TextIdentificationRequestResponseMapper textIdentificationMapper) {
		this.textIdentificationProcess = textIdentificationProcess;
		this.textIdentificationMapper = textIdentificationMapper;
	}

	@Override
	public TextIdentificationResponseModel createTextIdentification(
			TextIdentificationReqeustModel textIdentificationRequestModel) {

		TextIdentificationProcessModel processModel = this.textIdentificationMapper
				.reqeustModelToProcessModel(textIdentificationRequestModel, new CyclePreventiveContext());
		
		boolean result = this.textIdentificationProcess.startProcess(processModel);
		
		return this.textIdentificationMapper.processModelToResponseModel(processModel, result,  new CyclePreventiveContext());
	}
}
