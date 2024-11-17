package com.multibite.service;

import com.multibite.model.ActiveUser;
import com.multibite.model.DeliveryActivity;
import com.multibite.model.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMonitoringService {

	private final OrderService orderService;
	
	public AdminMonitoringService(OrderService orderService) {
        this.orderService = orderService;
    }	
    
	public String getOrderStatus(List<OrderDetails> orderDetailsList) {
        return orderService.getOrderStatus(orderDetailsList);
    }

	public ActiveUser getActiveUserCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public DeliveryActivity getDeliveryActivity() {
		// TODO Auto-generated method stub
		return null;
	}
}
