package com.basic.process.mappers.book;

import com.basic.process.models.vo.book.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserMapper {

    /* sqlSession */
    @Resource(name="book-sst")
    private SqlSession sqlSession;

    /* TRANSACTION MANAGER */
    @Qualifier("book-tm")
    private DataSourceTransactionManager transactionManager;

    private final String userMapperNameSpace = "com.basic.process.mappers.book.UserMapper.";

    /* 사용자 정보 (목록) */
    public List<UserVo> selectUser(UserVo vo) {
        return this.sqlSession.selectList(userMapperNameSpace + "selectUser", vo);
    }

    /* 사용자 정보 (단일) */
    public UserVo getUser(UserVo vo) {
        return this.sqlSession.selectOne(userMapperNameSpace + "selectUser", vo);
    }

}
