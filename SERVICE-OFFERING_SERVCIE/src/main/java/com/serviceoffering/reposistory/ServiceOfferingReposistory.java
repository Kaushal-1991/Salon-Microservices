package com.serviceoffering.reposistory;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serviceoffering.model.ServiceOffering;

@Repository
public interface ServiceOfferingReposistory extends JpaRepository<ServiceOffering, Long>{

	Set<ServiceOffering> findBySalonId(Long salonId);

}
