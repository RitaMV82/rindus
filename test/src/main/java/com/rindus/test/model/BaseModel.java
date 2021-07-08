package com.rindus.test.model;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BaseModel {
	@Size(min = 1, max = 3, message = "Length id between 1 and 3 digits")
	private Integer id;
	@Size(max = 200, message = "Length body no more than 200 character")
	private String body;
}
