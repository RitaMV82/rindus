package com.rindus.test.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Comments extends BaseModel {

	private Integer postId;  
	@Size(max = 100, message = "Lenght name not be longer than 100")
    private String name;  
	@Email(message = "Email should be valid")
    private String email;
}
