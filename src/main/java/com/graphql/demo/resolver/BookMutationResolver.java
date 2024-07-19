package com.graphql.demo.resolver;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.demo.entity.Book;
import com.graphql.demo.repository.BookRepository;

@Component
public class BookMutationResolver implements GraphQLMutationResolver{

	@Autowired
	BookRepository bookRepository;
	
	@Transactional
	public Book createBook(final String bookName,final String publishDate, final String author) {
		Book book =new Book();
		book.setBookName(bookName);
		book.setPublishDate(LocalDate.parse(publishDate));
		book.setAuthor(author);
		return bookRepository.save(book);
	}
	
	@Transactional
	public Book updateBook(int id, final String bookName, final String publishDate, final String author) {
		Optional<Book> opBook = bookRepository.findById(id);
		Book book = null;

		if (opBook.isPresent()) {
			book = opBook.get();
			book.setBookName(bookName);
			book.setAuthor(author);
			book.setPublishDate(LocalDate.parse(publishDate));
			return bookRepository.save(book);
		}
		return book;
	}
	 
	
	@Transactional
	public Boolean deleteBook(int id) {
		bookRepository.deleteById(id);
		Optional<Book> opBook = bookRepository.findById(id);
		return !opBook.isPresent();
	}
}
