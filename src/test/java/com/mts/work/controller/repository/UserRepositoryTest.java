package com.mts.work.controller.repository;


import com.mts.work.entity.User;
import com.mts.work.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;

@DataJpaTest
@Transactional(value = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres");

  @Autowired
  UserRepository userRepository;

  @Test
  void create() {
    User testUser = userRepository.save(new User("UserName"));
    User responseUser = userRepository.findById(testUser.getId()).orElseThrow();

    assertEquals(testUser, responseUser);
  }

  @Test
  void getById() {
    Optional<User> responseUser = userRepository.findById(1);
    assertTrue(responseUser.isPresent());
  }
}
