package com.mts.work.controller;

import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;
import com.mts.work.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<String> saveUser(@RequestBody User user)
    {
        long id = userService.create(user);
        return new ResponseEntity<>("User created! ID: " + id, HttpStatus.CREATED);
    }
}
