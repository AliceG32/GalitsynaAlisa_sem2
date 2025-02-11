package com.mts.work.repository;

import com.mts.work.entity.University;
import com.mts.work.entity.UniversityId;
import com.mts.work.repository.exception.EntityNotFound;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUniversityRepository implements UniversityRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  Map<Long, University> data = new ConcurrentHashMap<>();

  @Override
  public synchronized long create(University university) {
    university.setId(new UniversityId(this.nextId.incrementAndGet()));
    data.put(this.nextId.longValue(), university);

    return this.nextId.longValue();
  }

  @Override
  public University getById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      return data.get(id);
    } else {
      throw new EntityNotFound("Cannot find university by id=" + id);
    }
  }
}
