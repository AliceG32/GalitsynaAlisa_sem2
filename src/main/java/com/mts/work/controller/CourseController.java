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
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(courseService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<String> saveCourse(@RequestBody Course course)
    {
        long id = courseService.create(course);
        return new ResponseEntity<>("Course created! ID: " + id, HttpStatus.CREATED);
    }
}
