package com.bootpractice.apiperformance;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	private Long userId;
	private Double amount;
	private String status;
	private List<OrderItemRequest> orderItems = new ArrayList<>();
}
