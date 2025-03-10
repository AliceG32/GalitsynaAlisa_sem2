package com.mts.work.service;

import com.mts.work.entity.User;
import com.mts.work.repository.UserRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository repository;

  public List<User> getAll() {
    return repository.findAll();
  }

  public User getById(Integer id) throws EntityNotFound {
    Optional<User> optionalItem = Optional.of(
            repository.findById(id).orElseThrow(() -> new EntityNotFound("User not found"))
    );
    return optionalItem.get();
  }

  public Integer create(User User) {
    User savedItem = repository.save(User);

    log.info("User with id: {} saved successfully", User.getId());
    return savedItem.getId();
  }

  public Optional<User> update(User user) throws EntityNotFound {
    Optional<User> optionalItem = repository.findById(user.getId());
    if (optionalItem.isEmpty()) {
      log.info("User with id: {} doesn't exist", user.getId());
      throw new EntityNotFound("User with id: " + user.getId() + " doesn't exist");
    }
    repository.save(user);
    log.info("User with id: {} updated successfully", user.getId());
    return optionalItem;
  }

  public void deleteById(Integer id) throws EntityNotFound {
    Optional<User> optionalItem = repository.findById(id);
    if (optionalItem.isEmpty()) {
      log.info("User with id: {} doesn't exist", id);
      throw new EntityNotFound("User with id: " + id + " doesn't exist");
    }

    repository.deleteById(id);
  }
}
