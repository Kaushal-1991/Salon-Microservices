package com.salon.services;

import java.util.List;

import com.salon.dto.UserRequestDto;
import com.salon.dto.UserResponseDto;

public interface UserServcie {

	List<UserResponseDto> getUsers();

	UserResponseDto createUser(UserRequestDto user);

	UserResponseDto getUser(String userId);

	UserResponseDto updateUser(String userId, UserRequestDto user);

	String deleteUser(String userId);

}
