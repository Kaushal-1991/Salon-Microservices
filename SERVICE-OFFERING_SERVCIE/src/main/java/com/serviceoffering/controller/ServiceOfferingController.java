package com.serviceoffering.controller;

import java.util.Set;

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

import com.serviceoffering.dto.CategoryDto;
import com.serviceoffering.dto.SalonDto;
import com.serviceoffering.dto.ServiceOfferingDto;
import com.serviceoffering.model.ServiceOffering;
import com.serviceoffering.services.ServiceOfferingService;

@RestController
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

	@Autowired
	private ServiceOfferingService offeringService;
	
	@PostMapping
	public ResponseEntity<ServiceOffering> create(@RequestBody ServiceOfferingDto offeringDto){
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(1L);
		SalonDto salonDto = new SalonDto();
		salonDto.setId(1L);
		ServiceOffering offering = offeringService.createServiceOffering(offeringDto, salonDto, categoryDto);
		return new ResponseEntity<ServiceOffering>(offering,HttpStatus.CREATED);
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ServiceOffering> update(@PathVariable Long id,
			@RequestBody ServiceOfferingDto offeringDto){
		ServiceOffering updateServiceOffering = offeringService.updateServiceOffering(id, offeringDto);
		return ResponseEntity.ok(updateServiceOffering);
	}
	
	@GetMapping("/salon/{salonId}")
	public ResponseEntity<Set<ServiceOffering>> getServiceOfferBySalon(@PathVariable Long salonId,
			@RequestParam(required = false) Long categoryId){
		Set<ServiceOffering> serviceOfferingBySalon = offeringService.getServiceOfferingBySalon(salonId, categoryId);
		return ResponseEntity.ok(serviceOfferingBySalon);
	}
	
	@GetMapping("/list/{ids}")
	public ResponseEntity<Set<ServiceOffering>> getServiceOfferByIds(@PathVariable  Set<Long> ids){
		Set<ServiceOffering> serviceOfferingBySalon = offeringService.getServiceOfferingByIds(ids);
		return ResponseEntity.ok(serviceOfferingBySalon);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ServiceOffering> getServiceOfferById(@PathVariable Long id){
		ServiceOffering serviceOfferingBySalon = offeringService.getServiceById(id);
		return ResponseEntity.ok(serviceOfferingBySalon);
	}
}
