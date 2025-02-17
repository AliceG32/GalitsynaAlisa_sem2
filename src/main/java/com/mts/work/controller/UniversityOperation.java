package com.mts.work.controller;

import com.mts.work.entity.University;
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

@RequestMapping("/university")
@Tag(name = "University API", description = "Управление университетами")
public interface UniversityOperation {
    @GetMapping("/{id}")
    @Operation(summary = "Получить университет по ID")
    @ApiResponse(responseCode = "200", description = "Университет найден")
    @ApiResponse(responseCode = "404", description = "Университет не найден")
    ResponseEntity<University> getUniversityById(@PathVariable Long id) throws EntityNotFound;

    @PostMapping("")
    @Operation(summary = "Создать университет по ID")
    @ApiResponse(responseCode = "200", description = "Университет создан")
    ResponseEntity<String> saveUniversity(@RequestBody University university);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить университет по ID")
    @ApiResponse(responseCode = "200", description = "Университет удален")
    @ApiResponse(responseCode = "404", description = "Университет не найден")
    ResponseEntity<String> deleteUniversityById(@PathVariable Long id) throws EntityNotFound;

    @PutMapping("/{id}")
    @Operation(summary = "Изменить университет по ID")
    @ApiResponse(responseCode = "200", description = "Университет изменена")
    @ApiResponse(responseCode = "404", description = "Университет не найдена")
    ResponseEntity<String> updateUniversity(@PathVariable Long id, @RequestBody University University) throws EntityNotFound;
}