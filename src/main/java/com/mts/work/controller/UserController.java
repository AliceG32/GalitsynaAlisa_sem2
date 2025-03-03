package com.mts.work.controller;

import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.UserService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequiredArgsConstructor
@Validated
public class UserController implements UserOperation {
  private final UserService userService;
  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("apiCircuitBreaker");
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("apiRateLimiter");

  @Override
  @Cacheable(value = "UserControllerUserCache", key = "#id")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      try {
        return ResponseEntity.ok().body(userService.getById(id));
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
    }));
  }

  @Override
  public User getUserByIdRestTemplate(Long id) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      return restTemplate.getForObject("http://localhost:8080/user/" + id, User.class);
    }));
  }

  @Override
  public User getUserByIdWebClient(Long id) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      return webClient.get().uri("http://localhost:8080/user/" + id).retrieve().bodyToMono(User.class).block();
    }));
  }

  @Override
  public ResponseEntity<String> saveUser(@RequestBody User user) {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      long id = userService.create(user);
      return new ResponseEntity<>("User created! ID: " + id, HttpStatus.CREATED);
    }));
  }

  @Override
  @CacheEvict(value = "UserControllerUserCache", key = "#id")
  public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      try {
        userService.deleteById(id);
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
      return new ResponseEntity<>("User deleted! ID: " + id, HttpStatus.OK);
    }));
  }

  @Override
  @CachePut(value = "UserControllerUserCache", key = "#id")
  public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User User) throws EntityNotFound {
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
      User user = null;
      try {
        user = userService.getById(id);
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
      User.setId(user.getId());
      try {
        userService.update(User);
      } catch (EntityNotFound e) {
        throw new RuntimeException(e);
      }
      return new ResponseEntity<>("User updated! ID: " + id, HttpStatus.OK);
    }));
  }
}
