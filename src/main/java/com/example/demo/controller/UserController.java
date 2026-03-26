package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 根据ID查询用户
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        return Result.success(user);
    }

    // 新增用户
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        return Result.success("用户创建成功：" + user.getName());
    }

    // 登录接口
    @PostMapping("/login")
    public Result<String> login() {
        return Result.success("登录成功");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        return Result.success("用户删除成功，ID：" + id);
    }
}