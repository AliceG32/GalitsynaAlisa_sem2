package com.mts.work.service;

import com.mts.work.entity.Book;
import com.mts.work.repository.BookRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository repository;

    public Book getById(Long id) throws EntityNotFound {
        return repository.getById(id);
    }

    public long create(Book Book){
        long id = repository.create(Book);

        log.info("Book with id: {} created successfully", Book.getId());
        return id;
    }
}
