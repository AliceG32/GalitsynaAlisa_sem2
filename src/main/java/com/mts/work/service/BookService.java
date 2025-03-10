package com.mts.work.service;

import com.mts.work.entity.Book;
import com.mts.work.repository.BookRepository;
import com.mts.work.repository.exception.EntityNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
  private final BookRepository repository;

  public List<Book> getAll() {
    return repository.findAll();
  }

  public Book getById(Integer id) throws EntityNotFound {
    Optional<Book> optionalItem = Optional.of(
            repository.findById(id).orElseThrow(() -> new EntityNotFound("Book not found"))
    );
    return optionalItem.get();
  }

  @Transactional
  public Integer create(Book Book) {
    Book savedItem = repository.save(Book);

    log.info("Book with id: {} saved successfully", Book.getId());
    return savedItem.getId();
  }

  @Transactional
  public Optional<Book> update(Book book) throws EntityNotFound {
    Optional<Book> optionalItem = repository.findById(book.getId());
    if (optionalItem.isEmpty()) {
      log.info("Book with id: {} doesn't exist", book.getId());
      throw new EntityNotFound("Book with id: " + book.getId() + " doesn't exist");
    }
    repository.save(book);
    log.info("Book with id: {} updated successfully", book.getId());
    return optionalItem;
  }

  public void deleteById(Integer id) throws EntityNotFound {
    Optional<Book> optionalItem = repository.findById(id);
    if (optionalItem.isEmpty()) {
      log.info("Book with id: {} doesn't exist", id);
      throw new EntityNotFound("Book with id: " + id + " doesn't exist");
    }

    repository.deleteById(id);
  }
}
