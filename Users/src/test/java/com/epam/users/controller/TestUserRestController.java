package com.epam.users.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.EmptyListException;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.model.User;
import com.epam.users.service.UserServiceImple;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class TestUserRestController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserServiceImple userService;
	
	private User user1;
	private User user2;
	private User user;
	private UserDTO userdto;
	private User updateUser;
	private List<User> userList;
	private ObjectMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		this.userList=new ArrayList<>();
		this.user1=new User("muskan07","muskan","Muskan");
		this.user2=new User("muskan07","muskan07@in.com","M");
		this.user=new User("muskan07","muskan07@in.com","Muskan");
		this.userdto=new UserDTO("muskan07","muskan07@in.com","Muskan");
		this.updateUser=new User("muskan07","muskan07@in.com","Muskan Salampuria");
		this.userList.add(user);
	}

	@Test
	void testDisplayUserList() {
		assertDoesNotThrow(() -> {
			when(userService.getUserList()).thenReturn(List.of(user));
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/users")
					.content(mapper.writeValueAsString(userList)).contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder).andExpect(jsonPath("$[0].username", is("muskan07")))
					.andExpect(jsonPath("$[0].name", is("Muskan")))
					.andExpect(jsonPath("$[0].email", is("muskan07@in.com")));
		});
	}
	
	@Test
	void testInvalidDisplayUserList() throws Exception {
			when(userService.getUserList()).thenThrow(EmptyListException.class);
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/users")
					.content(mapper.writeValueAsString(userList)).contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNoContent());
	}

	@Test
	void testDisplayUserByName() {
		assertDoesNotThrow(() -> {
			when(userService.getUser(Mockito.anyString())).thenReturn(this.user);
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/users/1")
					.content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.username", is("muskan07")));
			});
	}
	
	@Test
	void testInvalidDisplayUserByName() throws Exception{
		when(userService.getUser(Mockito.anyString())).thenThrow(UserNotFoundException.class);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/users/2")
				.content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
		.andExpect(status().isNotFound());
	}

	@Test
	void testAddNewUser() {
		assertDoesNotThrow(() -> {
			when(userService.addUser(Mockito.any(UserDTO.class))).thenReturn(this.user);

			RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/users")
					.content(mapper.writeValueAsString(this.userdto)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.username", is("muskan07")))
			.andExpect(jsonPath("$.name", is("Muskan")))
			.andExpect(jsonPath("$.email", is("muskan07@in.com")));
		});
	}
	
	@Test
	void testInvalidAddNewUser() {
		assertDoesNotThrow(() -> {
			when(userService.addUser(Mockito.any(UserDTO.class))).thenReturn(this.user1);

			RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/users")
					.content(mapper.writeValueAsString(this.user1)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder)
			.andExpect(status().isBadRequest());
		});
	}
	
	@Test
	void testDeleteUser() {
		assertDoesNotThrow(() -> {
			Mockito.when(userService.deleteUser(Mockito.anyString())).thenReturn(user.getUsername());
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders
			.delete("/users/1")
			.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNoContent());
		});
	}
	
	@Test
	void testInvalidDeleteUser() throws Exception {
		Mockito.when(userService.deleteUser(Mockito.anyString())).thenThrow(UserNotFoundException.class);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
		.delete("/users/2")
		.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(reqBuilder)
		.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateUserDetails() {
		assertDoesNotThrow(()-> {
			when(userService.updateUser(Mockito.anyString(), Mockito.any(UserDTO.class))).thenReturn(this.updateUser);
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders
			.put("/users/1")
			.content(mapper.writeValueAsString(userdto))
			.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.username", is("muskan07")))
			.andExpect(jsonPath("$.name", is("Muskan Salampuria")))
			.andExpect(jsonPath("$.email", is("muskan07@in.com")));
		});
	}
	
	@Test
	void testInvalidUpdateUserDetails() {
		assertDoesNotThrow(()-> {
			when(userService.updateUser(Mockito.anyString(), Mockito.any(UserDTO.class))).thenReturn(this.user2);
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders
			.put("/users/1")
			.content(mapper.writeValueAsString(user2))
			.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder)
			.andExpect(status().isBadRequest());
		});
	}

}
