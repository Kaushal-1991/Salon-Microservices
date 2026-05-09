package com.payment.services;

import com.payment.dtos.BookingDto;
import com.payment.dtos.PaymentLinkResponse;
import com.payment.dtos.UserDto;
import com.payment.enums.PaymentMethod;
import com.payment.model.PaymentOrder;
import com.razorpay.PaymentLink;


public interface PaymentService {
	PaymentLinkResponse create(UserDto userDto, BookingDto bookingDto,PaymentMethod paymentMethod) throws Exception;
	PaymentOrder getPaymentOrder(Long id) throws Exception;
	PaymentOrder getPaymentOrderByPaymentId(String paymentId);
	PaymentLink createRazorPaymentLink(UserDto userDto,Long amount,Long orderId) throws Exception;
	String createStripePaymentLink(UserDto userDto,Long amount,Long orderId) throws Exception;
	Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkedId) throws Exception;
}
