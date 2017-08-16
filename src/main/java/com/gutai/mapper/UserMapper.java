package com.gutai.mapper;

import com.gutai.model.User;

import java.util.List;

public interface UserMapper {
	List<User> selectAllUser() throws Exception;
}
