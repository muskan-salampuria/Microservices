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

import com.epam.library.dto.BookBean;
import com.epam.library.exception.BookIDNotFoundException;


@FeignClient(name="book",fallback = BookClientImple.class)
@LoadBalancerClient(name="book",configuration = BookClientImple.class)
public interface BookClient {

	@GetMapping("/books")
	public ResponseEntity<List<BookBean>> displayBookList();
	
	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookBean> displayById(@PathVariable("book_id") int id) throws BookIDNotFoundException;
	
	@PostMapping("/books")
	public ResponseEntity<BookBean> addNewBook(@RequestBody @Valid BookBean book) ;
	
	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<Integer> deleteBook(@PathVariable("book_id") int id) throws BookIDNotFoundException ;
	
	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookBean> updateBookDetails(@PathVariable("book_id") int id,@RequestBody @Valid BookBean bookdto) throws BookIDNotFoundException;
	
}
