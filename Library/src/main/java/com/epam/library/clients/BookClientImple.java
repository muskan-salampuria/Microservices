package com.epam.library.clients;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.library.dto.BookBean;
import com.epam.library.exception.BookIDNotFoundException;

@Service
public class BookClientImple implements BookClient {

	BookBean book = new BookBean(0,"No Values", "No Values", "No Values", "from Fallback");
	
	@Override
	public ResponseEntity<List<BookBean>> displayBookList() {
		List<BookBean> bookList = new ArrayList<>();
		bookList.add(book);
		return new ResponseEntity<>(bookList, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<BookBean> displayById(int id) throws BookIDNotFoundException {
		return new ResponseEntity<>(book, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<BookBean> addNewBook(@Valid BookBean book) {
		return new ResponseEntity<>(book, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<Integer> deleteBook(int id) throws BookIDNotFoundException {
		return new ResponseEntity<>(0, HttpStatus.BAD_GATEWAY);
	}

	@Override
	public ResponseEntity<BookBean> updateBookDetails(int id, @Valid BookBean bookdto) throws BookIDNotFoundException {
		return new ResponseEntity<>(book, HttpStatus.BAD_GATEWAY);
	}

}
