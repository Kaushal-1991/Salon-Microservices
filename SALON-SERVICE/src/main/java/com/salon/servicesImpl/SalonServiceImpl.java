package com.salon.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;
import com.salon.exception.ResourceNotFound;
import com.salon.mapper.SalonMapper;
import com.salon.model.Salon;
import com.salon.services.SalonRepository;
import com.salon.services.SalonService;

@Service
public class SalonServiceImpl implements SalonService {

	@Autowired
	private SalonRepository salonRepository;

	@Override
	public SalonResponseDto createSalon(SalonRequestDto salonRequestDto, UserDto userDto) {
		Salon salon = SalonMapper.mapToSalon(salonRequestDto, userDto);
		salonRepository.save(salon);
		return SalonMapper.mapToDto(salon, userDto);
	}

	@Override
	public SalonResponseDto updateSalon(SalonRequestDto salonRequestDto, String salonId, UserDto userDto) {
		Salon salon = salonRepository.findById(Long.valueOf(salonId))
				.orElseThrow(() -> new ResourceNotFound("Salon not found with id: " + salonId));

		if (!salon.getOwnerId().equals(userDto.getId())) {
			throw new ResourceNotFound("You are not authorized to update this salon");
		}

		salon.setName(salonRequestDto.getName());
		salon.setAddress(salonRequestDto.getAddress());
		salon.setCity(salonRequestDto.getCity());
		salon.setEmail(salonRequestDto.getEmail());
		salon.setImages(salonRequestDto.getImages());
		salon.setMobile(salonRequestDto.getMobile());
		salon.setOpenTime(salonRequestDto.getOpenTime());
		salon.setCloseTime(salonRequestDto.getCloseTime());
		salonRepository.save(salon);
		SalonResponseDto mappedToDto = SalonMapper.mapToDto(salon, userDto);
		return mappedToDto;

	}

	@Override
	public SalonResponseDto getSalon(String salonId, UserDto userDto) {
		Salon salon = salonRepository.findById(Long.valueOf(salonId))
				.orElseThrow(() -> new ResourceNotFound("Salon not found with id: " + salonId));
		return SalonMapper.mapToDto(salon, userDto);
	}

	@Override
	public List<SalonResponseDto> getSalons(UserDto userDto) {
		List<Salon> salons = salonRepository.findAll();
		return salons.stream().map(salon -> SalonMapper.mapToDto(salon, userDto)).collect(Collectors.toList());
	}

	@Override
	public List<SalonResponseDto> getSalonByOwnerId(String ownerId, UserDto userDto) {
		List<Salon> salon = salonRepository.findByOwnerId(Long.valueOf(ownerId));
		if(salon == null) {
			throw new ResourceNotFound("Salon owner not found with id: " + ownerId);
		}
		List<SalonResponseDto> mappedSalon = salon.stream().map(salons -> SalonMapper.mapToDto(salons, userDto)).collect(Collectors.toList());
		return mappedSalon;
	}

	@Override
	public List<SalonResponseDto> searchSalonByCity(String city, UserDto userDto) {
		List<Salon> listSalon = salonRepository.searchSalon(city);
		List<SalonResponseDto> searchedSalon = listSalon.stream().map(salon -> SalonMapper.mapToDto(salon, userDto)).collect(Collectors.toList());
		return searchedSalon;
	}
}
