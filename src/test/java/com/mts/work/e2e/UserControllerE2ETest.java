package com.mts.work.e2e;

import com.mts.work.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import static org.testng.AssertJUnit.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() {
        User user = new User("User name");
        String url = "http://localhost:%d/user".formatted(port);
        ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created! ID: 1", response.getBody());

        url = "http://localhost:%d/user/1".formatted(port);
        ResponseEntity<User> responseGet = restTemplate.getForEntity(url, User.class);
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertEquals(user, responseGet.getBody());

        ResponseEntity<String> responseDelete = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertEquals("User deleted! ID: 1", responseDelete.getBody());

        url = "http://localhost:%d/user/1".formatted(port);
        ResponseEntity<String> responseGet2 = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseGet2.getStatusCode());
        assertEquals("Cannot find user by id=1", responseGet2.getBody());
    }
}