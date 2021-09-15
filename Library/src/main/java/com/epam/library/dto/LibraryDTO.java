package com.epam.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LibraryDTO {

	private int id;
	
	@NotNull(message="Username can not be Null")
	@NotBlank(message = "Username should not be blank")
	@Size(min = 3, max = 25, message = "Username Length should be in between 3 to 25")
	private String username;
	
	@NotNull(message="Book ID can not be Null")
	private int bookId;
}
