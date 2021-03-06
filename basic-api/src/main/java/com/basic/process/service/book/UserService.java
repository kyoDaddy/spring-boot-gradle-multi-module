package com.basic.process.service.book;

import com.basic.process.models.entities.book.User;
import com.basic.process.models.vo.book.UserVo;
import com.basic.process.repository.book.UserRepository;
import com.basic.process.mappers.book.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    /** 전체 회원 목록 반환 */
    public List<User> findAll() { return userRepository.findAll(); }
    /** 단건 회원 반환 */
    public Optional<User> findById(Long id) { return userRepository.findById(id); }

    /**
     * 다중으로 호출시 -> @Transactional 메서드를 내부적으로 사용하지 않는것이 근본적인 해결책
     * @param user
     */
    @Transactional
    public User save(User user) { return userRepository.save(user); }

    public List<UserVo> selectUser(UserVo vo) {
        return userMapper.selectUser(vo);
    }

    /* 사용자 정보 (단일) */
    public UserVo getUser(UserVo vo) {
        return userMapper.getUser(vo);
    }



}
