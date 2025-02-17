package com.mts.work.controller;

import com.mts.work.entity.Course;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class CourseController implements CourseOperation {
    private final CourseService courseService;

    public ResponseEntity<Course> getCourseById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(courseService.getById(id));
    }

    public ResponseEntity<String> saveCourse(@RequestBody Course course)
    {
        long id = courseService.create(course);
        return new ResponseEntity<>("Course created! ID: " + id, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) throws EntityNotFound {
        courseService.deleteById(id);
        return new ResponseEntity<>("Course deleted! ID: " + id, HttpStatus.OK);
    }

    public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody Course Course) throws EntityNotFound {
        Course course = courseService.getById(id);
        Course.setId(course.getId());
        Course.setUserId(course.getUserId());
        courseService.update(Course);
        return new ResponseEntity<>("Course updated! ID: " + id, HttpStatus.OK);
    }
}
