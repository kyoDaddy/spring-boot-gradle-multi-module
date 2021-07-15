package com.basic.process.service.book;

import com.basic.process.models.entities.book.User;
import com.basic.process.models.vo.book.UserVo;
import com.basic.process.repository.book.UserRepository;
import com.basic.process.mappers.book.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Optional<User> selectById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserVo> selectUser(UserVo vo) {
        return userMapper.selectUser(vo);
    }

    /* 사용자 정보 (단일) */
    public UserVo getUser(UserVo vo) {
        return userMapper.getUser(vo);
    }

}
