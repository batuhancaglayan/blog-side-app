package com.assignment.text.identifier.service;

import org.springframework.stereotype.Component;

import com.assignment.text.identifier.dao.TextIdentificationProcess;
import com.assignment.text.identifier.model.TextIdentificationProcessModel;
import com.assignment.text.identifier.model.TextIdentificationReqeustModel;
import com.assignment.text.identifier.model.TextIdentificationResponseModel;

@Component
public class TextIdentificationServiceImpl implements TextIdentificationService {

	private TextIdentificationProcess textIdentificationProcess;

	public TextIdentificationServiceImpl(TextIdentificationProcess textIdentificationProcess) {
		this.textIdentificationProcess = textIdentificationProcess;
	}

	@Override
	public TextIdentificationResponseModel createTextIdentification(
			TextIdentificationReqeustModel textIdentificationRequestModel) {

		boolean result = this.textIdentificationProcess.startProcess(new TextIdentificationProcessModel(
				textIdentificationRequestModel.getEmail(), textIdentificationRequestModel.getComment()));
		return null;
	}
}
