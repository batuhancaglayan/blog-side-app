package com.assignment.comment.app.dao;

import java.util.List;

import com.assignment.comment.app.model.data.CommentDocument;

public interface CommentDao {

	public List<CommentDocument> findByEmail(String index, String email);
	
	public List<CommentDocument> findByEmailAndSearchInComment(String index, String email, String keyword);
}
