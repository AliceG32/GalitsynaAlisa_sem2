package com.mts.work.repository;

import com.mts.work.entity.Book;
import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;

public interface BookRepository {
    long create(Book user);

    Book getById(long id) throws EntityNotFound;
    void deleteById(long id) throws EntityNotFound;
    void update(Book book) throws EntityNotFound;
}
