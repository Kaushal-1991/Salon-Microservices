package com.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalonReport {
	private Long salonId;
	private String salonName;
	private Double totalEarning;
	private Integer totalBooking;
	private Integer cancelBooking;
	private Double totalRefund;
}
