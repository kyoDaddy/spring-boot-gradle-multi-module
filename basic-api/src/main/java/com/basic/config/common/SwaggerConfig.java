package com.basic.config.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;

/**
 * swagger 3.0 부터 ui 접근 url이 바뀜
 * http://localhost:port/swagger-ui/index.html
 */
@Configuration
@Profile({"!live"})
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.basic.process.controllers")) //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**")) //.paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemeList())
                .securityContexts(singletonList(securityContext()))
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("basic api")
                .description("basic-api 상세소개 및 사용법 등")
                .contact(new Contact("kyo", "https://github.com/kyoDaddy", "rlarbghrbgh@gmail.com"))
                .version("1.0.0")
                .build();
    }

    private List<SecurityScheme> securitySchemeList() {
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey("API 접근 키", "service-key", "header"));
        list.add(new ApiKey("API 접근 아이디", "service-id", "header"));
        return list;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(customizeAuth())
                //.forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> customizeAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return newArrayList(
                new SecurityReference("API 접근 아이디", authorizationScopes)
                ,new SecurityReference("API 접근 키", authorizationScopes)
        );
    }



/*
    // autowird 경고 떠서, 생성자 주입으로 바꿈..
    // final 의 장점은 누군가가 클래스 내부에서 객체를 바꿔치기 할 수 없다.
    private final TypeResolver typeResolver;
    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(Map.class, String.class, typeResolver.resolve(LocalDate.class)),
                                typeResolver.resolve(Map.class, String.class, Date.class),
                                Ordered.HIGHEST_PRECEDENCE
                        )
                )
                .alternateTypeRules(
                        newRule(typeResolver.resolve(ResponseEntity.class),
                                typeResolver.resolve(Void.class)
                        )
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.basic.process.controllers")) //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**")) //.paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)
                        )
                )
                .useDefaultResponseMessages(false)
                //.consumes(getConsumeContentTypes())
                //.produces(getProduceContentTypes())
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemeList())
                .securityContexts(singletonList(securityContext()))
                .enableUrlTemplating(true);

                */
/*
                .globalRequestParameters(
                        singletonList(new springfox.documentation.builders.RequestParameterBuilder()
                                .name("someGlobalParameter")
                                .description("Description of someGlobalParameter")
                                .in(ParameterType.QUERY)
                                .required(true)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()));
                *//*

                */
/*
                .globalResponses(HttpMethod.GET,
                        singletonList(new ResponseBuilder()
                                .code("500")
                                .description("500 message")
                                .representation(MediaType.TEXT_XML)
                                .apply(r ->
                                        r.model(m ->
                                                m.referenceModel(ref ->
                                                        ref.key(k ->
                                                                k.qualifiedModelName(q ->
                                                                        q.namespace("some:namespace")
                                                                                .name("ERROR"))))))
                                .build()))

                 *//*

                */
/*
                .tags(new Tag("Base Service", "All apis relating to base"));
                *//*

                */
/* request object mapping
                //.additionalModels(typeResolver.resolve(AdditionalModel.class));
                *//*



    }



    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private List<SecurityScheme> securitySchemeList() {
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey("API 접근 키", "service-key", "header"));
        list.add(new ApiKey("API 접근 아이디", "service-id", "header"));
        return list;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(customizeAuth())
                //.forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> customizeAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return newArrayList(
                new SecurityReference("API 접근 아이디", authorizationScopes)
                ,new SecurityReference("API 접근 키", authorizationScopes)
        );
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("service-id")
                .clientSecret("service-key")
                .realm("base-api-realm")
                .appName("base-api")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .enableCsrfSupport(false)
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
*/

}
