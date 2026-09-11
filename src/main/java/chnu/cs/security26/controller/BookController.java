package chnu.cs.security26.controller;

/*
    @author     Anastasiia Shevchenko
    @project    security26
    @class      BookController
    @version    1.0.0
    @since      11.09.2026 - 16:44.43
*/

import chnu.cs.security26.model.Book;
import chnu.cs.security26.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook( @PathVariable String id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}") public boolean deleteBook(@PathVariable String id) {
        return bookService.deleteBook(id);
    }
}
