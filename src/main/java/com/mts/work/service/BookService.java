package com.mts.work.service;

import com.mts.work.entity.Book;
import com.mts.work.repository.BookRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
  private final BookRepository repository;
  private final Set<Long> processedIds = ConcurrentHashMap.newKeySet();

  // Гарантия того, что сообщение будет доставлено ровно один раз
  public Book getById(Long id) throws EntityNotFound {
    if (!processedIds.add(id)) {
      throw new RuntimeException("Already processed");
    }
    return repository.getById(id);
  }

  // Гарантирует, что сообщение будет доставлено хотя бы один раз, но возможны дубликаты.
  // Максимальное количество попыток 5, пауза 10 сек.
  @Retryable(retryFor = RuntimeException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
  public long create(Book Book) {
    long id = repository.create(Book);
    log.info("Book with id: {} created successfully", Book.getId());
    return id;
  }

  public void deleteById(Long id) throws EntityNotFound {
    repository.deleteById(id);
  }

  public void update(Book book) throws EntityNotFound {
    repository.update(book);
  }
}
