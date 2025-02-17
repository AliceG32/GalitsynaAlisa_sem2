package com.mts.work.service;

import com.mts.work.entity.Course;
import com.mts.work.repository.CourseRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository repository;

    public Course getById(Long id) throws EntityNotFound {
        return repository.getById(id);
    }

    public long create(Course Course){
        long id = repository.create(Course);

        log.info("Course with id: {} created successfully", Course.getId());
        return id;
    }
    public void deleteById(Long id) throws EntityNotFound {
        repository.deleteById(id);
    }

    public void update(Course course) throws EntityNotFound {
        repository.update(course);
    }
}
