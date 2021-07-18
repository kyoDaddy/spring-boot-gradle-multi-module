package com.basic.process.repository.book;

import com.basic.process.models.entities.book.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
