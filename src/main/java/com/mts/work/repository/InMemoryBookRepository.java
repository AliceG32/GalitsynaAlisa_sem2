package com.mts.work.repository;

import com.mts.work.entity.Book;
import com.mts.work.entity.BookId;
import com.mts.work.repository.exception.EntityNotFound;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBookRepository implements BookRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  Map<Long, Book> data = new ConcurrentHashMap<>();

  @Override
  public synchronized long create(Book book) {
    book.setId(new BookId(this.nextId.incrementAndGet()));
    data.put(this.nextId.longValue(), book);

    return this.nextId.longValue();
  }

  @Override
  public Book getById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      return data.get(id);
    } else {
      throw new EntityNotFound("Cannot find book by id=" + id);
    }
  }

  @Override
  public void deleteById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      data.remove(id);
    } else {
      throw new EntityNotFound("Cannot find book by id=" + id);
    }
  }

  @Override
  public void update(Book book) throws EntityNotFound {
    if (data.containsKey(book.getId().getValue())) {
      data.put(book.getId().getValue(), book);
    } else {
      throw new EntityNotFound("Cannot find book by id=" + book.getId());
    }
  }
}
