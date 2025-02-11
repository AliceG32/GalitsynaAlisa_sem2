package com.mts.work.repository;

import com.mts.work.entity.User;
import com.mts.work.entity.UserId;
import com.mts.work.repository.exception.EntityNotFound;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {
  private final AtomicLong nextId = new AtomicLong(0);
  Map<Long, User> data = new ConcurrentHashMap<>();

  @Override
  public synchronized long create(User user) {
    user.setId(new UserId(this.nextId.incrementAndGet()));
    data.put(this.nextId.longValue(), user);

    return this.nextId.longValue();
  }

  @Override
  public User getById(long id) throws EntityNotFound {
    if (data.containsKey(id)) {
      return data.get(id);
    } else {
      throw new EntityNotFound("Cannot find user by id=" + id);
    }
  }
}
