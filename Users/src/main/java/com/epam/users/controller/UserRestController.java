package com.epam.users.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.exception.EmptyListException;
import com.epam.users.model.User;
import com.epam.users.service.UserService;

@RestController
@RequestMapping
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	Environment env;
	 
	@GetMapping("/users")
	public ResponseEntity<List<User>> displayUserList() {
		List<User> bookList = new ArrayList<>();
		ResponseEntity<List<User>> responseEntity;
		try {
			bookList = userService.getUserList();
			responseEntity = new ResponseEntity<>(bookList, HttpStatus.OK);
		} catch (EmptyListException ex) {
			responseEntity = new ResponseEntity<>(bookList, HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<User> displayUserByName(@PathVariable("username") String username) throws UserNotFoundException {
		User user=userService.getUser(username);
		user.setPort(env.getProperty("local.server.port"));
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addNewUser(@RequestBody @Valid UserDTO userdto) {
		User user=userService.addUser(userdto);
		user.setPort(env.getProperty("local.server.port"));
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserNotFoundException {
		return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/users/{username}")
	public ResponseEntity<User> updateUserDetails(@PathVariable("username") String username,@RequestBody @Valid UserDTO userdto) throws UserNotFoundException {
		User user=userService.updateUser(username,userdto);
		user.setPort(env.getProperty("local.server.port"));
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

}
