package com.basic.process.mappers.test;

import com.basic.process.models.vo.book.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TestMapper {

    /* sqlSession */
    @Resource(name="test-sst")
    private SqlSession sqlSession;

    /* TRANSACTION MANAGER */
    @Qualifier("test-tm")
    private DataSourceTransactionManager transactionManager;

    private final String userMapperNameSpace = "com.basic.process.mappers.test.TestMapper.";


}
