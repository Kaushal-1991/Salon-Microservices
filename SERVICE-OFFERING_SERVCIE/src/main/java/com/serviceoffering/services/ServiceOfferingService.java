package com.serviceoffering.services;

import java.util.Set;

import com.serviceoffering.dto.CategoryDto;
import com.serviceoffering.dto.SalonDto;
import com.serviceoffering.dto.ServiceOfferingDto;
import com.serviceoffering.model.ServiceOffering;

public interface ServiceOfferingService {
	public ServiceOffering createServiceOffering(ServiceOfferingDto offeringDto,
			SalonDto salonDto,CategoryDto categoryDto);
	
	public ServiceOffering updateServiceOffering(Long id,ServiceOfferingDto offeringDto);
	
	public Set<ServiceOffering> getServiceOfferingBySalon(Long salonId,Long categoryId);
	
	public Set<ServiceOffering> getServiceOfferingByIds(Set<Long> id);
	
	public ServiceOffering getServiceById(Long id);
}
