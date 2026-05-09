package com.serviceoffering.servicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.serviceoffering.dto.CategoryDto;
import com.serviceoffering.dto.SalonDto;
import com.serviceoffering.dto.ServiceOfferingDto;
import com.serviceoffering.model.ServiceOffering;
import com.serviceoffering.reposistory.ServiceOfferingReposistory;
import com.serviceoffering.services.ServiceOfferingService;

@Repository
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

	@Autowired
	private ServiceOfferingReposistory offeringReposistory;

	@Override
	public ServiceOffering createServiceOffering(ServiceOfferingDto offeringDto, SalonDto salonDto,
			CategoryDto categoryDto) {
		ServiceOffering newOffering = new ServiceOffering();
		newOffering.setName(offeringDto.getName());
		newOffering.setPrice(offeringDto.getPrice());
		newOffering.setImage(offeringDto.getImage());
		newOffering.setDuration(offeringDto.getDuration());
		newOffering.setCategoryId(categoryDto.getId());
		newOffering.setSalonId(salonDto.getId());
		return offeringReposistory.save(newOffering);
	}

	@Override
	public ServiceOffering updateServiceOffering(Long id, ServiceOfferingDto offeringDto) {
		ServiceOffering newServiceOffering = offeringReposistory.findById(id).orElseGet(null);
		if (newServiceOffering == null) {
			try {
				throw new Exception("Resource not found !!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		newServiceOffering.setDuration(offeringDto.getDuration());
		newServiceOffering.setName(offeringDto.getName());
		newServiceOffering.setPrice(offeringDto.getPrice());
		newServiceOffering.setImage(offeringDto.getImage());
		return newServiceOffering;
	}

	@Override
	public Set<ServiceOffering> getServiceOfferingBySalon(Long salonId, Long categoryId) {
		Set<ServiceOffering> serviceOfferings = offeringReposistory.findBySalonId(salonId);
		if (categoryId != null) {
			serviceOfferings = serviceOfferings.stream()
					.filter(service -> service.getCategoryId() != null && service.getCategoryId() == categoryId)
					.collect(Collectors.toSet());
		}
		return serviceOfferings;
	}

	@Override
	public Set<ServiceOffering> getServiceOfferingByIds(Set<Long> id) {
		List<ServiceOffering> service = offeringReposistory.findAllById(id);
		return new HashSet<>(service);
	}

	@Override
	public ServiceOffering getServiceById(Long id) {
		ServiceOffering serviceOffering = offeringReposistory.findById(id).orElseGet(null);
		if (serviceOffering == null) {
			try {
				throw new Exception("Resource not found !!!" + id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serviceOffering;
	}
}
