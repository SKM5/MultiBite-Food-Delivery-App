package com.multibite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multibite.model.RestaurantMenu;
import com.multibite.model.RestaurantOwner;

@Repository
public interface RestaurantMenuRepo extends JpaRepository<RestaurantMenu, Long> {
	public RestaurantMenu findByMenuId(Integer id);
}
