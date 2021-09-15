package com.epam.library.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.clients.BookClient;
import com.epam.library.clients.UserClient;
import com.epam.library.dto.BookBean;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.dto.UserBean;
import com.epam.library.exception.BookIDNotFoundException;
import com.epam.library.exception.MaximumBookIssuedException;
import com.epam.library.exception.NoSuchBookForRelease;
import com.epam.library.exception.UserNotFoundException;
import com.epam.library.model.Library;
import com.epam.library.service.LibraryService;

@RestController
public class LibraryRestController {

	@Autowired
	BookClient bookClient;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	LibraryService libService;
	
	@GetMapping("/library/books")
	public ResponseEntity<List<BookBean>> getBookList(){
		return bookClient.displayBookList();
	}
	
	@GetMapping("/library/books/{bookId}")
	public ResponseEntity<BookBean> getBook(@PathVariable("bookId") int id) throws BookIDNotFoundException{
		return bookClient.displayById(id);
	}
	
	@PostMapping("/library/books")
	public ResponseEntity<BookBean> addNewBook(@RequestBody @Valid BookBean book) {
		return bookClient.addNewBook(book);
	}
	
	@DeleteMapping("library/books/{book_id}")
	public ResponseEntity<Integer> deleteBook(@PathVariable("book_id") int id) throws BookIDNotFoundException{
		return bookClient.deleteBook(id);
	}
	
	@PutMapping("/library/books/{book_id}")
	public ResponseEntity<BookBean> updateBookDetails(@PathVariable("book_id") int id,@RequestBody @Valid BookBean bookdto) throws BookIDNotFoundException{
		return bookClient.updateBookDetails(id, bookdto);
	}
	
	@GetMapping("/library/users")
	public ResponseEntity<List<UserBean>> displayUserList() {
		return userClient.displayUserList();
	}
	
	@PostMapping("/library/users")
	public ResponseEntity<UserBean> addNewUser(@RequestBody @Valid UserBean userdto){
		return userClient.addNewUser(userdto);
	}
	
	@DeleteMapping("/library/users/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserNotFoundException{
		return userClient.deleteUser(username);
	}
	
	@PutMapping("/library/users/{username}")
	public ResponseEntity<UserBean> updateUserDetails(@PathVariable("username") String username,@RequestBody @Valid UserBean userdto) throws UserNotFoundException{
		return userClient.updateUserDetails(username, userdto);
	}
	
	@PostMapping("/library/users/{username}/books/{book_id}")
	public ResponseEntity<Library> issueBook(@PathVariable("username") String username,@PathVariable("book_id") int id,@RequestBody @Valid LibraryDTO libDto) throws MaximumBookIssuedException{
		Library lib=libService.issueBook(libDto);
		return new ResponseEntity<>(lib,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/library/users/{username}/books/{book_id}")
	public ResponseEntity<Library> releaseBook(@PathVariable("username") String username,@PathVariable("book_id") int bookId) throws NoSuchBookForRelease{
		Library lib= libService.releaseBook(username,bookId);
		return new ResponseEntity<>(lib,HttpStatus.ACCEPTED);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/library/users/{username}")
	public ResponseEntity<List> viewUser(@PathVariable("username") String username) throws UserNotFoundException{
		UserBean user=userClient.displayByUsername(username).getBody();
		List<BookBean> issuedBooks = new ArrayList<>();
		for(Integer book: libService.getBookIssued(username)) {
			issuedBooks.add(getBook(book).getBody());
		}
		return new ResponseEntity<>(Arrays.asList(user,issuedBooks),HttpStatus.ACCEPTED);
	}

}
 