package com.epam.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.EmptyListException;
import com.epam.users.exception.UserNotFoundException;
import com.epam.users.model.User;
import com.epam.users.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImpleTest {

	@InjectMocks
	UserServiceImple userService;
	
	@Mock
	UserRepository userRepo;
	
	private User user;
	private UserDTO userdto;
	private User updateUser;
	private List<User> userList;
	
	@BeforeEach
	void setUp() throws Exception {
		this.userList=new ArrayList<>();
		this.user=new User("muskan07","muskan07@in.com","Muskan");
		this.userdto=new UserDTO("muskan07","muskan07@in.com","Muskan");
		this.updateUser=new User("muskan07","muskan07@in.com","Muskan Salampuria");
		this.userList.add(user);
	}

	@Test
	void testGetUserList() {
		when(userRepo.findAll()).thenReturn(this.userList);
		assertEquals(this.userList,userService.getUserList());
		verify(userRepo,atLeastOnce()).findAll();
	}
	
	@Test
	void testInvalidGetUserList() {
		when(userRepo.findAll()).thenReturn(List.of());
		assertThrows(EmptyListException.class,()->userService.getUserList());
		verify(userRepo,atLeastOnce()).findAll();
	}

	@Test
	void testGetUser() {
		Optional<User> returnValue=Optional.of((User) this.user);
		Mockito.<Optional<User>>when(userRepo.findById(Mockito.anyString())).thenReturn(returnValue);
		assertEquals(this.user,userService.getUser("muskan07"));
		verify(userRepo, atLeastOnce()).findById(Mockito.anyString());
	}
	
	@Test
	void testInvalidGetUser() {
		when(userRepo.findById(Mockito.anyString())).thenThrow(new UserNotFoundException("User Not Found"));
		assertThrows(UserNotFoundException.class,()->userService.getUser("muskan"));
		verify(userRepo, atLeastOnce()).findById(Mockito.anyString());
	}
	
	@Test
	void testAddUser() {
		when(userRepo.save(Mockito.any(User.class))).thenReturn(this.user);
		assertEquals(this.user,userService.addUser(this.userdto));
		verify(userRepo,atLeastOnce()).save(Mockito.any(User.class));
	}
	
	@Test
	void testDeleteUser() {
		when(userRepo.existsById(Mockito.anyString())).thenReturn(true);
		doNothing().when(userRepo).deleteById(Mockito.anyString());
		assertEquals("muskan07",userService.deleteUser("muskan07"));
		verify(userRepo, atLeastOnce()).existsById(Mockito.anyString());
	}
	
	@Test
	void testInvalidDeleteBook() {
		when(userRepo.existsById(Mockito.anyString())).thenReturn(false);
		assertThrows(UserNotFoundException.class,()->userService.deleteUser("muskan"));
		verify(userRepo, atLeastOnce()).existsById(Mockito.anyString());
	}
	
	@Test
	void testUpdateUser() {
		Optional<User> returnValue=Optional.of((User) this.user);
		Mockito.<Optional<User>>when(userRepo.findById(Mockito.anyString())).thenReturn(returnValue);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(this.updateUser);
		assertEquals(this.updateUser,userService.updateUser("muskan07",this.userdto));
		verify(userRepo,atLeastOnce()).save(Mockito.any(User.class));
	}
	
	@Test
	void testInvalidUpdateBook() {
		when(userRepo.findById(Mockito.anyString())).thenThrow(new UserNotFoundException("User Not Found"));
		assertThrows(UserNotFoundException.class,()->userService.updateUser("muskan",this.userdto));
		verify(userRepo,atLeastOnce()).findById(Mockito.anyString());
	}
}
