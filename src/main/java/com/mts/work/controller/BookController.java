package com.mts.work.controller;

import com.mts.work.entity.Book;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(bookService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<String> saveBook(@RequestBody Book book)
    {
        long id = bookService.create(book);
        return new ResponseEntity<>("Book created! ID: " + id, HttpStatus.CREATED);
    }
}
