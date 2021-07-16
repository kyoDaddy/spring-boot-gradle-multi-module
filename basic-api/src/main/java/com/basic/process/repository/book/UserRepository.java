package com.basic.process.repository.book;

import java.util.List;
import java.util.Optional;

import com.basic.process.models.entities.book.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    Optional<List<User>> findAll();
    Optional<User> findById(Long userSeq);
}
