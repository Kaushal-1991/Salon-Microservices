package com.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
	private Long id;
	private String name;
	private int price;
	private int duration;
	private Long salonId;
	private Long categoryId;
	private String image;
}
