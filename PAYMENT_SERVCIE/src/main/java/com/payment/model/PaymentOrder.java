package com.payment.model;

import com.payment.enums.PaymentMethod;
import com.payment.enums.PaymentOrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payment_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long amount;
	
	@Column(nullable = false)
	private PaymentOrderStatus orderStatus = PaymentOrderStatus.PENDING;
	
	private PaymentMethod methodStatus;
	
	@Column(nullable = false)
	private String paymentLinkId;
	
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long bookingId;
	
	@Column(nullable = false)
	private Long salonId;
	
}
