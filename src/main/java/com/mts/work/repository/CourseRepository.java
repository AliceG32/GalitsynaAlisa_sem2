package com.mts.work.repository;

import com.mts.work.entity.Course;
import com.mts.work.repository.exception.EntityNotFound;

public interface CourseRepository {
    long create(Course user);

    Course getById(long id) throws EntityNotFound;
    void deleteById(long id) throws EntityNotFound;
    void update(Course course) throws EntityNotFound;

}
