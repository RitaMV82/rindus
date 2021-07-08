package com.rindus.test.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rindus.test.client.interfaces.TestClient;
import com.rindus.test.model.Comments;
import com.rindus.test.model.Posts;
import com.rindus.test.service.interfaces.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	TestClient client;

	Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	/**
	 * Method in charge to read all posts
	 * 
	 * @throws ServiceException
	 */
	public ResponseEntity<List<Posts>> getAllPosts() throws ServiceException {
		ResponseEntity<String> allPosts = client.getAllPosts();
		List<Posts> postsList = null;
		try {
			postsList = mapper.readValue(allPosts.getBody(), ArrayList.class);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			throw new ServiceException();
		}
		return ResponseEntity.ok(postsList);
	}

	/**
	 * Method in charge to read a post by Id
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<Posts> getPostsById(String id) throws ServiceException {
		ResponseEntity<Posts> postsById = client.getPostsById(id);
		if (postsById != null) {
			return ResponseEntity.ok(postsById.getBody());
		} else {
			logger.warn("Posts not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Method in charge to add new posts
	 * 
	 * @param posts
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<Posts> insertPosts(Posts posts) throws ServiceException {
		ResponseEntity<Posts> postsById = this.getPostsById(String.valueOf(posts.getId()));
		if (postsById.getBody() == null) {
			Posts newPost = performAddPosts(posts);			
			return new ResponseEntity<Posts>(newPost, HttpStatus.CREATED);
		} else {
			logger.warn("Post already exist");
			return postsById;
		}
	}

	/**
	 * Method in charge to update a posts
	 * 
	 * @param id
	 * @param posts
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<Posts> updatePostsById(String id, Posts posts) throws ServiceException {
		ResponseEntity<Posts> postsById = this.getPostsById(id);
		if (postsById.getBody() != null) {
		  ResponseEntity<Posts> post = client.updatePostsById(id, posts);			
		  return ResponseEntity.ok(post.getBody());
		} else {
		  logger.error("Posts not exits");
		  throw new ServiceException();
		}
	}

	/**
	 * Method in charge to delete a posts
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<HttpStatus> deletePostsById(String id) throws ServiceException {
		ResponseEntity<Posts> postsById = this.getPostsById(id);
		if (postsById.getBody() != null) {
			client.deletePostsById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		} else {
			throw new ServiceException();
		}

	}
	
	/**
	 * Method in charge to get a list of Comments by postId
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<List<Comments>> getCommentsFromPost(String id) throws ServiceException {

		HttpEntity<String> commentsById = client.getCommentsByPostId(id);
		List<Comments> commentsList = null;
		try {
			commentsList = mapper.readValue(commentsById.getBody(), ArrayList.class);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			throw new ServiceException();
		}
		return ResponseEntity.ok(commentsList);
	}



	/**
	 * Method in charge to modify partially a posts
	 * 
	 * @param posts
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity<Posts> modifyPost(String id, Posts posts) throws ServiceException {
		ResponseEntity<Posts> postsById = this.getPostsById(id);

		if (postsById.getBody().getId() != null) {
			ResponseEntity<Posts> post = client.modifyPosts(id, posts);
			return ResponseEntity.ok(post.getBody());
		} else {
			logger.error("Posts not exits");
			throw new ServiceException();
		}
	}

	/**
	 * Method in charge to a add new posts
	 * 
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	private Posts performAddPosts(Posts posts) throws ServiceException {
		ResponseEntity<String> entity = client.addNewPosts(posts);
		Posts newPost;
		try {
			newPost = mapper.readValue(entity.getBody(), Posts.class);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			throw new ServiceException();
		}
		return newPost;
	}
	
}
