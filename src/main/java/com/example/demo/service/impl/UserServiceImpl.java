package com.example.demo.service.impl;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.constant.ErrorCode;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.UserVO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USER_EXISTS.getCode(), "用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), "用户不存在"));
        return convertToVO(user);
    }

    @Override
    @Transactional
    public UserVO updateUserInfo(Long userId, UserVO userVO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), "用户不存在"));
        if (userVO.getEmail() != null) user.setEmail(userVO.getEmail());
        if (userVO.getPhone() != null) user.setPhone(userVO.getPhone());
        if (userVO.getAvatar() != null) user.setAvatar(userVO.getAvatar());
        return convertToVO(userRepository.save(user));
    }

    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setAvatar(user.getAvatar());
        vo.setRole(user.getRole());
        vo.setCreatedAt(user.getCreatedAt());
        return vo;
    }
}