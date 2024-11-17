package com.multibite.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ItemDTO {
	@Id
	private Integer itemId;
	private Integer catergoryId;
	private String itemName;
	private Integer quantity;//availability
	private Double cost;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getCatergoryId() {
		return catergoryId;
	}
	public void setCatergoryId(Integer catergoryId) {
		this.catergoryId = catergoryId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public List<Restaurant> getRestaurants() {
		// TODO Auto-generated method stub
		return null;
	}
	
    @ManyToOne
    @JoinColumn(name = "restaurant_id")  // Foreign key to restaurant
    private Restaurant restaurant;
}
