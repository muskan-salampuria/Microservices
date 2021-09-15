package com.epam.books.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookIDNotFoundException;
import com.epam.books.exception.EmptyListException;
import com.epam.books.model.Book;
import com.epam.books.service.BookServiceImple;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class TestBookRestController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookServiceImple bookService;
	
	private Book book;
	private BookDTO bookdto;
	private Book book1;
	private Book updateBook;
	private List<Book> bookList;
	private ObjectMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		this.bookList=new ArrayList<>();
		this.book=new Book(1,"The Guide","VikingPress Methuen","R.K.Narayan");
		this.book1=new Book(1,"The Guide","","R.K.Narayan");
		this.bookdto=new BookDTO(1,"The Guide","VikingPress Methuen","R.K.Narayan");
		this.updateBook=new Book(1,"The Guide","Viking Press(US)","R.K.Narayan");
		this.bookList.add(book);
	}

	@Test
	void testDisplayBookList() {
		assertDoesNotThrow(() -> {
			when(bookService.getBookList()).thenReturn(List.of(book));
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/books")
					.content(mapper.writeValueAsString(bookList)).contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder).andExpect(jsonPath("$[0].id", is(1)))
					.andExpect(jsonPath("$[0].name", is("The Guide")))
					.andExpect(jsonPath("$[0].publisher", is("VikingPress Methuen")))
					.andExpect(jsonPath("$[0].author", is("R.K.Narayan")));
		});
	}
	
	@Test
	void testInvalidDisplayAll() throws Exception {
			when(bookService.getBookList()).thenThrow(EmptyListException.class);
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/books")
					.content(mapper.writeValueAsString(bookList)).contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNoContent());
	}

	@Test
	void testDisplayById() {
		assertDoesNotThrow(() -> {
			when(bookService.getBook(Mockito.anyInt())).thenReturn(this.book);
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/books/1")
					.content(mapper.writeValueAsString(book)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.id", is(1)));
			});
	}
	
	@Test
	void testInvalidDisplayById() throws Exception{
		when(bookService.getBook(Mockito.anyInt())).thenThrow(BookIDNotFoundException.class);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/books/1")
				.content(mapper.writeValueAsString(book)).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
		.andExpect(status().isNotFound());
	}

	@Test
	void testAddNewBook() {
		assertDoesNotThrow(() -> {
			when(bookService.addBook(Mockito.any(BookDTO.class))).thenReturn(this.book);

			RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/books")
					.content(mapper.writeValueAsString(this.bookdto)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("The Guide")))
			.andExpect(jsonPath("$.publisher", is("VikingPress Methuen")))
			.andExpect(jsonPath("$.author", is("R.K.Narayan")));
		});
	}
	
	@Test
	void testInvalidAddNewBook() {
		assertDoesNotThrow(() -> {
			when(bookService.addBook(Mockito.any(BookDTO.class))).thenReturn(this.book1);

			RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/books")
					.content(mapper.writeValueAsString(this.book1)).contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(reqBuilder)
			.andExpect(status().isBadRequest());
		});
	}
	
	@Test
	void testDeleteBook() {
		assertDoesNotThrow(() -> {
			Mockito.when(bookService.deleteBook(Mockito.anyInt())).thenReturn(book.getId());
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders
			.delete("/books/1")
			.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNoContent());
		});
	}
	
	@Test
	void testInvalidDeleteBook() throws Exception {
		Mockito.when(bookService.deleteBook(Mockito.anyInt()))
		.thenThrow(BookIDNotFoundException.class);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
		.delete("/books/1")
		.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(reqBuilder)
		.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateBookDetails() {
		assertDoesNotThrow(()-> {
			when(bookService.updateBook(Mockito.anyInt(), Mockito.any(BookDTO.class))).thenReturn(this.updateBook);
			
			RequestBuilder reqBuilder = MockMvcRequestBuilders
			.put("/books/1")
			.content(mapper.writeValueAsString(updateBook))
			.contentType(MediaType.APPLICATION_JSON);
			
			mockMvc.perform(reqBuilder).andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("The Guide")))
			.andExpect(jsonPath("$.publisher", is("Viking Press(US)")))
			.andExpect(jsonPath("$.author", is("R.K.Narayan")));
		});
	}

}
