package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dtos.BookingDto;
import com.payment.dtos.PaymentLinkResponse;
import com.payment.dtos.UserDto;
import com.payment.enums.PaymentMethod;
import com.payment.model.PaymentOrder;
import com.payment.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/create")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDto bookingDto,
			                                                     @RequestParam PaymentMethod paymentMethod) throws Exception{
		UserDto userDto = new UserDto();
		userDto.setEmail("rajkaushal02@gmail.com");
		userDto.setFullName("Kaushal Raj Singh");
		userDto.setId(1L);
		
		PaymentLinkResponse paymentLinkResponse = paymentService.create(userDto, bookingDto, paymentMethod);
		return ResponseEntity.ok(paymentLinkResponse);
	}
	
	@GetMapping("/paymentOrderId")
	public ResponseEntity<PaymentOrder> getPymentOrder(@PathVariable Long paymentOrderId) throws Exception{
		PaymentOrder paymentOrder = paymentService.getPaymentOrder(paymentOrderId);
		return ResponseEntity.ok(paymentOrder);
	}
	
	@PatchMapping("/proceed")
	public ResponseEntity<Boolean> proceedPayment(@RequestParam String paymentId,@RequestParam String paymentLinkId) throws Exception{
		PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);
		Boolean resOrder = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);
		return ResponseEntity.ok(resOrder);
	}
}
