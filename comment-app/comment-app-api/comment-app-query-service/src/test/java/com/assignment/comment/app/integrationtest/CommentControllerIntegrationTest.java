package com.assignment.comment.app.integrationtest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.comment.app.config.TestResourcesConfig;
import com.assignment.comment.app.model.dto.CommentSearchKeywordRequestModel;
import com.assignment.comment.app.model.dto.CommentSearchRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

	private static final String email = "batuhancaglayan1@gmail.com";

	private static final String username = "assigment-user";

	private static final String password = "assigment-pass";

	private static final String userRole = "TEMP-SUPER_USER";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Rule
	public MockServerRule mockServerRule = new MockServerRule(this, 9200);

	private MockServerClient mockClient;

	private ElasticsearchMockClient esMockClient;

	@Autowired
	private TestResourcesConfig testResources;

	@Before
	public void setup() {
		this.esMockClient = new ElasticsearchMockClient(mockClient, testResources);
	}

	/*
	 * This method tests email-search request functionality with valid request.
	 */
	@Test
	public void testEmailSearchValid() throws Exception {
		CommentSearchRequestModel requestModel = new CommentSearchRequestModel();
		requestModel.setEmail(email);
		requestModel.setSize(10);

		mockMvc.perform(post("/comment/email").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("success"))).andExpect(jsonPath("$.errors", IsNull.nullValue()))
				.andExpect(jsonPath("$.data.length()", is(3))).andExpect(jsonPath("$.data[0].email", is(email)));

	}

	/*
	 * This method tests valid keyword-search request functionality.
	 */
	@Test
	public void testKeywordSearchValid() throws Exception {
		CommentSearchKeywordRequestModel requestModel = new CommentSearchKeywordRequestModel();
		requestModel.setEmail(email);
		requestModel.setKeyword("deneme");
		requestModel.setSize(10);

		mockMvc.perform(post("/comment/email-keyword").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("success"))).andExpect(jsonPath("$.data.length()", is(1)))
				.andExpect(jsonPath("$.data[0].email", is(email)))
				.andExpect(jsonPath("$.data[0].comment", containsString("deneme")))
				.andExpect(jsonPath("$.errors", IsNull.nullValue()));

	}

	/*
	 * This method tests keyword-search request functionality with no matching
	 * result.
	 */
	@Test
	public void testKeywordSearchNoMatch() throws Exception {
		CommentSearchKeywordRequestModel requestModel = new CommentSearchKeywordRequestModel();
		requestModel.setEmail(email);
		requestModel.setKeyword("flower");
		requestModel.setSize(10);

		mockMvc.perform(post("/comment/email-keyword").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).with(user(username).password(password).roles(userRole))
				.content(mapper.writeValueAsString(requestModel))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("success"))).andExpect(jsonPath("$.errors", IsNull.nullValue()))
				.andExpect(jsonPath("$.data.length()", is(0)));

	}
}
