package com.example.demo.service;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.UserVO;
import com.example.demo.entity.User;

public interface UserService {
    User register(RegisterRequest request);
    UserVO getUserInfo(Long userId);
    UserVO updateUserInfo(Long userId, UserVO userVO);
}