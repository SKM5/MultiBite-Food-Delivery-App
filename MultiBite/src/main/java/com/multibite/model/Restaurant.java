package com.multibite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer restaurantId;
	
	@OneToOne(cascade = CascadeType.ALL)  // Correct mapping for single RestaurantMenu per Restaurant
	@JoinColumn(name = "menu_id")
	private RestaurantMenu menu;
	
	@NotNull(message = "Name is require")
	private String restaurantName;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<OrderDetails> listOrderDetails = new ArrayList<OrderDetails>();
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@MapKey(name = "orderId")
	private Map<Integer,OrderDetails> mapOrderStatus;
	
	
	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public RestaurantMenu getRestaurantMenu() {
		return menu;
	}

	public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
		this.menu = restaurantMenu;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<ItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemDTO> itemList) {
		this.itemList = itemList;
	}

	public String getOwnerName() {
		return OwnerName;
	}

	public void setOwnerName(String managerName) {
		this.OwnerName = managerName;
	}

	public String getContactNunber() {
		return contactNunber;
	}

	public void setContactNunber(String contactNunber) {
		this.contactNunber = contactNunber;
	}	
	
	public List<OrderDetails> getlistOrder() {		
		return listOrderDetails;
	}
	
	public void setOrder(OrderDetails order) {
		listOrderDetails.add(order);
		mapOrderStatus.put(order.getOrderId(), order);
	}
	
	public String getOrderStatus(Integer orderId) {
		OrderDetails order = mapOrderStatus.get(orderId);
		return order.getOrderStatus();
	}
	
	public void setOrderStatus(Integer orderId,String orderStatus) {
		OrderDetails order = mapOrderStatus.get(orderId);
		order.setOrderStatus(orderStatus);
	}

	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;

	@JsonIgnore
	@ManyToMany(targetEntity = Item.class, cascade = CascadeType.ALL, mappedBy = "restaurants")
	private List<ItemDTO> itemList = new ArrayList<>();
	private String OwnerName;

	@Size(min = 10, max = 10, message = "Mobile require only 10 digit")
	private String contactNunber;
}
