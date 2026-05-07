package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserInfo;
import com.example.demo.vo.UserDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("""
        SELECT
            u.id AS id,
            u.username AS username,
            u.email AS email,
            i.phone AS phone,
            i.real_name AS nickname,
            i.address AS address
        FROM sys_user u
        LEFT JOIN user_info i ON u.id = i.user_id
        WHERE u.id = #{userId}
        """)
    UserDetailVO getUserDetail(@Param("userId") Long userId);
}
