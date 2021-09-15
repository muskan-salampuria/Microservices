package com.epam.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.library.clients.BookClient;
import com.epam.library.clients.UserClient;
import com.epam.library.dto.BookBean;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.dto.UserBean;
import com.epam.library.model.Library;
import com.epam.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LibraryRestController.class)
class TestLibraryRestController {

	ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	BookClient bookClientMocked;
	
	@MockBean
	UserClient userClientMocked;
	
	@MockBean
	LibraryService libraryServiceMocked;
	
	private List<BookBean> bookss=new ArrayList<>();
	
	BookBean book1=new BookBean();
	
	private List<UserBean> userList=new ArrayList<>();
	UserBean user1=new UserBean();
	
	private List<Library> libraryList=new ArrayList<>();
	Library library1=new Library();
	
	LibraryDTO libdto=new LibraryDTO();
	
	
	@BeforeEach
	void setUp() {
		BookBean book=new BookBean();
		book.setId(1);
		book.setName("java");
		book.setPublisher("Ieee");
		book.setAuthor("dennis-riche");
		book1.setId(2);
		book1.setName("c++");
		book1.setPublisher("Ieee");
		book1.setAuthor("dennis-richeee");
		bookss.add(book);
		bookss.add(book1);
		
		UserBean user=new UserBean();
		user.setUsername("muskang");
		user.setEmail("muskan@gmail.com");
		user.setName("muskan goyal");
		userList.add(user);
		user1.setUsername("muskans");
		user1.setEmail("muskan07@gmail.com");
		user1.setName("muskan salampuria");
		userList.add(user1);
		
		Library library=new Library();
		library.setId(1);
		library.setUsername("muskang");
		library.setBookId(1);
		libraryList.add(library);
		library1.setId(2);
		library1.setUsername("muskang");
		library1.setBookId(2);
		libraryList.add(library1);
		libdto.setBookId(2);
		libdto.setUsername("muskang");
	}

	@Test
	void testGetBookList() throws Exception {
		ResponseEntity<List<BookBean>> bookList=new ResponseEntity <> (bookss,HttpStatus.OK);
		when(bookClientMocked.displayBookList()).thenReturn(bookList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/library/books/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testGetBook() throws Exception {
		ResponseEntity<BookBean> book2=new ResponseEntity <> (book1,HttpStatus.OK);
		when(bookClientMocked.displayById(Mockito.anyInt())).thenReturn(book2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/library/books/2/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testAddNewBook() throws Exception {
		ResponseEntity<BookBean> book2=new ResponseEntity <> (book1,HttpStatus.OK);
		when(bookClientMocked.addNewBook(Mockito.any(BookBean.class))).thenReturn(book2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/library/books/").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testDeleteBook() throws Exception {
		ResponseEntity<Integer> book2=new ResponseEntity <> (1,HttpStatus.NO_CONTENT);
		when(bookClientMocked.deleteBook(Mockito.anyInt())).thenReturn(book2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/library/books/2/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	@Test
	void testUpdateBookDetails() throws Exception {
		ResponseEntity<BookBean> book2=new ResponseEntity <> (book1,HttpStatus.OK);
		when(bookClientMocked.updateBookDetails(Mockito.anyInt(), Mockito.any(BookBean.class))).thenReturn(book2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/library/books/2/").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testDisplayUserList() throws Exception {
		ResponseEntity<List<UserBean>> usersList=new ResponseEntity <> (userList,HttpStatus.OK);
		when(userClientMocked.displayUserList()).thenReturn(usersList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/library/users/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	void testAddNewUser() throws Exception {
		ResponseEntity<UserBean> users2=new ResponseEntity <> (user1,HttpStatus.OK);
		when(userClientMocked.addNewUser(Mockito.any(UserBean.class))).thenReturn(users2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/library/users/").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testDeleteUser() throws Exception {
		ResponseEntity<String> user=new ResponseEntity <> ("muskang",HttpStatus.OK);
		when(userClientMocked.deleteUser(Mockito.anyString())).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/library/users/muskang/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testUpdateUserDetails() throws Exception {
		ResponseEntity<UserBean> users2=new ResponseEntity <> (user1,HttpStatus.OK);
		when(userClientMocked.updateUserDetails(Mockito.anyString(), Mockito.any(UserBean.class))).thenReturn(users2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/library/users/muskans/").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testIssueBook() throws Exception {
		when(libraryServiceMocked.issueBook(Mockito.any(LibraryDTO.class))).thenReturn(library1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/library/users/muskang/books/2/").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(library1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	void testReleaseBook() throws Exception {
		when(libraryServiceMocked.releaseBook(Mockito.anyString(), Mockito.anyInt())).thenReturn(library1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/library/users/muskang/books/1/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
	}

	@Test
	void testViewUser() throws Exception {
		ResponseEntity<UserBean> users2=new ResponseEntity <> (user1,HttpStatus.OK);
		when(userClientMocked.displayByUsername(Mockito.anyString())).thenReturn(users2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/library/users/muskang/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
	}

}
