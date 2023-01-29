package com.example.tacos;

import java.util.List;

import javax.validation.constraints.*;


import lombok.Data;

@Data
public class Taco {

	@NotNull
	@Size(min=5, message="Name must be at lteast 5 characthers long")
	private String name;

	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<String> ingredients;
}
