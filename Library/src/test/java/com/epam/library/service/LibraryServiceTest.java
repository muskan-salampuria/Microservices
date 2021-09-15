package com.epam.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.MaximumBookIssuedException;
import com.epam.library.exception.NoSuchBookForRelease;
import com.epam.library.model.Library;
import com.epam.library.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

	@InjectMocks
	LibraryService libraryService;

	@Mock
	LibraryRepository libraryRepo;

	private Library library1;
	private Library library2;
	private LibraryDTO librarydto;
	private List<Library> libraryList;
	private List<Integer> bookList;
	private List<Integer> bookList1;
	private List<Integer> emptyList;

	@BeforeEach
	void setUp() throws Exception {
		this.libraryList = new ArrayList<>();
		this.library1 = new Library(1, "muskan", 1);
		this.libraryList.add(library1);
		this.library2 = new Library(2, "muskan", 2);
		this.libraryList.add(library2);

		this.librarydto = new LibraryDTO(1, "muskan", 1);

		this.bookList1 = new ArrayList<>();
		this.bookList1.add(1);
		this.bookList1.add(2);
		this.bookList1.add(3);
		this.bookList1.add(4);
		this.bookList1.add(5);
		this.bookList1.add(6);

		this.emptyList = new ArrayList<>();

		this.bookList = new ArrayList<>();
		bookList.add(1);
		bookList.add(2);
	}

	@Test
	void testGetBookIssued() {
		when(libraryRepo.findBookIdByUsername(Mockito.anyString())).thenReturn(this.bookList);
		assertEquals(this.bookList, libraryService.getBookIssued("muskan"));
		verify(libraryRepo, atLeastOnce()).findBookIdByUsername(Mockito.anyString());
	}

	@Test
	void testInvalidGetBookIssued() {
		when(libraryRepo.findBookIdByUsername(Mockito.anyString())).thenReturn(List.of());
		assertEquals(emptyList, libraryService.getBookIssued("ankita"));
		verify(libraryRepo, atLeastOnce()).findBookIdByUsername(Mockito.anyString());
	}

	@Test
	void testIssueBook() {
		when(libraryRepo.findBookIdByUsername(Mockito.anyString())).thenReturn(this.bookList);
		when(libraryRepo.save(Mockito.any(Library.class))).thenReturn(this.library1);
		assertEquals(this.library1, libraryService.issueBook(this.librarydto));
		verify(libraryRepo, atLeastOnce()).save(Mockito.any(Library.class));
	}

	@Test
	void testInvalidIssueBook() {
		when(libraryRepo.findBookIdByUsername(Mockito.anyString())).thenReturn(this.bookList1);
		assertThrows(MaximumBookIssuedException.class, () -> libraryService.issueBook(this.librarydto));
		verify(libraryRepo, atLeastOnce()).findBookIdByUsername(Mockito.anyString());
	}

	@Test
	void testReleaseBook() {
		when(libraryRepo.findByNameandBookId(Mockito.anyString(), Mockito.anyInt())).thenReturn(this.library1);
//		when(libraryRepo.delete(Mockito.any(Library.class))).thenReturn(this.library1);
		assertEquals(this.library1, libraryService.releaseBook("muskan", 1));
		verify(libraryRepo, atLeastOnce()).delete(Mockito.any(Library.class));
	}

	@Test
	void testInvalidReleaseBook() {
		when(libraryRepo.findByNameandBookId(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		assertThrows(NoSuchBookForRelease.class, () -> libraryService.releaseBook("anku", 1));
		verify(libraryRepo, atLeastOnce()).findByNameandBookId(Mockito.anyString(), Mockito.anyInt());
	}

}
