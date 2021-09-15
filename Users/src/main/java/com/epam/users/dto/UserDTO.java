package com.epam.users.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
	
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

}
