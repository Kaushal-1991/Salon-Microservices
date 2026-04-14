package com.salon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salon.dto.SalonRequestDto;
import com.salon.dto.SalonResponseDto;
import com.salon.dto.UserDto;
import com.salon.services.SalonService;

@RestController
@RequestMapping("/api/salon")
public class SalonController {
	
	@Autowired
	private SalonService salonService;
	
	@PostMapping
	public ResponseEntity<SalonResponseDto> createSalon(@RequestBody SalonRequestDto salonRequestDto) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		SalonResponseDto salonResponseDto= salonService.createSalon(salonRequestDto,userDto);
		return new ResponseEntity<>(salonResponseDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{salonId}")
	public ResponseEntity<SalonResponseDto> updateSalon(@PathVariable String salonId,@RequestBody SalonRequestDto salonRequestDto) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		SalonResponseDto salonResponseDto= salonService.updateSalon(salonRequestDto,salonId,userDto);
		return new ResponseEntity<>(salonResponseDto,HttpStatus.OK);
	}
	
	@GetMapping("/{salonId}")
	public ResponseEntity<SalonResponseDto> getSalon(@PathVariable String salonId,@RequestBody SalonRequestDto salonRequestDto) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		SalonResponseDto salonResponseDto= salonService.getSalon(salonRequestDto,salonId,userDto);
		return new ResponseEntity<>(salonResponseDto,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<SalonResponseDto>> getSalons() {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		List<SalonResponseDto> salonResponseDto= salonService.getSalons(userDto);
		return ResponseEntity.ok(salonResponseDto);
	}
	
	@GetMapping("/{owner}")
	public ResponseEntity<SalonResponseDto> getSalonByOwner(@PathVariable String salonId) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		SalonResponseDto salonResponseDto= salonService.getSalonByOwnerId(userDto.getId(),userDto);
		return ResponseEntity.ok(salonResponseDto);
	}
	
	@GetMapping("/{search}")
	public ResponseEntity<List<SalonResponseDto>> searchSalons(@RequestParam("city") String city) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		List<SalonResponseDto> salonResponseDto= salonService.searchSalonByCity(city,userDto);
		return ResponseEntity.ok(salonResponseDto);
	}
	
	
}
