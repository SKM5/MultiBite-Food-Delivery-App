package com.multibite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multibite.model.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
	
	public OrderDetails findByOrderId(Integer orderId);	
	public OrderDetails save(String orderStatus);

}
