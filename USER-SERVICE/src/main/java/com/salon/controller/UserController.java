package com.salon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salon.dto.UserRequestDto;
import com.salon.dto.UserResponseDto;
import com.salon.services.UserServcie;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServcie userServcie;

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getUsers() {
		return ResponseEntity.ok(userServcie.getUsers());
	}

	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto user) {
		UserResponseDto userResponseDto = userServcie.createUser(user);
		return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId) {
		return ResponseEntity.ok(userServcie.getUser(userId));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable String userId, @RequestBody UserRequestDto user) throws Exception {
		return ResponseEntity.ok(userServcie.updateUser(userId, user));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		return ResponseEntity.ok(userServcie.deleteUser(userId));
	}

}
