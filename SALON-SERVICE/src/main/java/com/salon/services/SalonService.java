package com.salon.services;

import java.util.List;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;

public interface SalonService {

	SalonResponseDto createSalon(SalonRequestDto salonRequestDto, UserDto userDto);

	SalonResponseDto updateSalon(SalonRequestDto salonRequestDto, String salonId,UserDto userDto);

	SalonResponseDto getSalon(SalonRequestDto salonRequestDto, String salonId, UserDto userDto);

	List<SalonResponseDto> getSalons(UserDto userDto);

	SalonResponseDto getSalonByOwnerId(Long id,UserDto userDto);

	List<SalonResponseDto> searchSalonByCity(String city, UserDto userDto);

}
