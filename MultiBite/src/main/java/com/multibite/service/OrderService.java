package com.multibite.service;

import java.util.List;

import com.multibite.exception.CustomerException;
import com.multibite.exception.FoodCartException;
import com.multibite.exception.LoginException;
import com.multibite.exception.OrderDetailsException;
import com.multibite.model.OrderDetails;

public interface OrderService {

	public OrderDetails addOrder(String key, Integer customerId)
			throws CustomerException, FoodCartException, LoginException;

	public OrderDetails updateOrder(String key, Integer orderId, Integer customerId)
			throws OrderDetailsException, CustomerException, FoodCartException, LoginException;

	public OrderDetails removeOrder(String key, Integer orderId) throws OrderDetailsException, LoginException;

	public OrderDetails viewOrder(String key, Integer orderId) throws OrderDetailsException, LoginException;

	public String rescheduleOrder(Integer orderId, String newDeliveryTime) throws OrderDetailsException;
	
	public String getOrderStatus(List<OrderDetails> orderDetailsList);

}
