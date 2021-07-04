package com.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class BasicApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BasicApiApplication.class);
        app.addListeners(new ApplicationPidFileWriter());

        // 배너 등록
        app.setBanner( (environment, sourceClass, out) -> {
            out.println("============");
            out.println("basic-api :)");
            out.println("============");
        });
        // spring mvc restful api 구현 (reactive 방식은 다른 곳에서 해보기!)
        app.setWebApplicationType(WebApplicationType.SERVLET);

        app.run(args);
    }

}
