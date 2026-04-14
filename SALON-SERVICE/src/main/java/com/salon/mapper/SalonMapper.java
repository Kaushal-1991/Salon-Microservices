package com.salon.mapper;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;
import com.salon.model.Salon;

public class SalonMapper {
    public static SalonResponseDto mapToDto(Salon salon,UserDto userDto) {
    	SalonResponseDto responseDto = new SalonResponseDto();
    	responseDto.setAddress(salon.getAddress());
    	responseDto.setCity(salon.getCity());
    	responseDto.setCloseTime(salon.getCloseTime());
    	responseDto.setEmail(salon.getEmail());
    	responseDto.setImages(salon.getImages());
    	responseDto.setMobile(salon.getMobile());
    	responseDto.setName(salon.getName());
    	responseDto.setOpenTime(salon.getOpenTime());
    	responseDto.setOwnerId(userDto.getId());
    	return responseDto;
    }
    
    public static Salon mapToSalon(SalonRequestDto salonDto) {
    	Salon salon = new Salon();
    	salon.setName(salonDto.getName());
    	salon.setAddress(salonDto.getAddress());
    	salon.setCity(salonDto.getCity());
    	salon.setEmail(salonDto.getEmail());
    	salon.setImages(salonDto.getImages());
    	salon.setMobile(salonDto.getMobile());
    	salon.setOwnerId(salonDto.getOwnerId());
    	salon.setOpenTime(salonDto.getOpenTime());
    	salon.setCloseTime(salonDto.getCloseTime());
    	return salon;
    }
}
