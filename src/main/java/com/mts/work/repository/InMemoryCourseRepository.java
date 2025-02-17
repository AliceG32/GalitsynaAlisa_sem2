package com.mts.work.repository;

import com.mts.work.entity.Course;
import com.mts.work.entity.Course;
import com.mts.work.entity.CourseId;
import com.mts.work.repository.exception.EntityNotFound;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCourseRepository implements CourseRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  Map<Long, Course> data = new ConcurrentHashMap<>();

  @Override
  public synchronized long create(Course course) {
    course.setId(new CourseId(this.nextId.incrementAndGet()));
    data.put(this.nextId.longValue(), course);

    return this.nextId.longValue();
  }

  @Override
  public Course getById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      return data.get(id);
    } else {
      throw new EntityNotFound("Cannot find course by id=" + id);
    }
  }
  @Override
  public void deleteById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      data.remove(id);
    } else {
      throw new EntityNotFound("Cannot find course by id=" + id);
    }
  }

  @Override
  public void update(Course course) throws EntityNotFound {
    if (data.containsKey(course.getId().getValue())) {
      data.put(course.getId().getValue(), course);
    } else {
      throw new EntityNotFound("Cannot find course by id=" + course.getId());
    }
  }
}
