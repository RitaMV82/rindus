package com.rindus.test.model;

import lombok.Data;

@Data
public class Posts extends BaseModel {
	
	private Integer userId;
	private String title;
		
}
