package com.multibite.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multibite.exception.CustomerException;
import com.multibite.exception.FoodCartException;
import com.multibite.exception.ItemException;
import com.multibite.exception.LoginException;
import com.multibite.exception.OrderDetailsException;
import com.multibite.model.CurrentUserSession;
import com.multibite.model.Customer;
import com.multibite.model.FoodCart;
import com.multibite.model.Item;
import com.multibite.model.OrderDetails;
import com.multibite.model.Restaurant;
import com.multibite.repository.CurrentUserSessionRepo;
import com.multibite.repository.CustomerRepo;
import com.multibite.repository.OrderDetailsRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailsRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CurrentUserSessionRepo currSession;

	@Override
	public OrderDetails addOrder(String key, Integer customerId) throws CustomerException, FoodCartException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isEmpty())
			throw new CustomerException("customer does not exist..!");
		Customer customer = opt.get();
		FoodCart foodCart = customer.getCart();
		List<Item> itemList = foodCart.getItemList();
		if (itemList.isEmpty())
			throw new FoodCartException("cart is empty..!");
		List<OrderDetails> orderDetailsList = orderRepo.findAll();
		boolean flag = true;
		OrderDetails orderDetails = null;
		for (int i = 0; i < orderDetailsList.size(); i++) {
			OrderDetails exOrderDetails = orderDetailsList.get(i);
			if (exOrderDetails.getFoodCart().getCartId() == foodCart.getCartId()
					&& exOrderDetails.getOrderStatus().equals("Pending")) {
				exOrderDetails.setFoodCart(foodCart);
				orderDetails = exOrderDetails;
				flag = false;
			}
		}

		if (flag) {
			orderDetails = new OrderDetails();
			orderDetails.setFoodCart(foodCart);
			orderDetails.setOrderDate(LocalDateTime.now());
			orderDetails.setOrderStatus("Pending");
		}
		orderRepo.save(orderDetails);
		return orderDetails;

	}

	@Override
	public OrderDetails updateOrder(String key, Integer orderId, Integer customerId)
			throws OrderDetailsException, CustomerException, FoodCartException, LoginException {
		
		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
		Optional<OrderDetails> opt1 = orderRepo.findById(orderId);
		if (opt1.isPresent()) {
			Optional<Customer> opt = customerRepo.findById(customerId);
			if (opt.isEmpty())
				throw new CustomerException("customer does not exist..!");
			Customer customer = opt.get();
			FoodCart foodCart = customer.getCart();
			List<Item> itemList = foodCart.getItemList();
			if (itemList.isEmpty())
				throw new FoodCartException("cart is empty..!");
			List<OrderDetails> orderDetailsList = orderRepo.findAll();
			boolean flag = true;
			OrderDetails orderDetails = null;
			for (int i = 0; i < orderDetailsList.size(); i++) {
				OrderDetails exOrderDetails = orderDetailsList.get(i);
				if (exOrderDetails.getFoodCart().getCartId() == foodCart.getCartId()) {
					exOrderDetails.setFoodCart(foodCart);
					orderDetails = exOrderDetails;
					flag = false;
				}
			}

			if (flag) {
				orderDetails = new OrderDetails();
				orderDetails.setFoodCart(foodCart);
				orderDetails.setOrderDate(LocalDateTime.now());
				orderDetails.setOrderStatus("pending..!");
			}
			orderRepo.save(orderDetails);
			return orderDetails;
		} else {
			throw new OrderDetailsException("order does not exist...!");
		}
	}

	@Override
	public OrderDetails removeOrder(String key, Integer orderId) throws OrderDetailsException, LoginException {
		
		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
		Optional<OrderDetails> opt = orderRepo.findById(orderId);
		if (opt.isPresent()) {
			OrderDetails deletedOrder = opt.get();
			orderRepo.delete(deletedOrder);
			return deletedOrder;
		} else {
			throw new OrderDetailsException("order does not exist...!");
		}
	}

	@Override
	public OrderDetails viewOrder(String key, Integer orderId) throws OrderDetailsException, LoginException {
		
		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
		Optional<OrderDetails> opt = orderRepo.findById(orderId);
		if (opt.isPresent()) {
			OrderDetails order = opt.get();
			return order;
		} else {
			throw new OrderDetailsException("order does not exist...!");
		}
	}
	
	// Method to reschedule an order
	public String rescheduleOrder(Integer orderId, String newDeliveryTime) throws OrderDetailsException {
					
		OrderDetails order = orderRepo.findById(orderId).orElse(null);
		if (order == null) {
			throw new OrderDetailsException("order does not exist...!");
		}
		// Check if the order is already completed or cancelled
		if ("Cancelled".equalsIgnoreCase(order.getOrderStatus()) || "Completed".equalsIgnoreCase(order.getOrderStatus())) {
			throw new OrderDetailsException("Order cannot be rescheduled as it is either already cancelled or completed");
		}
		//order.setDeliveryTime(newDeliveryTime); // Update the delivery time
		//orderRepository.save(order); // Save the updated order
		return new String("Order has been rescheduled successfully");
	}
	
	public String getOrderStatus(List<OrderDetails> orderDetailsList) {
        // Count orders with different statuses
        long pendingOrders = orderDetailsList.stream()
                .filter(order -> "PENDING".equals(order.getOrderStatus()))
                .count();
        long completedOrders = orderDetailsList.stream()
                .filter(order -> "COMPLETED".equals(order.getOrderStatus()))
                .count();
        long canceledOrders = orderDetailsList.stream()
                .filter(order -> "CANCELED".equals(order.getOrderStatus()))
                .count();

        // Return the counts as a formatted string
        return String.format("Pending Orders: %d, Completed Orders: %d, Canceled Orders: %d", 
                             pendingOrders, completedOrders, canceledOrders);
    }

}
