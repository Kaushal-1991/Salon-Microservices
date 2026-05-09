package com.booking.servciesImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dtos.BookingRequest;
import com.booking.dtos.SalonDto;
import com.booking.dtos.SalonReport;
import com.booking.dtos.ServiceDto;
import com.booking.dtos.UserDto;
import com.booking.enums.BookingStatus;
import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.TimeSlotNotAvailableException;
import com.booking.model.Booking;
import com.booking.repository.BookingRepositroy;
import com.booking.servcies.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	private BookingRepositroy bookingRepositroy;

	@Override
	public Booking createBooking(BookingRequest bookingRequest, UserDto userDto, SalonDto salonRequestDto,
			Set<ServiceDto> serviceDtoSet) {

		int totalDuration = serviceDtoSet.stream().mapToInt(ServiceDto::getDuration).sum();

		LocalDateTime bookingStartTime = bookingRequest.getStartTime();
		LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

		Boolean isTimeSlot = isTimeSlotAvailable(salonRequestDto, bookingStartTime, bookingEndTime);

		int totalprice = serviceDtoSet.stream().mapToInt(ServiceDto::getPrice).sum();

		Set<Long> idsList = serviceDtoSet.stream().map(ServiceDto::getId).collect(Collectors.toSet());

		Booking booking = new Booking();
		booking.setCustomerId(userDto.getId());
		booking.setSalonId(salonRequestDto.getId());
		booking.setStatus(BookingStatus.PENDING);
		booking.setServiceIds(idsList);
		booking.setStartTime(bookingStartTime);
		booking.setEndTime(bookingEndTime);
		booking.setTotalPrize(totalprice);

		return bookingRepositroy.save(booking);
	}

	public Boolean isTimeSlotAvailable(SalonDto salonDto, LocalDateTime bookingStartTime,
			LocalDateTime bookingEndTime) {
		if (bookingStartTime == null || bookingEndTime == null) {
			throw new TimeSlotNotAvailableException("Start time and End time must not be null");
		}

		if (!bookingStartTime.isBefore(bookingEndTime)) {
			throw new TimeSlotNotAvailableException("Start time must be before End time");
		}

		List<Booking> existingBookings = getBookingsBySalon(salonDto.getId());
		LocalDateTime salonOpenTime = salonDto.getOpenTime().atDate(bookingStartTime.toLocalDate());
		LocalDateTime salonCloseTime = salonDto.getCloseTime().atDate(bookingStartTime.toLocalDate());

		if (bookingStartTime.isBefore(salonOpenTime)) {
			throw new TimeSlotNotAvailableException("Booking time must be within salon working hours");
		}

		for (Booking booking : existingBookings) {
			LocalDateTime existingStart = booking.getStartTime();
			LocalDateTime existingEnd = booking.getEndTime();
			if (bookingStartTime.isBefore(existingEnd) && bookingEndTime.isAfter(existingStart)) {

				throw new TimeSlotNotAvailableException("Slot not available: overlaps with existing booking from "
						+ existingStart + " to " + existingEnd);
			}
		}

		return true;
	}

	@Override
	public List<Booking> getBookingsByCustomer(Long customerId) {
		return bookingRepositroy.findByCustomerId(customerId);
	}

	@Override
	public List<Booking> getBookingsBySalon(Long salonId) {
		return bookingRepositroy.findBySalonId(salonId);
	}

	@Override
	public Booking getBookingById(Long id) {
		return bookingRepositroy.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking id is not exists"));
	}

	@Override
	public Booking updateBooking(Long bookingId, BookingStatus bookingStatus) {
		Booking existingBooking = getBookingById(bookingId);
		existingBooking.setStatus(bookingStatus);
		return bookingRepositroy.save(existingBooking);
	}

	@Override
	public List<Booking> getBookingByDate(LocalDate date, Long salonId) {
		List<Booking> allBooking = getBookingsBySalon(salonId);
		if (date == null) {
			return allBooking;
		}

		return allBooking.stream().filter(
				booking -> isBookingTime(booking.getStartTime(), date) || isBookingTime(booking.getEndTime(), date))
				.collect(Collectors.toList());
	}

	private Boolean isBookingTime(LocalDateTime dateTime, LocalDate date) {
		return dateTime.toLocalDate().isEqual(date);
	}

	@Override
	public SalonReport getSalonReport(Long salonId) {
		List<Booking> booking = getBookingsBySalon(salonId);
		
		Double totalEarnings = booking.stream()
				                      .mapToDouble(Booking::getTotalPrize)
				                      .sum();
		
		Integer totalBooking = booking.size();
		
		List<Booking> canceledBooking = booking.stream()
				                               .filter(bookings -> bookings.getStatus().equals(BookingStatus.CANCELLED))
				                               .collect(Collectors.toList());
		
		Double refundBooking = canceledBooking.stream().mapToDouble(Booking::getTotalPrize).sum();
		
		SalonReport salonReport = new SalonReport();
		salonReport.setCancelBooking(canceledBooking.size());
		salonReport.setSalonId(salonId);
		salonReport.setTotalBooking(totalBooking);
		salonReport.setTotalEarning(totalEarnings);
		salonReport.setTotalRefund(refundBooking);
		
		return salonReport;
	}

}
