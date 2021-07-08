package com.rindus.test.webservice.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rindus.test.model.Comments;
import com.rindus.test.model.Posts;

import io.swagger.annotations.ApiOperation;


@RequestMapping(path = "/prueba")
@RestController
public interface TestController {

	@ApiOperation(value = "Get all resources", notes = "Get all resources")
	@GetMapping("/posts")
	ResponseEntity<List<Posts>> getAllPosts();

	@ApiOperation(value = "Get one resource by id", notes = "Get one resource by id")
	@GetMapping("posts/{id}")
	ResponseEntity<Posts> getPostsById(@PathVariable("id") String id);

	@ApiOperation(value = "Create new resource", notes = "Create new resource")
	@PostMapping("/posts")
	ResponseEntity<Posts> addPosts(@RequestBody Posts posts);

	@ApiOperation(value = "Update resource", notes = "Update resource")
	@PutMapping(value = "/posts/{id}")
	ResponseEntity<Posts> updatePosts(@PathVariable("id") String id, @RequestBody Posts posts);

	@ApiOperation(value = "Delete resource", notes = "Delete resource")
	@DeleteMapping(value = "/posts/{id}")
	ResponseEntity<HttpStatus> removePosts(@PathVariable("id") String id);

	@ApiOperation(value = "Get all comments for one PostId", notes = "Get all comments for one PostId")
	@GetMapping("/{id}/comments")
	ResponseEntity<List<Comments>> getCommentsFromPost(@PathVariable("id") String id);
	
	@ApiOperation(value = "Partial modify resource", notes = "Partial modify resource")
	@PostMapping("/modify/{id}")
	ResponseEntity<Posts> modifyPost(@PathVariable("id") String id, @RequestBody Posts posts);
	
}
