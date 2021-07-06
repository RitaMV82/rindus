package com.rindus.test.model;

import lombok.Data;

@Data
public class Comments extends BaseModel {

	private Integer postId;  
    private String name;  
    private String email;
}
