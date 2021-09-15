package com.epam.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookBean {

	private int id;
	
	@NotNull
	@NotBlank(message = "Book Name should not be blank")
	@Size(min = 3, max = 25, message = "Book Name Length should be in between 3 to 25")
	private String name;
	
	@NotNull
	@NotBlank(message = "Publisher should not be blank")
	@Size(min = 3, max = 25, message = "Publisher Length should be in between 3 to 25")
	private String publisher;
	
	@NotNull
	@NotBlank(message = "Author should not be blank")
	@Size(min = 3, max = 25, message = "Author Length should be in between 3 to 25")
	private String author;
	
	String port;
}
