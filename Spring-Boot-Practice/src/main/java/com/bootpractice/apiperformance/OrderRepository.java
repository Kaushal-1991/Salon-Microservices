package com.bootpractice.apiperformance;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ DTO Projection (FAST)
    @Query("SELECT new com.bootpractice.apiperformance.OrderResponse(o.id, o.amount, o.status) FROM Order o")
    Page<OrderResponse> findOrderSummary(Pageable pageable);

    // ✅ JOIN FETCH (N+1 fix)
    @Query("SELECT o FROM Order o JOIN FETCH o.items")
    List<Order> findAllWithItems();
}
