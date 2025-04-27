package com.mts.work.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import com.mts.work.service.UserService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;


@RestController
@Validated
public class UserController implements UserOperation {
  private final UserService userService;
  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("apiCircuitBreaker");
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("apiRateLimiter");

  public UserController(UserService userService, MeterRegistry registry) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<User> getUserById(@PathVariable Integer id) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      try {
        return ResponseEntity.ok().body(userService.getById(id));
      } catch (EntityNotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
      }
    }));
  }

  @Override
  public User getUserByIdRestTemplate(Integer id) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      return restTemplate.getForObject("http://localhost:8080/user/" + id, User.class);
    }));
  }

  @Override
  public User getUserByIdWebClient(Integer id) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      return webClient.get().uri("http://localhost:8080/user/" + id).retrieve().bodyToMono(User.class).block();
    }));
  }

  @Timed(
          value = "add.user.request.duration",
          description = "Add User HTTP requests duration",
          percentiles = {0.5, 0.9, 0.99},
          extraTags = {"type", "Add User"},
          histogram = true)
  @Override
  public ResponseEntity<String> saveUser(@RequestBody User user, @RequestHeader("userId") String header) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      long id = 0;
      try {
        id = userService.create(user, header);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      return new ResponseEntity<>("User created! ID: " + id, HttpStatus.CREATED);
    }));
  }

  @Override
  public ResponseEntity<String> deleteUserById(@PathVariable Integer id, @RequestHeader("userId") String header) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {

      try {
        userService.deleteById(id, header);
      } catch (EntityNotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      return new ResponseEntity<>("User deleted! ID: " + id, HttpStatus.OK);
    }));
  }

  @Override
  public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody User user, @RequestHeader("userId") String header) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      try {
        user.setId(id);
        userService.update(user, header);
      } catch (EntityNotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      return new ResponseEntity<>("User updated! ID: " + id, HttpStatus.OK);
    }));
  }
}
