package com.mts.work.controller;

import com.mts.work.entity.Course;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.CourseService;
import io.github.resilience4j.ratelimiter.RateLimiter;
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
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("apiRateLimiter");

    public ResponseEntity<Course> getCourseById(@PathVariable Integer id){
        return rateLimiter.executeSupplier(() -> {
          try {
            return ResponseEntity.ok().body(courseService.getById(id));
          } catch (EntityNotFound e) {
            throw new RuntimeException(e);
          }
        });
    }

    public ResponseEntity<String> saveCourse(@RequestBody Course course)
    {
        return rateLimiter.executeSupplier(() -> {
          long id = courseService.create(course);
          return new ResponseEntity<>("Course created! ID: " + id, HttpStatus.CREATED);
        });
    }

    public ResponseEntity<String> deleteCourseById(@PathVariable Integer id){
        return rateLimiter.executeSupplier(() -> {
          try {
            courseService.deleteById(id);
          } catch (EntityNotFound e) {
            throw new RuntimeException(e);
          }
          return new ResponseEntity<>("Course deleted! ID: " + id, HttpStatus.OK);
        });
    }

    public ResponseEntity<String> updateCourse(@PathVariable Integer id, @RequestBody Course Course) {
        return rateLimiter.executeSupplier(() -> {
          Course course = null;
          try {
            course = courseService.getById(id);
          } catch (EntityNotFound e) {
            throw new RuntimeException(e);
          }
          Course.setId(course.getId());
            Course.setUserId(course.getUserId());
          try {
            courseService.update(Course);
          } catch (EntityNotFound e) {
            throw new RuntimeException(e);
          }
          return new ResponseEntity<>("Course updated! ID: " + id, HttpStatus.OK);
        });
    }
}
