package com.basic.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties
 *      - 자동 설정 클래스 내의 스프링 빈을 property 파일을 통해 재정의 하고자 할 때는 이 어노테이션을 사용한다.
 *      - build.gradle 내 spring-boot-configuration-processor dependency 추가 필요
 *      - Configuration bean 등록 후 사용
 *      - change active spring profile 설정 필요
*/
@Getter
@Setter
@ConfigurationProperties(prefix = "daemon")
public class DaemonProperties {

    private String nickName;
    private String rootUri;
    private String grpcIp;
    private int grpcPort;

}
