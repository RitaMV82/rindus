package com.rindus.test.model;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Posts extends BaseModel {
	
	private Integer userId;
	@Size(max = 100, message = "Lenght title not be more than 100 characters")
	private String title;
		
}
