package com.example.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDetailVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
