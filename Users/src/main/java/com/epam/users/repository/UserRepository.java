package com.epam.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{

//	@Query(value = "select * from user where username like ?1", nativeQuery = true)
//	User findByName(String username);
}
