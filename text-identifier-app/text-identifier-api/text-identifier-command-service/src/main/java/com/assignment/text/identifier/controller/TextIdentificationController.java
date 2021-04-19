package com.assignment.text.identifier.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.text.identifier.model.TextIdentificationReqeustModel;
import com.assignment.text.identifier.model.TextIdentificationResponseModel;
import com.assignment.text.identifier.service.TextIdentificationService;

@RestController
@RequestMapping("text/identification")
public class TextIdentificationController {

	private TextIdentificationService textIdentificationService;

	public TextIdentificationController(TextIdentificationService textIdentificationService) {
		this.textIdentificationService = textIdentificationService;
	}

	@PostMapping("/create")
	public ResponseEntity<TextIdentificationResponseModel> createTextIdentification(
			@Valid @RequestBody TextIdentificationReqeustModel textIdentificationRequestModel) {
		return ResponseEntity.ok()
				.body(textIdentificationService.createTextIdentification(textIdentificationRequestModel));
	}
}
