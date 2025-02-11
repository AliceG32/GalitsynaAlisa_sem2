package com.mts.work.controller;

import com.mts.work.entity.University;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
@Validated
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(universityService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<String> saveUniversity(@RequestBody University university)
    {
        long id = universityService.create(university);
        return new ResponseEntity<>("University created! ID: " + id, HttpStatus.CREATED);
    }
}
