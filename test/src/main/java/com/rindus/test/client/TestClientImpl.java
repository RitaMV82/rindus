package com.rindus.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rindus.test.client.interfaces.TestClient;
import com.rindus.test.model.Posts;

@Component
public class TestClientImpl implements TestClient {

	private static final String URL = "http://jsonplaceholder.typicode.com/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public TestClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}	
	
	public ResponseEntity<String> getAllPosts() {	
		return restTemplate.getForEntity(URL +"posts", String.class);		
	}
	
	public ResponseEntity<Posts> getPostsById(String id) {		
		try {
			return restTemplate.getForEntity(URL + "posts/" + id, Posts.class);
		} catch (Exception e) {
			return null;
		}		
	}

	public ResponseEntity<String> addNewPosts(Posts posts) {			
		  return restTemplate.postForEntity(URL +"posts", posts, String.class);
	}

	
	public void deletePostsById(String id) {		
		 restTemplate.delete(URL + "posts/" + id, String.class);
	}

}
