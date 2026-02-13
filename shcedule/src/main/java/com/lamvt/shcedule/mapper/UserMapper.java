package com.lamvt.shcedule.mapper;

import com.lamvt.shcedule.dto.UserDto;
import com.lamvt.shcedule.entity.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}