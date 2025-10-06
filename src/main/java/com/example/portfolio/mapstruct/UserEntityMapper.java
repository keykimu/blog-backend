package com.example.portfolio.mapstruct;

import com.example.portfolio.entity.User;
import com.example.portfolio.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    List<UserResponse> toResponseList(List<User> users);
}