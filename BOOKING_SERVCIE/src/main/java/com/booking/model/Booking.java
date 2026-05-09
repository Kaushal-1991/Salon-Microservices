package com.booking.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.booking.enums.BookingStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long salonId;
	private Long customerId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	@ElementCollection
	private Set<Long> serviceIds;
	private BookingStatus status = BookingStatus.PENDING;
	private int totalPrize;
}
