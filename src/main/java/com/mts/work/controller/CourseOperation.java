package com.mts.work.controller;

import com.mts.work.entity.Course;
import com.mts.work.repository.exception.EntityNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/course")
@Tag(name = "Course API", description = "Управление курсами")
public interface CourseOperation {
    @GetMapping("/{id}")
    @Operation(summary = "Получить курс по ID")
    @ApiResponse(responseCode = "200", description = "Курс найден")
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    ResponseEntity<Course> getCourseById(@PathVariable Long id) throws EntityNotFound;

    @PostMapping("")
    @Operation(summary = "Создать курс по ID")
    @ApiResponse(responseCode = "200", description = "Курс создана")
    ResponseEntity<String> saveCourse(@RequestBody Course course);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить курс по ID")
    @ApiResponse(responseCode = "200", description = "Курс удален")
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    ResponseEntity<String> deleteCourseById(@PathVariable Long id) throws EntityNotFound;

    @PutMapping("/{id}")
    @Operation(summary = "Изменить курс по ID")
    @ApiResponse(responseCode = "200", description = "Курс изменен")
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody Course Course) throws EntityNotFound;
}
