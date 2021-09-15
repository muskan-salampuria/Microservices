package com.epam.library.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserBean {
	@NotNull
	@NotBlank(message = "Username should not be blank")
	@Size(min = 3, max = 25, message = "Username Length should be in between 3 to 25")
	private String username;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@NotBlank(message = "Name should not be blank")
	@Size(min = 3, max = 25, message = "Name Length should be in between 3 to 25")
	private String name;
	String port;
}
