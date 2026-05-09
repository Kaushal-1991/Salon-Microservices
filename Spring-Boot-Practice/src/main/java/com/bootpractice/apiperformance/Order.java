package com.bootpractice.apiperformance;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders" , indexes = {
		@Index(name="idx_user_id" , columnList = "user_id"),
		@Index(name="idx_status" , columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	private Double amount;
	
	private String status;
	
	@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<OrderItem> orderItems = new ArrayList<>();
}
