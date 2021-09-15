package com.epam.books;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes=BooksApplication.class)
class BooksApplicationTests {
	
//	@Test
//	void contextLoads() {
//		
//	}
	
	@Test
	void main() {
		BooksApplication.main(new String[] {});
	}


}
