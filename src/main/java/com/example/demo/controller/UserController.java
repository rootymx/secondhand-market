package com.example.demo.controller;

import com.example.demo.common.result.Result;
import com.example.demo.dto.response.UserVO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        // 实际项目中根据 username 查询用户 ID
        return Result.success(userService.getUserInfo(1L));
    }

    @PutMapping("/me")
    public Result<UserVO> updateCurrentUser(@RequestBody UserVO userVO, Authentication auth) {
        return Result.success(userService.updateUserInfo(1L, userVO));
    }
}