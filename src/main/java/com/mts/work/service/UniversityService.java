package com.mts.work.service;

import com.mts.work.entity.Book;
import com.mts.work.entity.University;
import com.mts.work.repository.UniversityRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityService {
    private final UniversityRepository repository;

    public University getById(Long id) throws EntityNotFound {
        return repository.getById(id);
    }

    public long create(University University){
        long id = repository.create(University);

        log.info("University with id: {} created successfully", University.getId());
        return id;
    }
    public void deleteById(Long id) throws EntityNotFound {
        repository.deleteById(id);
    }

    public void update(University university) throws EntityNotFound {
        repository.update(university);
    }
}
