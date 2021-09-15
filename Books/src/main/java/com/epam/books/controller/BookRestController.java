package com.epam.books.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.books.service.BookService;
import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookIDNotFoundException;
import com.epam.books.exception.EmptyListException;
import com.epam.books.model.Book;

@RestController
@RequestMapping
public class BookRestController {
	
	@Autowired
	BookService bookService;
	
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> displayBookList() {
		List<Book> bookList = new ArrayList<>();
		ResponseEntity<List<Book>> responseEntity;
		try {
			bookList = bookService.getBookList();
			responseEntity = new ResponseEntity<>(bookList, HttpStatus.OK);
		} catch (EmptyListException ex) {
			responseEntity = new ResponseEntity<>(bookList, HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	@GetMapping("/books/{book_id}")
	public ResponseEntity<Book> displayById(@PathVariable("book_id") int id) throws BookIDNotFoundException {
		return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> addNewBook(@RequestBody @Valid BookDTO book) {
		return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<Integer> deleteBook(@PathVariable("book_id") int id) throws BookIDNotFoundException {
		return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/books/{book_id}")
	public ResponseEntity<Book> updateBookDetails(@PathVariable("book_id") int id,@RequestBody @Valid BookDTO bookdto) throws BookIDNotFoundException {
		return new ResponseEntity<>(bookService.updateBook(id,bookdto), HttpStatus.ACCEPTED);
	}

}
