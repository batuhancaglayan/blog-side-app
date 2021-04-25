package com.assignment.comment.app.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.comment.app.dao.CommentDao;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.infra.util.hash.MD5Util;
import com.assignment.comment.app.mapper.CommentDtoDataModelMapper;
import com.assignment.comment.app.model.data.CommentDocument;
import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchResponseModel;
import com.assignment.comment.app.service.CommentService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceUnitTest {

	private static final String email = "example@gmail.com";

	private static final String comment = "This is my comment";

	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentDao commentDao;

	@SpyBean
	private CommentDtoDataModelMapper commentDtoDataModelMapper;

	/*
	 * This method tests just the email search functionality on service layer.
	 */
	@Test
	public void testFindByEmailValid() {

		List<CommentDocument> commentDocumentList = new ArrayList<>();
		CommentDocument commentDocument = new CommentDocument();
		commentDocument.setEmail(email);
		commentDocument.setComment(comment);
		commentDocument.setCreatedAt(Instant.now());
		commentDocument.setIsActive(1);
		commentDocumentList.add(commentDocument);
		CommentSearchRequestModel requestModel = new CommentSearchRequestModel();
		requestModel.setEmail(email);
		requestModel.setSize(10);
		String calculatedHash = MD5Util.hash(requestModel.getEmail());

		when(commentDao.findByEmail(calculatedHash, requestModel.getEmail(), requestModel.getFrom(),
				requestModel.getSize())).thenReturn(commentDocumentList);

		List<CommentSearchResponseModel> result = commentService.findByEmail(requestModel);
		CommentSearchResponseModel resultObject = result.get(0);

		assertThat(result).isNotNull().isNotEmpty();
		assertThat(resultObject.getComment().equals(comment));
		assertThat(resultObject.getEmail().equals(email));
		verify(commentDao, times(1)).findByEmail(calculatedHash, requestModel.getEmail(), requestModel.getFrom(),
				requestModel.getSize());
		verify(commentDtoDataModelMapper, times(1)).dataModelToDto(any(CommentDocument.class),
				any(CyclePreventiveContext.class));
	}

	/*
	 * This method tests the email and comment search functionality on service
	 * layer.
	 */
	@Test
	public void testFindByEmailAndSearchInCommentValid() {

		List<CommentDocument> commentDocumentList = new ArrayList<>();
		CommentDocument commentDocument = new CommentDocument();
		commentDocument.setEmail(email);
		commentDocument.setComment(comment);
		commentDocument.setCreatedAt(Instant.now());
		commentDocument.setIsActive(1);
		commentDocumentList.add(commentDocument);
		CommentSearchKeywordRequestModel requestModel = new CommentSearchKeywordRequestModel();
		requestModel.setEmail(email);
		requestModel.setSize(10);
		requestModel.setKeyword("valid");
		String calculatedHash = MD5Util.hash(requestModel.getEmail());

		when(commentDao.findByEmailAndSearchInComment(calculatedHash, requestModel.getEmail(),
				requestModel.getKeyword(), requestModel.getFrom(), requestModel.getSize()))
						.thenReturn(commentDocumentList);

		List<CommentSearchResponseModel> result = commentService.findByEmailAndSearchInComment(requestModel);
		CommentSearchResponseModel resultObject = result.get(0);

		assertThat(result).isNotNull().isNotEmpty();
		assertThat(resultObject.getComment().equals(comment));
		assertThat(resultObject.getEmail().equals(email));
		verify(commentDao, times(1)).findByEmailAndSearchInComment(calculatedHash, requestModel.getEmail(),
				requestModel.getKeyword(), requestModel.getFrom(), requestModel.getSize());
		verify(commentDtoDataModelMapper, times(1)).dataModelToDto(any(CommentDocument.class),
				any(CyclePreventiveContext.class));
	}
}
