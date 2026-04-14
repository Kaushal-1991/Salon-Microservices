package com.salon.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;
import com.salon.mapper.SalonMapper;
import com.salon.model.Salon;
import com.salon.services.SalonRepository;
import com.salon.services.SalonService;

@Service
public class SalonServiceImpl implements SalonService{

	@Autowired
	private SalonRepository salonRepository;
	
	@Override
	public SalonResponseDto createSalon(SalonRequestDto salonRequestDto, UserDto userDto) {
		Salon salon = SalonMapper.mapToSalon(salonRequestDto);
		salonRepository.save(salon);
		return SalonMapper.mapToDto(salon, userDto);
	}

	@Override
	public SalonResponseDto updateSalon(SalonRequestDto salonRequestDto, String salonId,UserDto userDto) {
		Salon salon = salonRepository.findById(Long.valueOf(salonId)).orElse(null);
		if (salon != null && salonRequestDto.getOwnerId().equals(userDto.getId())) {			
			Salon mappedSalon = SalonMapper.mapToSalon(salonRequestDto);
			salonRepository.save(mappedSalon);
			SalonResponseDto mappedToDto = SalonMapper.mapToDto(mappedSalon, userDto);
			return mappedToDto;
		}
		return null;
	}

	@Override
	public SalonResponseDto getSalon(SalonRequestDto salonRequestDto, String salonId, UserDto userDto) {
		Salon salon = salonRepository.findById(Long.valueOf(salonId)).orElse(null);
		return SalonMapper.mapToDto(salon, userDto);
	}

	@Override
	public List<SalonResponseDto> getSalons(UserDto userDto) {
		List<Salon> salons = salonRepository.findAll();
		return salons.stream()
				     .map(salon -> SalonMapper.mapToDto(salon, userDto))
				     .collect(Collectors.toList());
	}

	@Override
	public SalonResponseDto getSalonByOwnerId(Long id,UserDto userDto) {
		Salon salon = salonRepository.findByOwnerId(id);
		SalonResponseDto mappedData = SalonMapper.mapToDto(salon, userDto);
		return mappedData;
	}

	@Override
	public List<SalonResponseDto> searchSalonByCity(String city, UserDto userDto) {
		List<Salon> listSalon = salonRepository.searchSalon(city);
		return null;
	}
}
