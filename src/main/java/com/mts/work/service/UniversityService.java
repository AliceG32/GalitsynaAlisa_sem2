package com.mts.work.service;

import com.mts.work.entity.Book;
import com.mts.work.entity.University;
import com.mts.work.entity.University;
import com.mts.work.entity.University;
import com.mts.work.repository.UniversityRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityService {
  private final UniversityRepository repository;

  public University getById(Integer id) throws EntityNotFound {
    Optional<University> optionalItem = Optional.of(
            repository.findById(id).orElseThrow(() -> new EntityNotFound("University not found"))
    );
    return optionalItem.get();
  }

  public long create(University University) {
    University saveItem = repository.save(University);

    log.info("University with id: {} created successfully", University.getId());
    return saveItem.getId();
  }

  public Optional<University> update(University university) throws EntityNotFound {
    Optional<University> optionalItem = repository.findById(university.getId());
    if (optionalItem.isEmpty()) {
      log.info("University with id: {} doesn't exist", university.getId());
      throw new EntityNotFound("University with id: " + university.getId() + " doesn't exist");
    }
    repository.save(university);
    log.info("University with id: {} updated successfully", university.getId());
    return optionalItem;
  }

  public void deleteById(Integer id) throws EntityNotFound {
    Optional<University> optionalItem = repository.findById(id);
    if (optionalItem.isEmpty()) {
      log.info("University with id: {} doesn't exist", id);
      throw new EntityNotFound("University with id: " + id + " doesn't exist");
    }

    repository.deleteById(id);
  }
}
