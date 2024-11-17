package com.multibite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multibite.exception.AddressException;
import com.multibite.exception.CustomerException;
import com.multibite.exception.ItemException;
import com.multibite.exception.LoginException;
import com.multibite.exception.RestaurantException;
import com.multibite.model.Customer;
import com.multibite.model.Restaurant;
import com.multibite.model.RestaurantMenu;
import com.multibite.service.AddressService;
import com.multibite.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService resService;

	@PostMapping("/add")
	public ResponseEntity<Restaurant> addRestaurant(@RequestParam(required = false) String key,
			@RequestBody Restaurant res) throws RestaurantException, LoginException {
		Restaurant restaurant = resService.addRestaurant(key, res);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestParam(required = false) String key,
			@RequestBody Restaurant res) throws RestaurantException, LoginException {
		Restaurant restaurant = resService.updateRestaurant(key, res);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Restaurant> deleteRestaurant(@RequestParam(required = false) String key, Integer restaurantId)
			throws RestaurantException, LoginException {
		Restaurant restaurant = resService.removeRestaurant(key, restaurantId);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<Restaurant> viewRestaurant(@RequestParam(required = false) String key,
			@PathVariable("id") Integer restaurantId) throws RestaurantException, LoginException {
		Restaurant restaurant = resService.viewRestaurantById(key, restaurantId);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/view")
	public ResponseEntity<List<Restaurant>> viewAllRestaurant(@RequestParam(required = false) String key)
			throws RestaurantException, LoginException {
		List<Restaurant> restaurant = resService.getAllRestaurants(key);
		return new ResponseEntity<List<Restaurant>>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/findNearByRestaurantByCity/{city}")
	public ResponseEntity<List<Restaurant>> findNearByRestaurantByCityHandler(
			@RequestParam(required = false) String key, @PathVariable("city") String city)
			throws RestaurantException, AddressException, LoginException {
		List<Restaurant> restaurantList = resService.viewNearByRestaurant(key, city);
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.OK);
	}

	@GetMapping("/findNearByRestaurantByItemName/{item}")
	public ResponseEntity<List<Restaurant>> viewRestaurantByItemNameHandler(@RequestParam(required = false) String key,
			@PathVariable("item") String item) throws RestaurantException, ItemException, LoginException {
		List<Restaurant> restaurantList = resService.viewRestaurantByItemName(key, item);
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.OK);
	}
	
	@GetMapping("/getRestMenu/{restaurantId}")
    public ResponseEntity<RestaurantMenu> getRestaurantMenu(@RequestParam String key, @RequestParam Integer restaurantId) throws RestaurantException, LoginException
    {    	
    	 try {    		 
    		 RestaurantMenu restaurantMenu = resService.viewRestaurantById(key, restaurantId).getRestaurantMenu();
    		   return new ResponseEntity<RestaurantMenu>(restaurantMenu, HttpStatus.ACCEPTED);
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;				  
    }

}
