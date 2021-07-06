package com.rindus.test.client.interfaces;

import org.springframework.http.ResponseEntity;

import com.rindus.test.model.Posts;

public interface TestClient {

	 ResponseEntity<String> getAllPosts();
	 
	 ResponseEntity<Posts> getPostsById(String id);

	 ResponseEntity<String> addNewPosts(Posts posts);

	 void deletePostsById(String id);	 
	
}
