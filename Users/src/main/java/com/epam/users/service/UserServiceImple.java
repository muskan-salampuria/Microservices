package com.epam.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.EmptyListException;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.exception.UsernameExistsException;
import com.epam.users.model.User;
import com.epam.users.repository.UserRepository;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public List<User> getUserList() {
		List<User> userList = userRepo.findAll();
		if (userList.isEmpty())
			throw new EmptyListException("No Users Available");
		return userList;
	}

	@Override
	public User getUser(String username) {
		return userRepo.findById(username).orElseThrow(() -> new UserNotFoundException("User Not Found"));
	}

	@Override
	public User addUser(UserDTO userdto) throws UsernameExistsException {
		if(userRepo.existsById(userdto.getUsername()))
			throw new UsernameExistsException("Username already present");
		User user=new User(userdto.getUsername(),userdto.getEmail(),userdto.getName());
		return userRepo.save(user);
	}

	@Override
	public String deleteUser(String username) {
		if (userRepo.existsById(username))
			userRepo.deleteById(username);
		else
			throw new UserNotFoundException("User Not Found");
		return username;
	}

	@Override
	public User updateUser(String username,UserDTO userdto) {
		getUser(username);
		User user=new User(username,userdto.getEmail(),userdto.getName());
		return userRepo.save(user);
	}

}
