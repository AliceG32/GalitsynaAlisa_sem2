package com.mts.work.repository;

import com.mts.work.entity.User;
import com.mts.work.repository.exception.EntityNotFound;

public interface UserRepository {
    long create(User user);

    User getById(long id) throws EntityNotFound;
    void deleteById(long id) throws EntityNotFound;
    void update(User user) throws EntityNotFound;
}
