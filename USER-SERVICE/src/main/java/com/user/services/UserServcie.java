package com.user.services;

import java.util.List;

import com.user.dto.UserRequestDto;
import com.user.dto.UserResponseDto;

public interface UserServcie {

	List<UserResponseDto> getUsers();

	UserResponseDto createUser(UserRequestDto user);

	UserResponseDto getUser(String userId);

	UserResponseDto updateUser(String userId, UserRequestDto user);

	String deleteUser(String userId);

}
