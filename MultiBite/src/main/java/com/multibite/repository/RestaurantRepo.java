package com.multibite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multibite.model.Restaurant;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

	public Restaurant findByRestaurantId(Integer restaurantId);
	public Restaurant findByRestaurantName(String name);
}
