package com.booking.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingSlotDto {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
