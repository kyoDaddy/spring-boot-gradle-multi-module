package com.basic;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("local")
@SpringBootTest
class BasicApplicationTests {
    @Autowired
    Environment environment;

    @Test
    void contextLoads() {
        System.out.println("on port (yml test) : " + environment.getProperty("server.port"));
        assertThat(environment.getProperty("server.port").equals("8081"));
    }

}
