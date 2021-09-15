package com.epam.books.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookIDNotFoundException;
import com.epam.books.exception.EmptyListException;
import com.epam.books.model.Book;
import com.epam.books.repository.BookRepository;

@Service
public class BookServiceImple implements BookService {

	@Autowired
	BookRepository bookRepo;
	
	@Override
	public List<Book> getBookList() {
		List<Book> bookList = bookRepo.findAll();
		if (bookList.isEmpty())
			throw new EmptyListException("No Books Available");
		return bookList;
	}

	@Override
	public Book getBook(int id) {
		return bookRepo.findById(id).orElseThrow(() -> new BookIDNotFoundException("Book ID Not Found"));
	}

	@Override
	public Book addBook(BookDTO bookdto) {
		Book book=new Book(bookdto.getName(),bookdto.getPublisher(),bookdto.getAuthor());
		return bookRepo.save(book);
	}

	@Override
	public int deleteBook(int id) {
		if (bookRepo.existsById(id))
			bookRepo.deleteById(id);
		else
			throw new BookIDNotFoundException("Book ID Not Found");
		return id;
	}

	@Override
	public Book updateBook(int id,BookDTO bookdto) {
		getBook(id);
		Book book=new Book(id,bookdto.getName(),bookdto.getPublisher(),bookdto.getAuthor());
		return bookRepo.save(book);
	}

}
