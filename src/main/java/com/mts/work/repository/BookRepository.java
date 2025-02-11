package com.mts.work.repository;

import com.mts.work.entity.Book;
import com.mts.work.repository.exception.EntityNotFound;

public interface BookRepository {
    long create(Book user);

    Book getById(long id) throws EntityNotFound;
}
