package chnu.cs.security26.service;

/*
    @author     Anastasiia Shevchenko
    @project    security26
    @class      BookService
    @version    1.0.0
    @since      11.09.2026 - 16.31.31
*/


import chnu.cs.security26.model.Book;
import chnu.cs.security26.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final List<Book> books = new ArrayList<>(List.of(
            new Book("1", "Harry Potter", "J.K. Rowling", "A young wizard discovers his magical abilities."),
            new Book("2", "The Hobbit", "J.R.R. Tolkien", "The adventure of Bilbo Baggins."),
            new Book("3", "1984", "George Orwell", "A dystopian novel about a totalitarian society.")
    ));
    private final BookRepository bookRepository;

    @PostConstruct
    void init(){
        bookRepository.deleteAll();
        books.forEach(this::createBook);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(String id, Book updatedBook) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return null;
        }

        Book existingBook = book.get();

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setDescription(updatedBook.getDescription());

        return bookRepository.save(existingBook);
    }

    public boolean deleteBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
