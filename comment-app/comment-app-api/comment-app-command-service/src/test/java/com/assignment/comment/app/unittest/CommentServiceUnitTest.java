package com.assignment.comment.app.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.comment.app.api.common.enumeration.ProcessType;
import com.assignment.comment.app.infra.mapper.CyclePreventiveContext;
import com.assignment.comment.app.mapper.CommentRequestResponseMapper;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.model.dto.CommentCreateResponseModel;
import com.assignment.comment.app.repository.CommentRepository;
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
	private CommentRepository commentRepository;

	@SpyBean
	private CommentRequestResponseMapper commentRequestResponseMapper;

	/*
	 * This method tests successful create comment functionality on service layer.
	 */
	@Test
	public void testCreateCommentSuccessfull() {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel(email, comment);

		when(commentRepository.createComment(any(CommentModel.class))).thenReturn(true);

		CommentCreateResponseModel result = commentService.createComment(requestModel);

		assertThat(result).isNotNull();
		assertThat(result.getProcessId()).isNotNull();
		assertEquals(result.getResult(), ProcessType.IN_PROCESS);
		verify(commentRepository, times(1)).createComment(any(CommentModel.class));
		verify(commentRequestResponseMapper, times(1)).reqeustModelToProcessModel(any(CommentCreateRequestModel.class),
				any(CyclePreventiveContext.class));
		verify(commentRequestResponseMapper, times(1)).processModelToResponseModel(any(CommentModel.class),
				any(boolean.class), any(CyclePreventiveContext.class));
	}

	/*
	 * This method tests unsuccessful create comment functionality on service layer.
	 */
	@Test
	public void testCreateCommentUnsuccessfull() {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel(email, comment);

		when(commentRepository.createComment(any(CommentModel.class))).thenReturn(false);

		CommentCreateResponseModel result = commentService.createComment(requestModel);

		assertThat(result).isNotNull();
		assertThat(result.getProcessId()).isNotNull();
		assertEquals(result.getResult(), ProcessType.DENIED);
		verify(commentRepository, times(1)).createComment(any(CommentModel.class));
		verify(commentRequestResponseMapper, times(1)).reqeustModelToProcessModel(any(CommentCreateRequestModel.class),
				any(CyclePreventiveContext.class));
		verify(commentRequestResponseMapper, times(1)).processModelToResponseModel(any(CommentModel.class),
				any(boolean.class), any(CyclePreventiveContext.class));
	}

	/*
	 * This method tests the remove comment functionality on service layer.
	 */
	@Test
	public void testRemoveComment() {
		String identifier = UUID.randomUUID().toString();

		commentService.removeComment(identifier);

		verify(commentRepository, times(1)).softDeleteItem(identifier);
	}
}
