package com.mts.work.controller;

import com.mts.work.entity.Book;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Validated
public class BookController implements BookOperation {
  private final BookService bookService;

  @Override
  @RateLimiter(name = "defaultRateLimiter")
  @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallbackMethod")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) throws EntityNotFound {
    return ResponseEntity.ok().body(bookService.getById(id));
  }

  @Override
  @Async
  public CompletableFuture<ResponseEntity<String>> saveBook(@RequestBody Book book) {
    long id = bookService.create(book);
    return CompletableFuture.completedFuture(new ResponseEntity<>("Book created! ID: " + id, HttpStatus.CREATED));
  }

  @Override
  public ResponseEntity<String> deleteBookById(@PathVariable Long id) throws EntityNotFound {
    bookService.deleteById(id);
    return new ResponseEntity<>("Book deleted! ID: " + id, HttpStatus.OK);
  }

    @Override
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book Book) throws EntityNotFound {
        Book book = bookService.getById(id);
        Book.setId(book.getId());
        Book.setUserId(book.getUserId());
        bookService.update(Book);
        return new ResponseEntity<>("Book updated! ID: " + id, HttpStatus.OK);
    }
}
