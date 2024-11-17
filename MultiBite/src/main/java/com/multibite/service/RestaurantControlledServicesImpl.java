package com.multibite.service;

import com.multibite.exception.AddressException;
import com.multibite.exception.ItemException;
import com.multibite.exception.LoginException;
import com.multibite.exception.RestaurantException;
import com.multibite.exception.RestaurantOwnerException;
import com.multibite.model.CurrentUserSession;
import com.multibite.model.Restaurant;
import com.multibite.model.RestaurantOwner;
import com.multibite.model.RestaurantMenu;
import com.multibite.model.Item;
import com.multibite.model.ItemDTO;
import com.multibite.model.LoginDTO;
import com.multibite.model.OrderDetails;
import com.multibite.repository.RestaurantOwnerRepo;
import com.multibite.repository.RestaurantRepo;
import com.multibite.repository.RestaurantMenuRepo;
import com.multibite.repository.ItemRepo;
import com.multibite.repository.OrderDetailsRepo;
import com.multibite.repository.CurrentUserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantControlledServicesImpl implements RestaurantControlledServices {

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepository;
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private RestaurantMenuRepo restaurantMenuRepository;

    @Autowired
    private ItemRepo itemRepository;

    @Autowired
    private OrderDetailsRepo orderRepository;

    @Autowired
    private CurrentUserSessionRepo currentUserSessionRepository;
    

    @Override
    public RestaurantOwner registerRestaurantOwner(RestaurantOwner restaurantOwner) throws LoginException, RestaurantException, RestaurantOwnerException {
            	
    	RestaurantOwner existingOwner = restaurantOwnerRepository.findByEmail(restaurantOwner.getEmail());

		if (existingOwner != null) {
			throw new RestaurantOwnerException("Resaturant Owner email alreday exists!");
		} 
		// Save the new RestaurantOwner to the database
		RestaurantOwner savedOwner = restaurantOwnerRepository.save(restaurantOwner);

	    // Login the owner after successful registration
	    LoginDTO loginDTO = new LoginDTO();
	    loginDTO.setEmail(savedOwner.getEmail());
	    loginDTO.setPassword(savedOwner.getPassword()); 		
	 
		// Assuming you have an instance of LoginService (could be injected via constructor or setter)
	    String loginResponse = this.loginService.loginAccount(loginDTO);
	    
	    if (loginResponse != null) {
            System.out.println("Restaurant Owner logged in successfully: " + loginResponse);
        } else {
            throw new LoginException("Login failed after registration.");
        }
	    
        return savedOwner;
    }
 
    
    @Override
    public Restaurant addRestaurant(String key, Restaurant restaurant) throws RestaurantException, LoginException {
        // Check if the user is logged in with a valid session key
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("RestaurantOwner")) {
            throw new LoginException("Invalid login session");
        }
               
        RestaurantOwner restaurantOwner = null;
		try {
			restaurantOwner = restaurantOwnerRepository.findByEmail(currentSession.getEmail());
		} catch (RestaurantOwnerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (restaurantOwner  == null ) {
            throw new LoginException("Restaurant Owner not found");
        }
        // Save the restaurant
        restaurantOwner.setRestaurant(restaurant);

        // Return the saved restaurant object
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void removeRestaurant(String key, Integer restaurantId) throws RestaurantException, LoginException {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("RestaurantOwner")) {
            throw new LoginException("Invalid login session");
        }

        // Remove the restaurant if it exists
        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId);
        if (restaurant != null) {
            restaurantRepository.delete(restaurant);          
        } else {
            throw new RestaurantException("Restaurant not found");
        }
    }

    @Override
    public RestaurantMenu addRestaurantMenu(String key, Integer restaurantId, RestaurantMenu menu) throws RestaurantException, LoginException, RestaurantOwnerException {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) {
            throw new LoginException("Invalid login session");
        }

        // Find the restaurant and add the menu
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByEmail(currentSession.getEmail());
        if (restaurantOwner  == null ) {
            throw new LoginException("Restaurant Owner not found");
        }
        // Save the restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);        
        restaurant.setItemList(menu.getRestaurantMenuList());
        restaurantOwner.setRestaurant(restaurant);       
        if (restaurant != null) {
        	restaurantOwner.setRestaurantMenu(menu);
            return restaurantMenuRepository.save(menu);
        } else {
            throw new RestaurantException("Restaurant not found");
        }
    }
   
    public RestaurantMenu updateMenu(String key, Integer menuId, List<ItemDTO> items) throws RestaurantException, LoginException {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) {
            throw new LoginException("Invalid login session");
        }
        // Find the menu and update
        RestaurantMenu menu = restaurantMenuRepository.findByMenuId(menuId);
        if (menu != null) {
            menu.setRestaurantMenuList(items);
            return restaurantMenuRepository.save(menu);
        } else {
            throw new RestaurantException("Menu not found");
        }
    }

    @Override
    public List<OrderDetails> viewOrders(String key, Integer restaurantId) throws RestaurantException, LoginException {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) {
            throw new LoginException("Invalid login session");
        }
        // Fetch orders for the restaurant
        List<OrderDetails> list = null;
        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId);
        if (restaurant != null) {
        	//restaurant.
        	list = restaurant.getlistOrder();
        } else {
            throw new RestaurantException("Restaurant not found");
        }
        
        return list;
    }

    @Override
    public OrderDetails updateOrderStatus(String key, Integer orderId, String status) throws RestaurantException, LoginException {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) {
            throw new LoginException("Invalid login session");
        }

        // Update the order status
        OrderDetails order = orderRepository.findByOrderId(orderId);
        if (order != null ) {
            order.setOrderStatus(status);
            return orderRepository.save(order.getOrderStatus());
        } else {
            throw new RestaurantException("Order not found");
        }
    }

    @Override
    public Restaurant updateRestaurant(String key, Integer restaurantId, Restaurant restaurant) throws RestaurantException, LoginException 
    {
        // Check login session
        CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) 
        {
            throw new LoginException("Invalid login session");
        }

        Restaurant restrnt = this.restaurantRepository.findByRestaurantId(restaurantId);
       
        if (restrnt != null) 
        {
        	 // Update restaurant        
        	restrnt = restaurant;
        } 
        else 
        {
            throw new RestaurantException("Restaurant not found");
        }
		return restrnt;  
    }
    
    public Restaurant getRestaurant (String key, Integer restaurantId) throws RestaurantException, LoginException
    {
    	CurrentUserSession currentSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentSession == null || !currentSession.getRole().equalsIgnoreCase("owner")) 
        {
            throw new LoginException("Invalid login session");
        }

        return this.restaurantRepository.findByRestaurantId(restaurantId);
    }
	
}
        		
        		
        		
  
