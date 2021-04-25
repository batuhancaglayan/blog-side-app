package com.assignment.comment.app.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.comment.app.dao.CommentDao;
import com.assignment.comment.app.exception.CommentItemNotFoundException;
import com.assignment.comment.app.infra.aws.sqs.SqsClient;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.model.data.CommentTable;
import com.assignment.comment.app.repository.CommentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryUnitTest {

	private static final String email = "example@gmail.com";

	private static final String comment = "This is my comment";

	private static final String commentId = "844a376f-424b-4bb2-9e47-a154329e9989";

	@Autowired
	private CommentRepository commentRepository;

	@MockBean
	private CommentDao commentDao;

	@MockBean
	private SqsClient sqsClient;

	/*
	 * This method tests create comment functionality for existing model on
	 * repository layer without sqs exception.
	 */
	@Test
	public void testCreateCommentValid() throws JsonProcessingException {
		CommentModel commentModel = new CommentModel();

		Mockito.doNothing().when(sqsClient).send(any(String.class), any(CommentModel.class));

		boolean result = commentRepository.createComment(commentModel);

		assertTrue(result);
		verify(sqsClient, times(1)).send(any(String.class), any(CommentModel.class));
	}

	/*
	 * This method tests create comment functionality for existing model on
	 * repository layer with sqs exception.
	 */
	@Test
	public void testCreateCommentWithException() throws JsonProcessingException {
		CommentModel commentModel = new CommentModel();

		Mockito.doThrow(new RuntimeException("test")).when(sqsClient).send(any(String.class), any(CommentModel.class));

		boolean result = commentRepository.createComment(commentModel);

		assertFalse(result);
		verify(sqsClient, times(1)).send(any(String.class), any(CommentModel.class));
	}

	/*
	 * This method tests the soft delete functionality for existing model on
	 * repository layer.
	 */
	@Test
	public void testRemoveCommentValid() {
		CommentTable commentTable = new CommentTable();
		commentTable.setEmail(email);
		commentTable.setComment(comment);
		commentTable.setIsActive(1);

		when(commentDao.findOne(commentId)).thenReturn(commentTable);
		Mockito.doNothing().when(commentDao).save(any(CommentTable.class));

		commentRepository.softDeleteItem(commentId);

		assertEquals(commentTable.getIsActive(), 0);
		verify(commentDao, times(1)).findOne(commentId);
		verify(commentDao, times(1)).save(any(CommentTable.class));
	}

	/*
	 * This method tests the soft delete functionality for not existing model on
	 * repository layer.
	 */
	@Test(expected = CommentItemNotFoundException.class)
	public void testRemoveCommentNotExist() {
		when(commentDao.findOne(commentId)).thenReturn(null);

		commentRepository.softDeleteItem(commentId);

		verify(commentDao, times(1)).findOne(commentId);
		verify(commentDao, times(0)).save(any(CommentTable.class));
	}
}
