package com.basic.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 *  1. @EnableJpaRepositories
 *      - multiple datasource 설정 시, 연동시킬 repositories 패키지와 entityManagerFactoryRef, transactionManagerRef를 설정
 *
 *  2. @ConfigurationProperties
 *      - spring.datasource.hikari에 설정한 구성 정보를 가져와 dataSource 빈을 구성
 *      - spring.jpa에 설정한 jpa 관련 설정을 읽어들여 jpa 설정
 *
 *
 *
 *
 *
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.basic.process.repository.book", entityManagerFactoryRef = "book-emf", transactionManagerRef = "book-tm")
@MapperScan(basePackages = {"com.basic.process.mappers.book"}, sqlSessionFactoryRef = "book-ssf", sqlSessionTemplateRef = "book-sst")
public class BookConfig {

    @Primary
    @Bean(name="book-ds")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "jpa-prop")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = "book-emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("book-ds") DataSource primaryDataSource,
                                                                       @Qualifier("jpa-prop") JpaProperties jpaProperties) {
        return builder
                .dataSource(primaryDataSource)
                .properties(jpaProperties.getProperties())
                .packages("com.basic.process.models.entities.book")
                .persistenceUnit("default")
                .build();
    }

    @Primary
    @Bean(name = "book-tm")
    public PlatformTransactionManager transactionManager(
            @Qualifier("book-emf") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }


    @Bean(name="book-ssf")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("book-ds") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.basic.process.models.vo.book");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/book/*-mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "book-sst")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("book-ssf") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
