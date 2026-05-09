package com.booking.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dtos.BookingDto;
import com.booking.dtos.BookingRequest;
import com.booking.dtos.BookingSlotDto;
import com.booking.dtos.SalonDto;
import com.booking.dtos.SalonReport;
import com.booking.dtos.ServiceDto;
import com.booking.dtos.UserDto;
import com.booking.enums.BookingStatus;
import com.booking.mapper.BookingMapper;
import com.booking.model.Booking;
import com.booking.servcies.BookingService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequest bookingRequest,@RequestParam Long salonId) throws Exception{
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		
		SalonDto salonDto = new SalonDto();
		salonDto.setId(salonId);
		salonDto.setOpenTime(LocalTime.now());
		salonDto.setCloseTime(LocalTime.now().plusHours(12));
		
		Set<ServiceDto> servcieDto = new HashSet<>();
		ServiceDto service = new ServiceDto();
		service.setId(1L);
		service.setPrice(399);
		service.setDuration(45);
		service.setName("Hair cut for men");
		
		servcieDto.add(service);		
		Booking booking = bookingService.createBooking(bookingRequest, userDto, salonDto, servcieDto);
		return new ResponseEntity<>(BookingMapper.toDto(booking),HttpStatus.CREATED);
	}
	
	@GetMapping("{bookingId}")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId){
		Booking booking = bookingService.getBookingById(bookingId);
		return ResponseEntity.ok(BookingMapper.toDto(booking));
	}
	
	@GetMapping("/salon")
	public ResponseEntity<Set<BookingDto>> getBookingsByCustomer(){
		List<Booking> booking = bookingService.getBookingsByCustomer(1L);
		return ResponseEntity.ok(getBookingDtos(booking));
	}
	
	@PutMapping("{bookingId}/status")
	public ResponseEntity<BookingDto> updateBooking(@PathVariable Long bookingId,@RequestParam BookingStatus status){
		Booking updateBooking = bookingService.updateBooking(bookingId, status);
		return ResponseEntity.ok(BookingMapper.toDto(updateBooking));
	}
	
	@GetMapping("/slots/slon/{salonId}/date/{date}")
	public ResponseEntity<List<BookingSlotDto>> getBookingSlot(@PathVariable Long salonId,@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date){
		List<Booking> bookingByDate = bookingService.getBookingByDate(date, salonId);
		
		List<BookingSlotDto> newDateSlot = bookingByDate.stream().map(booking -> {
			BookingSlotDto dto = new BookingSlotDto();
			dto.setStartDate(booking.getStartTime());
			dto.setEndDate(booking.getEndTime());
			return dto;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(newDateSlot);
	}
	
	@GetMapping("/report")
	public ResponseEntity<SalonReport> getSalonReport(){
		SalonReport report = bookingService.getSalonReport(2L);
		return ResponseEntity.ok(report);
	}
	
	private Set<BookingDto> getBookingDtos(List<Booking> bookings){
		return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet());
	}	
	
}
