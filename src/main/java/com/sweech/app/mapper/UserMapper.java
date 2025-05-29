package com.sweech.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sweech.app.model.User;

public interface UserMapper {
	User findByEmail(@Param("email") String email);

	User findById(@Param("id") Long id);

	void insert(User user);

	void update(User user);

	void delete(@Param("id") Long id);

	List<User> findAll();
}
