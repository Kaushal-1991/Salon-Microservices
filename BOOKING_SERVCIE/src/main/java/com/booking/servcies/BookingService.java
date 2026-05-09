package com.booking.servcies;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.booking.dtos.BookingRequest;
import com.booking.dtos.SalonDto;
import com.booking.dtos.SalonReport;
import com.booking.dtos.ServiceDto;
import com.booking.dtos.UserDto;
import com.booking.enums.BookingStatus;
import com.booking.model.Booking;

public interface BookingService {

	Booking createBooking(BookingRequest bookingRequest,UserDto userDto
			,SalonDto salonRequestDto,Set<ServiceDto> serviceDtoSet) throws Exception;
	
	List<Booking> getBookingsByCustomer(Long customerId);
	List<Booking> getBookingsBySalon(Long salonId);
	Booking getBookingById(Long id);
	Booking updateBooking(Long bookingId, BookingStatus bookingStatus);
	List<Booking> getBookingByDate(LocalDate localDate, Long salonId);
	SalonReport getSalonReport(Long salonId);
}
