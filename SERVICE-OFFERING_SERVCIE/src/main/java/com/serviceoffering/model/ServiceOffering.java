package com.serviceoffering.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="service-offering")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOffering {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int price;
	
	@Column(nullable = false)
	private int duration;
	
	@Column(nullable = false)
	private Long salonId;
	
	@Column(nullable = false)
	private Long categoryId;
	
	private String image;
}
/*
{
	"name" : "XYZ",
	"price" :50,
	"duration" : 5
	image : ""
}
 */