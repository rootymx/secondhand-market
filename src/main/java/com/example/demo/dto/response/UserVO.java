package com.example.demo.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String role;
    private LocalDateTime createdAt;
}
