package com.salon.servciesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.dto.UserRequestDto;
import com.salon.dto.UserResponseDto;
import com.salon.exception.UserNotFoundException;
import com.salon.model.User;
import com.salon.repository.UserRepository;
import com.salon.services.UserServcie;

@Service
public class UserServcieImpl implements UserServcie {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserResponseDto> getUsers() {
		return userRepository.findAll()
				             .stream()
				             .map(this::mapUserToDto)
				             .collect(Collectors.toList());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userDto) {
		User mappedUser = mapDtoToUser(userDto);
		userRepository.save(mappedUser);
		return mapUserToDto(mappedUser);
	}

	@Override
	public UserResponseDto getUser(String userId) {
		User useropt = userRepository.findById(Long.valueOf(userId))
				                     .orElseThrow(() -> new UserNotFoundException("User Not Found With this " + userId));
		UserResponseDto mappedUser= mapUserToDto(useropt);
		return mappedUser;
	}

	@Override
	public UserResponseDto updateUser(String userId, UserRequestDto user) {
		
		User useropt = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User Not Found With this " + userId));
		useropt.setFullName(user.getFullName());
		useropt.setEmail(user.getEmail());
		useropt.setPhone(user.getPhone());
		useropt.setRole(user.getRole());
		userRepository.save(useropt);
		return mapUserToDto(useropt);
	}

	@Override
	public String deleteUser(String userId) {
		User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User Not Found With this " + userId));
		userRepository.delete(user);
		return "User deleted successfully !!!";
	}

	private UserResponseDto mapUserToDto(User userRequestDto) {
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setId(userRequestDto.getId());
		userResponseDto.setFullName(userRequestDto.getFullName());
		userResponseDto.setEmail(userRequestDto.getEmail());
		//userResponseDto.setPassword(userRequestDto.getPassword());
		userResponseDto.setPhone(userRequestDto.getPhone());
		userResponseDto.setRole(userRequestDto.getRole());
		return userResponseDto;
	}

	private User mapDtoToUser(UserRequestDto userDto) {
		User user = new User();
		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		user.setPhone(userDto.getPhone());
		user.setRole(userDto.getRole());
		user.setUsername(user.getUsername());
		user.setPassword(userDto.getPassword());
		return user;
	}

}
