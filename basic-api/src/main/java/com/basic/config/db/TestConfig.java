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
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.basic.process.repository.test", entityManagerFactoryRef = "test-emf", transactionManagerRef = "test-tm")
@MapperScan(basePackages = {"com.basic.process.mappers.test"}, sqlSessionFactoryRef = "test-ssf", sqlSessionTemplateRef = "test-sst")
public class TestConfig {

    @Bean(name="test-ds")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.test")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test-jpa-prop")
    @ConfigurationProperties(prefix = "spring.jpa.test")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "test-emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("test-ds") DataSource primaryDataSource,
                                                                       @Qualifier("test-jpa-prop") JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(primaryDataSource);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("com.basic.process.models.entities.test");
        emf.setJpaPropertyMap(jpaProperties.getProperties());
        emf.setPersistenceUnitName("default");

        return emf;
    }

    @Bean(name = "test-tm")
    public PlatformTransactionManager transactionManager(
            @Qualifier("test-emf") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    @Bean(name="test-ssf")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("test-ds") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.basic.process.models.vo.test");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/test/*-mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "test-sst")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test-ssf") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
