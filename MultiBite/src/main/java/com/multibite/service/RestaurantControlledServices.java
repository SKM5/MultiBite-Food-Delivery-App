package com.multibite.service;

import com.multibite.exception.LoginException;
import com.multibite.exception.RestaurantException;
import com.multibite.exception.RestaurantOwnerException;
import com.multibite.model.RestaurantOwner;
import com.multibite.model.Restaurant;
import com.multibite.model.RestaurantMenu;
import com.multibite.model.Item;
import com.multibite.model.ItemDTO;
import com.multibite.model.OrderDetails;

import java.util.List;

public interface RestaurantControlledServices {

    // 1. Register RestaurantOwner 
    public RestaurantOwner registerRestaurantOwner(RestaurantOwner restaurantOwner) throws LoginException, RestaurantException, RestaurantOwnerException;

    // 2. Add a restaurant to the system
    public Restaurant addRestaurant(String key, Restaurant restaurant) throws RestaurantException, LoginException,RestaurantOwnerException;

    // 3. Remove a restaurant
    public void removeRestaurant(String key, Integer restaurantId) throws RestaurantException, LoginException,RestaurantOwnerException;

    // 4. Add a restaurant menu
    public RestaurantMenu addRestaurantMenu(String key, Integer restaurantId, RestaurantMenu menu) throws RestaurantException, LoginException,RestaurantOwnerException;

    // 5. Update items in the menu
    public RestaurantMenu updateMenu(String key, Integer menuId, List<ItemDTO> items) throws RestaurantException, LoginException;

    // 6. View orders of a restaurant
    public List<OrderDetails> viewOrders(String key, Integer restaurantId) throws RestaurantException, LoginException;

    // 7. Update the status of an order
    public OrderDetails updateOrderStatus(String key, Integer orderId, String status) throws RestaurantException, LoginException,RestaurantOwnerException;

    // 8. Update restaurant details
    public Restaurant updateRestaurant(String key, Integer restaurantId, Restaurant restaurant) throws RestaurantException, LoginException;
    
    public Restaurant getRestaurant(String key, Integer restaurantId) throws RestaurantException, LoginException;
}

