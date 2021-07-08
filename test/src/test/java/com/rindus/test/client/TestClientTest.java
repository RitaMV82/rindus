package com.rindus.test.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rindus.test.model.Posts;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestClientTest {
	private static final String URL = "http://jsonplaceholder.typicode.com/";
	
	@Mock
	RestTemplate restTemplate;
	
	@Mock
	HttpHeaders headers;

	
	@InjectMocks
	private TestClientImpl client;	
	
	@Test
	void shoudlReturnAllPosts() {
		//prepare
	    when(restTemplate.getForEntity(URL +"posts", String.class)).thenReturn(new ResponseEntity<String>("Body", HttpStatus.OK));	    
	    //execute
	    client = new TestClientImpl(restTemplate);		 
		ResponseEntity<String> result = client.getAllPosts();
		//results
		assertNotNull(result.getBody());
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void shoudlReturnPostsById() {
		//prepare
		Integer id = 1;			
		Posts posts = new Posts();
		posts.setUserId(id);
		posts.setTitle("tittle");
		when(restTemplate.getForEntity(URL + "posts/" + id, Posts.class)).thenReturn(new ResponseEntity<Posts>(posts, HttpStatus.OK));	    
		//execute
		client = new TestClientImpl(restTemplate);
		ResponseEntity<Posts> result = client.getPostsById(String.valueOf(id));
		//results
		assertNotNull(result.getBody());
		assertEquals(result.getBody().getUserId(), id);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void shoudlReturnNewPosts() {
		//prepare
		Posts posts = new Posts();
		posts.setId(1000);
		//execute
		when(restTemplate.postForEntity(URL +"posts", posts, String.class)).thenReturn(new ResponseEntity<String>("Body", HttpStatus.CREATED));
		client = new TestClientImpl(restTemplate);
		ResponseEntity<String> entity = client.addNewPosts(posts);
		//results
		assertNotNull(entity);	
		assertEquals(entity.getBody(), "Body");
		assertEquals(entity.getStatusCode(), HttpStatus.CREATED);		
	}	

}
