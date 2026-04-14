package com.salon.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalonRequestDto {
	private String name;
	private List<String> images;
	private String address;
	private String mobile;
	private String email;
	private String city;
	private Long ownerId;
	private LocalTime openTime;
	private LocalTime closeTime;
}
