package com.mts.work.service;

import com.mts.work.entity.User;
import com.mts.work.repository.UserRepository;
import com.mts.work.repository.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;

    public User getById(Long id) throws EntityNotFound {
        return repository.getById(id);
    }

    public long create(User User){
        long id = repository.create(User);

        log.info("User with id: {} created successfully", User.getId());
        return id;
    }

    public void deleteById(Long id) throws EntityNotFound {
        repository.deleteById(id);
    }

    public void update(User user) throws EntityNotFound {
        repository.update(user);
    }
}
