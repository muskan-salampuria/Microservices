package com.epam.users.service;

import java.util.List;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.UsernameExistsException;
import com.epam.users.model.User;

public interface UserService {

	public abstract List<User> getUserList();
	
	public abstract User getUser(String username);
	
	public abstract User addUser(UserDTO userdto) throws UsernameExistsException;
	
	public abstract String deleteUser(String username);
	
	public abstract User updateUser(String username,UserDTO userdto);
	
}
