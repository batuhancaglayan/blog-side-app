package com.assignment.comment.app.repository;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.assignment.comment.app.dao.CommentDao;
import com.assignment.comment.app.exception.CommentItemNotFoundException;
import com.assignment.comment.app.infra.util.hash.date.DateTimeConverter;
import com.assignment.comment.app.model.data.CommentModel;
import com.assignment.comment.app.model.data.CommentTable;
import com.assignment.comment.app.process.TextIdentificationProcess;

@Component
public class CommentRepositoryImpl implements CommentRepository {

	private TextIdentificationProcess textIdentificationProcess;
	
	private CommentDao commentDao;

	public CommentRepositoryImpl(TextIdentificationProcess textIdentificationProcess, CommentDao commentDao) {
		this.textIdentificationProcess = textIdentificationProcess;
		this.commentDao = commentDao;
	}

	@Override
	public boolean createComment(CommentModel commentModel) {
		return this.textIdentificationProcess.startProcess(commentModel);
	}

	@Override
	public void softDeleteItem(String commentId) {
		
		CommentTable comment = this.commentDao.findOne(commentId);
		if (comment == null) {
			throw new CommentItemNotFoundException("Item not found with id: " + commentId);
		}
		
		if (comment.getIsActive() == 1) {			
			comment.setIsActive(0);
			comment.setUpdatedAt(DateTimeConverter.toEpoch(Instant.now()));
			this.commentDao.save(comment);
		}
	}
}
