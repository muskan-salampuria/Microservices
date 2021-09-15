package com.epam.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.MaximumBookIssuedException;
import com.epam.library.exception.NoSuchBookForRelease;
import com.epam.library.model.Library;
import com.epam.library.repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository libRepo;
	
	public List<Integer> getBookIssued(String username) {
		return libRepo.findBookIdByUsername(username);	
	}

	public Library issueBook(LibraryDTO libdto) throws MaximumBookIssuedException {
		if(getBookIssued(libdto.getUsername()).size()>=5)
			throw new MaximumBookIssuedException("Can not issue more than 5 books");
		Library lib=new Library(libdto.getUsername(),libdto.getBookId());
		return libRepo.save(lib);
	}
	
	public Library releaseBook(String username,int bookID) throws NoSuchBookForRelease {
		Library lib=libRepo.findByNameandBookId(username, bookID);
		if(lib==null)
			throw new NoSuchBookForRelease("No Such Book for release");
		libRepo.delete(lib);
		return lib;
	}
}
