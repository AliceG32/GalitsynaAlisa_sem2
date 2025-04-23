package com.mts.work.service;

import com.mts.work.entity.Course;
import com.mts.work.repository.CourseRepository;
import com.mts.work.repository.exception.EntityNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
  private final CourseRepository repository;

  public Course getById(Integer id) throws EntityNotFound {
    Optional<Course> optionalItem = Optional.of(
            repository.findById(id).orElseThrow(() -> new EntityNotFound("Course not found"))
    );
    return optionalItem.get();
  }

  @Transactional
  public long create(Course Course) {
    Course savedItem = repository.save(Course);

    log.info("Course with id: {} created successfully", Course.getId());
    return savedItem.getId();
  }

  @Transactional
  public Optional<Course> update(Course course) throws EntityNotFound {
    Optional<Course> optionalItem = repository.findById(course.getId());
    if (optionalItem.isEmpty()) {
      log.info("Course with id: {} doesn't exist", course.getId());
      throw new EntityNotFound("Course with id: " + course.getId() + " doesn't exist");
    }
    repository.save(course);
    log.info("Course with id: {} updated successfully", course.getId());
    return optionalItem;
  }

  public void deleteById(Integer id) throws EntityNotFound {
    Optional<Course> optionalItem = repository.findById(id);
    if (optionalItem.isEmpty()) {
      log.info("Course with id: {} doesn't exist", id);
      throw new EntityNotFound("Course with id: " + id + " doesn't exist");
    }

    repository.deleteById(id);
  }
}
