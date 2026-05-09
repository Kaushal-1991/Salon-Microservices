package com.booking.mapper;

import com.booking.dtos.BookingDto;
import com.booking.model.Booking;

import lombok.Data;

@Data
public class BookingMapper {

	public static BookingDto toDto(Booking booking) {
		BookingDto bookingDto = new BookingDto();
		bookingDto.setId(booking.getId());
		bookingDto.setCustomerId(booking.getCustomerId());
		bookingDto.setSalonId(booking.getSalonId());
		bookingDto.setServiceIds(booking.getServiceIds());
		bookingDto.setStatus(booking.getStatus());
		bookingDto.setStartTime(booking.getStartTime());
		bookingDto.setEndTime(booking.getEndTime());
		return bookingDto;
	}
}
