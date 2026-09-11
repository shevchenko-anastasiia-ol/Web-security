package chnu.cs.security26.service;

/*
    @author     Anastasiia Shevchenko
    @project    security26
    @class      BookService
    @version    1.0.0
    @since      11.09.2026 - 16.31.31
*/


import chnu.cs.security26.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>(List.of(
            new Book("1", "Harry Potter", "J.K. Rowling", "A young wizard discovers his magical abilities."),
            new Book("2", "The Hobbit", "J.R.R. Tolkien", "The adventure of Bilbo Baggins."),
            new Book("3", "1984", "George Orwell", "A dystopian novel about a totalitarian society.")
    ));

    public Book createBook(Book book) {
        books.add(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Book updateBook(String id, Book updatedBook) {
        Book book = getById(id);

        if (book == null) {
            return null;
        }

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setDescription(updatedBook.getDescription());

        return book;
    }

    public boolean deleteBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
