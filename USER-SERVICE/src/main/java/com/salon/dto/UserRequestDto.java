package com.salon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
	@NotBlank(message = "Username  is required")
	private String fullName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be right")
	private String email;
	private String phone;
	private String role;
	
	@NotBlank(message = "Password is required")
	private String password;
}
