package com.rindus.test.webservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rindus.test.Application;
import com.rindus.test.model.Posts;
import com.rindus.test.service.interfaces.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc // need this in Spring Boot test
class TestControllerTest {

	@InjectMocks
	private TestControllerImpl controller;

	@Mock
	private TestService testService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetAllPosts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/prueba/posts").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetCommentsByIdPosts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/prueba/{id}/comments",7).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllComments() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/prueba/{id}/comments", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getPostsById() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/prueba/posts/{id}", 1).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getPostsByIdNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/prueba/posts/{id}", 9999).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createPosts() throws Exception {
		Posts p1 = new Posts();
		p1.setUserId(500);
		p1.setId(500);

		mvc.perform(MockMvcRequestBuilders.post("/prueba/posts").content(asJsonString(p1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	@Test
	public void updatePosts() throws Exception  {
		Posts p1 = new Posts();
		p1.setUserId(500);
		p1.setId(500);
		
	    mvc.perform( MockMvcRequestBuilders.put("/prueba/posts/{id}", 2)
	      .content(asJsonString(p1))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void modifyPosts() throws Exception  {
		Posts p1 = new Posts();
		p1.setUserId(500);
		p1.setId(500);
		
	    mvc.perform( MockMvcRequestBuilders.post("/prueba/modify/{id}", 2)
	      .content(asJsonString(p1))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
	
	
	@Test
	public void deletePosts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/prueba/posts/{id}", 1) )
        .andExpect(status().isNoContent());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
