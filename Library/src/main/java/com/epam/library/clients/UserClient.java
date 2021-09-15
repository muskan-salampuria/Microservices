package com.epam.library.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.UserBean;
import com.epam.library.exception.UserNotFoundException;

@FeignClient(name="user",fallback = UserClientImple.class)
@LoadBalancerClient(name="user",configuration = UserClientImple.class)
public interface UserClient {

	@GetMapping("/users")
	public ResponseEntity<List<UserBean>> displayUserList() ;
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserBean> displayByUsername(@PathVariable("username") String username) throws UserNotFoundException;
	
	@PostMapping("/users")
	public ResponseEntity<UserBean> addNewUser(@RequestBody @Valid UserBean userdto);
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserNotFoundException;
	
	@PutMapping("/users/{username}")
	public ResponseEntity<UserBean> updateUserDetails(@PathVariable("username") String username,@RequestBody @Valid UserBean userdto) throws UserNotFoundException;
	
}
