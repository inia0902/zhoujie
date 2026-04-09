package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 检查用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDTO.getUsername());
        User existingUser = userMapper.selectOne(wrapper);

        if (existingUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 2. 创建新用户
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        // 3. 插入数据库
        userMapper.insert(user);

        // 4. 使用 JWT 生成 Token
        String token = JwtUtil.generateToken(userDTO.getUsername());
        return Result.success(token);
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDTO.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 2. 验证密码
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 3. 使用 JWT 生成 Token
        String token = JwtUtil.generateToken(userDTO.getUsername());
        return Result.success("Bearer " + token);
    }

    @Override
    public Result<UserDTO> getUserById(Long id) {
        // 1. 根据 ID 查询用户
        User user = userMapper.selectById(id);

        // 2. 判断用户是否存在
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 转换为 DTO 返回（不返回密码）
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return Result.success(userDTO);
    }
}