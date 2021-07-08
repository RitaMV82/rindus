package com.rindus.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
		
	/**
	 *  Method in charge to retrieve all posts
	 */
	public ResponseEntity<String> getAllPosts() {	
		return restTemplate.getForEntity(URL +"posts", String.class);		
	}
	
	/**
	 * Method in charge to retrieve a post by id 
	 */
	public ResponseEntity<Posts> getPostsById(String id) {		
		try {
			return restTemplate.getForEntity(URL + "posts/" + id, Posts.class);
		} catch (Exception e) {
			return null;
		}		
	}
	
	/**
	 * Method in charge to retrieve a new post
	 */
	public ResponseEntity<String> addNewPosts(Posts posts) {			
		  return restTemplate.postForEntity(URL +"posts", posts, String.class);
	}

	/**
	 * Method in charge to delete a posts
	 */
	public void deletePostsById(String id) {		
		 restTemplate.delete(URL + "posts/" + id, String.class);
	}

	/**
	 * Method in charge to retrieve all comments by postId
	 */
	public ResponseEntity<String> getCommentsByPostId(String id) {
		HttpHeaders headers = buildHeaders();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "comments/").queryParam("id", id);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
        return response;		
	}


	/**
	 * Method in charge to update a posts
	 * @return 
	 */
	public ResponseEntity<Posts> updatePostsById(String id, Posts posts) {		
		
		HttpHeaders headers = buildHeaders();
		buildRequestFactory();		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "posts/" + id);
		HttpEntity<Posts> requestEntity = new HttpEntity<Posts>(posts, headers);
		ResponseEntity<Posts> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, requestEntity,
				Posts.class);
		return response;
	}


	/**
	 * @throws Exception 
	 * 
	 */
	public ResponseEntity<Posts> modifyPosts(String id, Posts posts) {	

		HttpHeaders headers = buildHeaders();		
		buildRequestFactory();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "posts/" + id);		
		HttpEntity<Posts> requestEntity = new HttpEntity<Posts>(posts, headers);
		ResponseEntity<Posts> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, requestEntity,
				Posts.class);

		return response;
	}
	
	
	/**
	 * Build RequestFactory
	 */
	private void buildRequestFactory() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(5000);
		requestFactory.setReadTimeout(5000);
		restTemplate.setRequestFactory(requestFactory);
	}

	
	/**
	 * Build HttpHeaders
	 * @return
	 */
	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
}
