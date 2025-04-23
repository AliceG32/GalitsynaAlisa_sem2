package com.mts.work.controller;

import com.mts.work.entity.University;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.UniversityService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
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
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("apiCircuitBreaker");

  public ResponseEntity<University> getUniversityById(@PathVariable Integer id) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> {
              try {
                return ResponseEntity.ok().body(universityService.getById(id));
              } catch (EntityNotFound e) {
                throw new RuntimeException(e);
              }
    });
  }

  public ResponseEntity<String> saveUniversity(@RequestBody University university) {
    return circuitBreaker.executeSupplier(() -> {
      long id = universityService.create(university);
      return new ResponseEntity<>("University created! ID: " + id, HttpStatus.CREATED);
    });
  }

  public ResponseEntity<String> deleteUniversityById(@PathVariable Integer id) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> {
      try {
        universityService.deleteById(id);
        return new ResponseEntity<>("University deleted! ID: " + id, HttpStatus.OK);
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
    });
  }

  public ResponseEntity<String> updateUniversity(@PathVariable Integer id, @RequestBody University University) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> {
      try {
        University university = universityService.getById(id);
        University.setId(university.getId());
        University.setUserId(university.getUserId());
        universityService.update(University);
        return new ResponseEntity<>("University updated! ID: " + id, HttpStatus.OK);
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
    });
  }
}
