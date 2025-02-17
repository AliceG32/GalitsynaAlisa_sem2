package com.mts.work.repository;

import com.mts.work.entity.University;
import com.mts.work.repository.exception.EntityNotFound;

public interface UniversityRepository {
  long create(University user);

  University getById(long id) throws EntityNotFound;

  void deleteById(long id) throws EntityNotFound;

  void update(University university) throws EntityNotFound;
}
