package com.mts.work.controller.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;


@Testcontainers
public class PostgreSQLTest {

  @Container
  private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpass")
          .withInitScript("init.sql");

  @Test
  void testPostgresConnectionAndSchemaCreation() throws SQLException {
    postgres.start();
    String host = postgres.getHost();
    int port = postgres.getFirstMappedPort();
    String jdbcUrl = postgres.getJdbcUrl();

    System.out.printf("PostgreSQL container started at %s:%d%n", host, port);
    System.out.println("JDBC URL: " + jdbcUrl);
    postgres.stop();
  }
}
