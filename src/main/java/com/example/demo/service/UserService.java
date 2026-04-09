package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.dto.UserDTO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<UserDTO> getUserById(Long id);

    // 新增：分页查询用户
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}