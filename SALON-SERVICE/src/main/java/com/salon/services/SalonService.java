package com.salon.services;

import java.util.List;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;

public interface SalonService {

	SalonResponseDto createSalon(SalonRequestDto salonRequestDto, UserDto userDto);

	SalonResponseDto updateSalon(SalonRequestDto salonRequestDto, String salonId,UserDto userDto);

	SalonResponseDto getSalon(String salonId, UserDto userDto);

	List<SalonResponseDto> getSalons(UserDto userDto);

	List<SalonResponseDto> getSalonByOwnerId(String ownerId,UserDto userDto);

	List<SalonResponseDto> searchSalonByCity(String city, UserDto userDto);

}
