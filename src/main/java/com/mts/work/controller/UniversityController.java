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
@RequiredArgsConstructor
@Validated
public class UniversityController implements UniversityOperation {
    private final UniversityService universityService;

    public ResponseEntity<University> getUniversityById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(universityService.getById(id));
    }

    public ResponseEntity<String> saveUniversity(@RequestBody University university)
    {
        long id = universityService.create(university);
        return new ResponseEntity<>("University created! ID: " + id, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteUniversityById(@PathVariable Long id) throws EntityNotFound {
        universityService.deleteById(id);
        return new ResponseEntity<>("University deleted! ID: " + id, HttpStatus.OK);
    }

    public ResponseEntity<String> updateUniversity(@PathVariable Long id, @RequestBody University University) throws EntityNotFound {
        University university = universityService.getById(id);
        University.setId(university.getId());
        University.setUserId(university.getUserId());
        universityService.update(University);
        return new ResponseEntity<>("University updated! ID: " + id, HttpStatus.OK);
    }
}
