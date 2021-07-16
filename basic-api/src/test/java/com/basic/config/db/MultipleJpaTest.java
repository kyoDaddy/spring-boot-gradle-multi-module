package com.basic.config.db;

import com.basic.process.repository.book.UserRepository;
import com.basic.process.repository.test.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
public class MultipleJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Test
    public void userTest() {
        userRepository.findAll()
                .stream()
                .map(i -> i.toString())
                .forEach(System.out::println);
    }

    @Test
    public void productTest() {
        testRepository.findAll()
                .stream()
                .map(i -> i.toString())
                .forEach(System.out::println);
    }

}
