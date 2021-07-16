package com.basic.process.repository.test;

import com.basic.process.models.entities.book.User;
import com.basic.process.models.entities.test.Product;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends Repository<Product, Long> {
    Optional<List<Product>> findAll();
}
