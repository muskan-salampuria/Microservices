package com.epam.service;

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

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookIDNotFoundException;
import com.epam.books.exception.EmptyListException;
import com.epam.books.model.Book;
import com.epam.books.repository.BookRepository;
import com.epam.books.service.BookServiceImple;

@ExtendWith(MockitoExtension.class)
class BookServiceImpleTest {

	@InjectMocks
	BookServiceImple bookService;
	
	@Mock
	BookRepository bookRepo;
	
	private Book book;
	private BookDTO bookdto;
	private Book updateBook;
	private List<Book> bookList;
	
	@BeforeEach
	void setUp() throws Exception {
		this.bookList=new ArrayList<>();
		this.book=new Book(1,"The Guide","VikingPress Methuen","R.K.Narayan");
		this.bookdto=new BookDTO(1,"The Guide","VikingPress Methuen","R.K.Narayan");
		this.updateBook=new Book(1,"The Guide","Viking Press(US)","R.K.Narayan");
		this.bookList.add(book);
	}

	@Test
	void testGetBookList() {
		when(bookRepo.findAll()).thenReturn(this.bookList);
		assertEquals(this.bookList,bookService.getBookList());
		verify(bookRepo,atLeastOnce()).findAll();
	}
	
	@Test
	void testInvalidGetBookList() {
		when(bookRepo.findAll()).thenReturn(List.of());
		assertThrows(EmptyListException.class,()->bookService.getBookList());
		verify(bookRepo,atLeastOnce()).findAll();
	}

	@Test
	void testGetBook() {
		Optional<Book> returnValue=Optional.of((Book) this.book);
		Mockito.<Optional<Book>>when(bookRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		assertEquals(this.book,bookService.getBook(1));
		verify(bookRepo, atLeastOnce()).findById(Mockito.anyInt());
	}
	
	@Test
	void testInvalidGetBook() {
		when(bookRepo.findById(Mockito.anyInt())).thenThrow(new BookIDNotFoundException("Book ID Not Found"));
		assertThrows(BookIDNotFoundException.class,()->bookService.getBook(10));
		verify(bookRepo, atLeastOnce()).findById(Mockito.anyInt());
	}
	
	@Test
	void testAddBook() {
		when(bookRepo.save(Mockito.any(Book.class))).thenReturn(this.book);
		assertEquals(this.book,bookService.addBook(this.bookdto));
		verify(bookRepo,atLeastOnce()).save(Mockito.any(Book.class));
	}
	
	@Test
	void testDeleteBook() {
		when(bookRepo.existsById(Mockito.anyInt())).thenReturn(true);
		doNothing().when(bookRepo).deleteById(Mockito.any());
		assertEquals(1,bookService.deleteBook(1));
		verify(bookRepo, atLeastOnce()).existsById(Mockito.anyInt());
	}
	
	@Test
	void testInvalidDeleteBook() {
		when(bookRepo.existsById(Mockito.anyInt())).thenReturn(false);
		assertThrows(BookIDNotFoundException.class,()->bookService.deleteBook(10));
		verify(bookRepo, atLeastOnce()).existsById(Mockito.anyInt());
	}
	
	@Test
	void testUpdateBook() {
		Optional<Book> returnValue=Optional.of((Book) this.book);
		Mockito.<Optional<Book>>when(bookRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		when(bookRepo.save(Mockito.any(Book.class))).thenReturn(this.updateBook);
		assertEquals(this.updateBook,bookService.updateBook(1,this.bookdto));
		verify(bookRepo,atLeastOnce()).save(Mockito.any(Book.class));
	}
	
	@Test
	void testInvalidUpdateBook() {
		when(bookRepo.findById(Mockito.anyInt())).thenThrow(new BookIDNotFoundException("Book ID Not Found"));
		assertThrows(BookIDNotFoundException.class,()->bookService.updateBook(1,this.bookdto));
		verify(bookRepo,atLeastOnce()).findById(Mockito.anyInt());
	}
}
