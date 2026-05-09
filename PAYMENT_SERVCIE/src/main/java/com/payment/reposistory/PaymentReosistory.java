package com.payment.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.model.PaymentOrder;

@Repository
public interface PaymentReosistory extends JpaRepository<PaymentOrder, Long> {

	PaymentOrder findByPaymentLinkId(String paymentId);
}
