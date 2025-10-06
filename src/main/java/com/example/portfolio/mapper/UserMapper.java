package com.example.portfolio.mapper;

import com.example.portfolio.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findByUsername(@Param("username") String username);
    void updateLastLogin(@Param("id") Long id, @Param("time") LocalDateTime time);
}

