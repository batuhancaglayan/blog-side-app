package com.assignment.text.identifier.service;

import com.assignment.text.identifier.model.TextIdentificationReqeustModel;
import com.assignment.text.identifier.model.TextIdentificationResponseModel;

public interface TextIdentificationService {

	public TextIdentificationResponseModel createTextIdentification(
			TextIdentificationReqeustModel textIdentificationRequestModel);
}
