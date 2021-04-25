package com.assignment.comment.app.unittest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.comment.app.model.dto.CommentCreateRequestModel;
import com.assignment.comment.app.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentCommandControllerUnitTest {

	private static final String email = "example@gmail.com";

	private static final String comment = "This is my comment";

	private static final String username = "assigment-user";

	private static final String password = "assigment-pass";

	private static final String userRole = "TEMP-SUPER_USER";

	@MockBean
	private CommentService commentService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	/**
	 * This method tests existence of email field in the request body.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCommentCheckEmailExistence() throws Exception {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel(null, comment);

		mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("error"))).andExpect(jsonPath("$.data", IsNull.nullValue()))
				.andExpect(jsonPath("$.errors[0].title", is("email")))
				.andExpect(jsonPath("$.errors[0].type", is("field-error")));
	}

	/**
	 * This method checks for malformed email field in the request body.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCommentCheckValidEmail() throws Exception {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel("malformed", comment);

		mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("error"))).andExpect(jsonPath("$.data", IsNull.nullValue()))
				.andExpect(jsonPath("$.errors[0].title", is("email")))
				.andExpect(jsonPath("$.errors[0].type", is("field-error")));
	}

	/**
	 * This method tests existence of comment field in the request body.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCommentCheckCommentExistence() throws Exception {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel(email, null);

		mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("error"))).andExpect(jsonPath("$.data", IsNull.nullValue()))
				.andExpect(jsonPath("$.errors[0].title", is("comment")))
				.andExpect(jsonPath("$.errors[0].type", is("field-error")));
	}

	/**
	 * This method checks for empty comment field in the request body.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCommentCheckValidComment() throws Exception {
		CommentCreateRequestModel requestModel = new CommentCreateRequestModel(email, "");

		mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("error"))).andExpect(jsonPath("$.data", IsNull.nullValue()))
				.andExpect(jsonPath("$.errors[0].title", is("comment")))
				.andExpect(jsonPath("$.errors[0].type", is("field-error")));

	}
}
