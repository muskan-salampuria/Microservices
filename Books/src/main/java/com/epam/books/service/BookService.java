package com.epam.books.service;

import java.util.List;

import com.epam.books.dto.BookDTO;
import com.epam.books.model.Book;

public interface BookService {

	public abstract List<Book> getBookList();
	
	public abstract Book getBook(int id);
	
	public abstract Book addBook(BookDTO bookdto);
	
	public abstract int deleteBook(int id);
	
	public abstract Book updateBook(int id,BookDTO bookdto);
	
}
