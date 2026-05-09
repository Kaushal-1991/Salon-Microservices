package com.booking.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import com.booking.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
	private Long id;
	private Long salonId;
	private Long customerId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Long> serviceIds;
	private BookingStatus status = BookingStatus.PENDING;
}
