package com.rindus.test.service.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rindus.test.model.Posts;
import com.rindus.test.service.ServiceException;

public interface TestService {

	ResponseEntity<List<Posts>> getAllPosts() throws ServiceException;

	ResponseEntity<Posts> getPostsById(String id) throws ServiceException;

	ResponseEntity<Posts> insertPosts(Posts posts) throws ServiceException;

	ResponseEntity<Posts> updatePostsById(String id, Posts posts) throws ServiceException;

	ResponseEntity<HttpStatus> deletePostsById(String id) throws ServiceException;
	
	
}
