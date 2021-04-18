//package com.assignment.text.identifier.dao;
//
//import java.util.Date;
//
//import org.springframework.stereotype.Component;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.assignment.text.identifier.model.TextIdentificationProcessModel;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//public class TextIdentificationS3Process implements TextIdentificationProcess {
//
//	private AmazonS3 amazonS3;
//	
//	private ObjectMapper objectMapper;
//
//	public TextIdentificationS3Process(AmazonS3 amazonS3, ObjectMapper objectMapper) {
//		this.amazonS3 = amazonS3;
//		this.objectMapper = objectMapper;
//	}
//
//	@Override
//	public boolean startProcess(TextIdentificationProcessModel textIdentificationProcessModel) {
//		
//		try {
//			String content = objectMapper.writeValueAsString(new TextIdentificationProcessModel("bbbb", "adwdawdafgwagaw"));
//			this.amazonS3.putObject("text-identification/reqeust", "deneme_" + new Date(), content);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//	
//		return false;
//	}
//}
