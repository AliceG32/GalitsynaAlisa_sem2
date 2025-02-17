package com.mts.work.controller;

import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Tag(name = "User API", description = "Управление пользователями")
public interface UserOperation {
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    ResponseEntity<User> getUserById(@PathVariable Long id) throws EntityNotFound;

    @PostMapping("")
    @Operation(summary = "Создать пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь создан")
    ResponseEntity<String> saveUser(@RequestBody User user);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    ResponseEntity<String> deleteUserById(@PathVariable Long id) throws EntityNotFound;

    @PutMapping("/{id}")
    @Operation(summary = "Изменить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь изменен")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User User) throws EntityNotFound;
}
