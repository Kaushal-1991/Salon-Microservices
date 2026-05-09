package com.serviceoffering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceOfferingServcieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOfferingServcieApplication.class, args);
	}

}
