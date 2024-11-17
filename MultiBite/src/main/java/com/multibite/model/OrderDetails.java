package com.multibite.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private LocalDateTime orderDate;

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	public FoodCart getFoodCart() {
		return foodCart;
	}
	public void setFoodCart(FoodCart foodCart) {
		this.foodCart = foodCart;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@OneToOne(targetEntity=FoodCart.class)
	private FoodCart foodCart;
	private String orderStatus;
	
	@ManyToOne
    @JoinColumn(name = "restaurant_id") // Foreign key column in OrderDetails table
    private Restaurant restaurant;

	public void rescheduleOrder(Integer orderId2, String newDeliveryTime) {
		// TODO Auto-generated method stub
		
	} 

}
