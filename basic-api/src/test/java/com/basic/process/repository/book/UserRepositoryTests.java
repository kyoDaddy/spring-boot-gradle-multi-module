package com.basic.process.repository.book;

import com.basic.process.models.entities.book.User;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private  UserRepository userRepository;

    @Test
    public void crate() {

        User user = new User(null,"테스트2", "test@test.com");
        User created = userRepository.save(user);

        System.out.println(created.toString());

        created.setEmail("test2@test.com");

        User updated = userRepository.save(created);
        System.out.println(updated.toString());

    }

    @Test
    public void del() {

        Long userSeq = 48L;
        User user = new User(userSeq,"테스트2", "test@test.com");

        userRepository.findById(userSeq)
                .map(user1 -> {
                    userRepository.delete(user1);
                    return true;
                })
                //.orElseThrow()
        ;
        //userRepository.deleteById(49L);

    }





}
