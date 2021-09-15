package com.epam.library.clients;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.library.dto.UserBean;
import com.epam.library.exception.UserNotFoundException;

@Service
public class UserClientImple implements UserClient {

	UserBean user = new UserBean("No Values", "No Values", "No Values", "from Fallback");

	@Override
	public ResponseEntity<List<UserBean>> displayUserList() {
		List<UserBean> userList = new ArrayList<>();
		userList.add(user);
		return new ResponseEntity<>(userList, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<UserBean> displayByUsername(String username) throws UserNotFoundException {
		return new ResponseEntity<>(user, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<UserBean> addNewUser(@Valid UserBean userdto) {
		return new ResponseEntity<>(user, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<String> deleteUser(String username) throws UserNotFoundException {
		return new ResponseEntity<>("No Message", HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<UserBean> updateUserDetails(String username, @Valid UserBean userdto)
			throws UserNotFoundException {
		return new ResponseEntity<>(user, HttpStatus.BAD_GATEWAY);
	}
}
