package com.basic.config.common;

import com.basic.config.interceptor.ControllerInterceptor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * WebMvcConfigurer
 *      -> 스프링 부트가 제공해주는 웹MVC의 기능들을 확장하고 싶을 때 추가적으로 설정
 *
 * @RequiredArgsConstructor
 *      -> final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
 *
 * 정적 리소스
 *      -> Spring Boot Web MVC 기본 설정에서 정적 리소스를 제공한다. 정적 리소스란 웹 브라우저에서 요청이 들어왔을 때 이미 만들어져있는 리소스
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /* @RequiredArgsConstructor 통한 생성자 주입 */
    private final ControllerInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.ico");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/swagger-ui/**")
                .addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
                //.setCachePeriod(604800);
                /* 캐시제어 (Last-Modified 헤더를 보고 304 응답을 보냄)
                   세밀제어시 -> .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePublic())
                */
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui/index.html");
    }

}
