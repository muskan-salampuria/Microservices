package com.epam.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.library.model.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

	@Query(value = "select * from library where username=?1 and book_id=?2", nativeQuery = true)
	Library findByNameandBookId(String username,int bookId);
	
	@Query(value = "select book_id from library where username=?1", nativeQuery = true)
	List<Integer> findBookIdByUsername(String username);
	
}
