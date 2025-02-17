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
@RequiredArgsConstructor
@Validated
public class UserController implements UserOperation {
    private final UserService userService;

    @Override
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws EntityNotFound {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @Override
    public ResponseEntity<String> saveUser(@RequestBody User user)
    {
        long id = userService.create(user);
        return new ResponseEntity<>("User created! ID: " + id, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws EntityNotFound {
        userService.deleteById(id);
        return new ResponseEntity<>("User deleted! ID: " + id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User User) throws EntityNotFound {
        User user = userService.getById(id);
        User.setId(user.getId());
        userService.update(User);
        return new ResponseEntity<>("User updated! ID: " + id, HttpStatus.OK);
    }
}
