package com.booking.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Long> serviceIds;
}
