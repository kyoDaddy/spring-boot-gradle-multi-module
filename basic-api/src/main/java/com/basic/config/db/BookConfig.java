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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;
import java.util.Iterator;

/**
 *  1. @EnableJpaRepositories
 *      - multiple datasource 설정 시, 연동시킬 repositories 패키지와 entityManagerFactoryRef, transactionManagerRef를 설정
 *
 *  2. @ConfigurationProperties
 *      - spring.datasource.hikari에 설정한 구성 정보를 가져와 dataSource 빈을 구성
 *      - spring.jpa에 설정한 jpa 관련 설정을 읽어들여 jpa 설정
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.basic.process.repository.book", entityManagerFactoryRef = "book-emf", transactionManagerRef = "book-tm")
@MapperScan(basePackages = {"com.basic.process.mappers.book"}, sqlSessionFactoryRef = "book-ssf", sqlSessionTemplateRef = "book-sst")
public class BookConfig {

    @Bean(name="book-ds")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.book")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "book-jpa-prop")
    @ConfigurationProperties(prefix = "spring.jpa.book")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "book-emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("book-ds") DataSource primaryDataSource,
                                                                       @Qualifier("book-jpa-prop") JpaProperties jpaProperties) {
        /*
        for( String key : jpaProperties.getProperties().keySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", key, jpaProperties.getProperties().get(key)) );
        }
        */

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(primaryDataSource);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("com.basic.process.models.entities.book");
        emf.setJpaPropertyMap(jpaProperties.getProperties());
        emf.setPersistenceUnitName("default");

        return emf;
    }

    @Bean(name = "book-tm")
    public PlatformTransactionManager transactionManager(@Qualifier("book-emf") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
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

    @Bean(name = "book-sst")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("book-ssf") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
