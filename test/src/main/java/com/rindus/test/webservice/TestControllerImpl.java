package com.rindus.test.webservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rindus.test.model.Posts;
import com.rindus.test.service.ServiceException;
import com.rindus.test.service.interfaces.TestService;
import com.rindus.test.webservice.interfaces.TestController;

@RestController
public class TestControllerImpl implements TestController {

	Logger logger = LoggerFactory.getLogger(TestControllerImpl.class);

	@Autowired
	TestService service;

	/**
	 * Return a list of all posts
	 * 
	 * @return a list
	 */
	public ResponseEntity<List<Posts>> getAllPosts() {
		logger.info("Init get all posts");
		ResponseEntity<List<Posts>> allPosts = null;
		try {
			allPosts = service.getAllPosts();
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return allPosts;
	}

	/**
	 * Returns the posts with specific id
	 *
	 * @param id
	 * @return a posts
	 */
	public ResponseEntity<Posts> getPostsById(String id) {
		logger.info("Init get posts by Id");
		ResponseEntity<Posts> postsById = null;
		try {
			postsById = service.getPostsById(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return postsById;
	}

	/**
	 * Add posts
	 * 
	 * @param Posts
	 */
	public ResponseEntity<Posts> addPosts(Posts posts) {
		logger.info("Init add posts");
		ResponseEntity<Posts> postsResponseEntity = null;
		try {
			postsResponseEntity = service.insertPosts(posts);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return postsResponseEntity;
	}

	/**
	 * Update posts
	 * 
	 * @param id
	 * @return a posts
	 */
	public ResponseEntity<Posts> updatePosts(String id, Posts posts) {
		logger.info("Init update posts");
		  ResponseEntity<Posts> postsResponseEntity;
		try {
			postsResponseEntity = service.updatePostsById(id, posts);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return postsResponseEntity;
	}

	/**
	 * Delete posts
	 * 
	 * @param id	 
	 */
	public ResponseEntity<HttpStatus> removePosts(String id) {

		logger.info("Init delete posts");
		ResponseEntity<HttpStatus> postsResponseEntity;
		try {
			postsResponseEntity = service.deletePostsById(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return postsResponseEntity;
	}


}
