package com.multibite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multibite.exception.RestaurantOwnerException;
import com.multibite.model.Customer;
import com.multibite.model.RestaurantOwner;

@Repository
public interface RestaurantOwnerRepo extends JpaRepository<RestaurantOwner, Integer> {

	public RestaurantOwner findByEmail(String email) throws RestaurantOwnerException;
	public RestaurantOwner findByUserId(Integer id);
}