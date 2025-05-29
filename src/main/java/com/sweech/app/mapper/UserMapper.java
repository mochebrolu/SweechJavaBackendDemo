package com.sweech.app.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sweech.app.dto.UserDto;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO members (email, password, username, registration_time) VALUES (#{email}, #{password}, #{username}, #{registrationTime})")
    void insert(UserDto userDto);

    @Select("SELECT * FROM members WHERE email = #{email}")
    UserDto findByEmail(String email);
}
