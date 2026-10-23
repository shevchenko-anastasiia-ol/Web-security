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
import java.util.Optional;

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
    public Optional<Book> getBookById(@PathVariable String id) {
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

    @GetMapping("/hello/user")
    public String helloUser(){
        return "Hello User!";
    }

    @GetMapping("/hello/admin")
    public String helloAdmin(){
        return "Hello Admin!";
    }

    @GetMapping("/hello/unknown")
    public String helloUnknown(){
        return "Hello Unknown!";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello!";
    }

    @GetMapping("/hello/man")
    public String helloMan(){
        return "Hello, man!";
    }


}
