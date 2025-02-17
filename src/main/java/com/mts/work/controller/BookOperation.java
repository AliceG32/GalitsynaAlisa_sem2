package com.mts.work.controller;

import com.mts.work.entity.Book;
import com.mts.work.repository.exception.EntityNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/book")
@Tag(name = "Book API", description = "Управление книгами")
public interface BookOperation {
  @GetMapping("/{id}")
  @Operation(summary = "Получить книгу по ID")
  @ApiResponse(responseCode = "200", description = "Книга найдена")
  @ApiResponse(responseCode = "404", description = "Книга не найдена")
  ResponseEntity<Book> getBookById(@PathVariable Long id) throws EntityNotFound;

  @PostMapping("")
  @Operation(summary = "Создать книгу по ID")
  @ApiResponse(responseCode = "200", description = "Книга создана")
  ResponseEntity<String> saveBook(@RequestBody Book book);

  @DeleteMapping("/{id}")
  @Operation(summary = "Удалить книгу по ID")
  @ApiResponse(responseCode = "200", description = "Книга удалена")
  @ApiResponse(responseCode = "404", description = "Книга не найдена")
  ResponseEntity<String> deleteBookById(@PathVariable Long id) throws EntityNotFound;

  @PutMapping("/{id}")
  @Operation(summary = "Изменить книгу по ID")
  @ApiResponse(responseCode = "200", description = "Книга изменена")
  @ApiResponse(responseCode = "404", description = "Книга не найдена")
  ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book Book) throws EntityNotFound;
}
