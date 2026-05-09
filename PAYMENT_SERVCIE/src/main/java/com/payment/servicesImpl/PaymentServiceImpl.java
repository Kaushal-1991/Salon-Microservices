package com.payment.servicesImpl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.dtos.BookingDto;
import com.payment.dtos.PaymentLinkResponse;
import com.payment.dtos.UserDto;
import com.payment.enums.PaymentMethod;
import com.payment.enums.PaymentOrderStatus;
import com.payment.model.PaymentOrder;
import com.payment.reposistory.PaymentReosistory;
import com.payment.services.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	
	@Autowired
	private PaymentReosistory paymentReosistory;
	
	@Value("${razorpay.api.key}")
	private String RAZORPAY_API_KEY;
	
	@Value("${razorpay.secret.key}")
	private String RAZORPAY_SECRET_KEY;
	
	@Value("${stripe.secret.key}")
	private String STRIPE_SECRET_KEY;

	@Override
	public PaymentLinkResponse create(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethodStatus) throws RazorpayException, StripeException {
		
		Long amount =(long) bookingDto.getTotalPrice();
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setBookingId(bookingDto.getId());
		paymentOrder.setMethodStatus(paymentMethodStatus);
		paymentOrder.setSalonId(bookingDto.getSalonId());
		PaymentOrder savedOrder= paymentReosistory.save(paymentOrder);
		
		PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
		
		if(PaymentMethod.RAZORPAY.equals(paymentMethodStatus)) {
			PaymentLink payment = createRazorPaymentLink(userDto,
					savedOrder.getAmount(), 
					savedOrder.getId());
			
			String paymentUrl = payment.get("Short_Url");
			String paymentId = payment.get("id");
			
			paymentLinkResponse.setPayment_link_id(paymentId);
			paymentLinkResponse.setPayment_link_url(paymentUrl);
			
			savedOrder.setPaymentLinkId(paymentId);
			
			paymentReosistory.save(savedOrder);		
		}else {
			String paymentUrl = createStripePaymentLink(userDto,
					savedOrder.getAmount(), 
					savedOrder.getId());
			
			paymentLinkResponse.setPayment_link_url(paymentUrl);
		}
		
		return paymentLinkResponse;
	}

	@Override
	public PaymentLink createRazorPaymentLink(UserDto userDto, Long Amount, Long orderId) throws RazorpayException {
		
		Long amount = Amount * 100;
		
		RazorpayClient  razorpayClient = new RazorpayClient(RAZORPAY_API_KEY, RAZORPAY_SECRET_KEY);

		JSONObject paymentLinkRequest = new JSONObject();
		paymentLinkRequest.put("amount", amount);
		paymentLinkRequest.put("currency", "INR");
		
		JSONObject customer = new JSONObject();
		customer.put("name", userDto.getFullName());
		customer.put("email", userDto.getEmail());
		
		paymentLinkRequest.put("customer", customer);
		
		JSONObject notify = new JSONObject();
		notify.put("emial", true);
		
		paymentLinkRequest.put("notify", notify);
		paymentLinkRequest.put("reminder_enable", true);
		paymentLinkRequest.put("callback_uri", "http://localhost:3000/payment-success"+orderId);
		paymentLinkRequest.put("callback_method", "get");
	
		return razorpayClient.paymentLink.create(paymentLinkRequest);
	}

	@Override
	public String createStripePaymentLink(UserDto userDto, Long amount, Long orderId) throws StripeException {
		Stripe.apiKey = STRIPE_SECRET_KEY;
		
		SessionCreateParams params = SessionCreateParams.builder()
		        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
		        .setMode(SessionCreateParams.Mode.PAYMENT)
		        .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
		        .setCancelUrl("http://localhost:3000/payment/cancel")
		        .addLineItem(SessionCreateParams.LineItem.builder()
		                        .setQuantity(1L)
		                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
		                                        .setCurrency("usd")
		                                        .setUnitAmount(amount * 100)
		                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
		                                                        .setName("Salon Appointment").build()       
		                                        ).build()       
		                        ).build()
		        ).build();
		Session session = Session.create(params);
		
		return session.getUrl();
	}

	@Override
	public PaymentOrder getPaymentOrder(Long id) throws Exception{
		PaymentOrder paymentOrder = paymentReosistory.findById(id).orElse(null); 
		if(paymentOrder == null) {
			throw new Exception("Order not found");
		}
		return paymentOrder;
	}

	@Override
	public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
		return paymentReosistory.findByPaymentLinkId(paymentId);
	}

	@Override
	public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId,String paymentLinkedId) throws RazorpayException {

        if(paymentOrder.getOrderStatus().equals(PaymentOrderStatus.PENDING)) {
        	if(paymentOrder.getMethodStatus().equals(PaymentMethod.RAZORPAY)) {
        		RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_API_KEY,RAZORPAY_SECRET_KEY);
        		Payment payment = razorpayClient.payments.fetch(paymentLinkedId);
        		Integer amount = payment.get("amount");
        		String status = payment.get("status");
        		
        		if(status.equals("captureId")) {
        			paymentOrder.setOrderStatus(PaymentOrderStatus.SUCCESS);
        			paymentReosistory.save(paymentOrder);
        			return true;
        		}
        		return false;
        	}else {
    			paymentOrder.setOrderStatus(PaymentOrderStatus.SUCCESS);
    			paymentReosistory.save(paymentOrder);
    			return true;
    		}
        }
		return false;
	}

}
