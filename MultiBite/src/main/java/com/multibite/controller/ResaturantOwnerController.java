package com.multibite.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multibite.exception.LoginException;
import com.multibite.exception.RestaurantException;
import com.multibite.exception.RestaurantOwnerException;
import com.multibite.model.ItemDTO;
import com.multibite.model.LoginDTO;
import com.multibite.model.OrderDetails;
import com.multibite.model.Restaurant;
import com.multibite.model.RestaurantMenu;
import com.multibite.model.RestaurantOwner;
import com.multibite.service.LoginService;
import com.multibite.service.RestaurantControlledServices;

@RestController
@RequestMapping("")
public class ResaturantOwnerController {

	@Autowired
	private LoginLogoutController loginLogoutController;
	
	@Autowired
	RestaurantControlledServices restaurantControlledServices;
	
	@PostMapping("/register")
    public RestaurantOwner register(@RequestBody RestaurantOwner owner) throws RestaurantOwnerException {
        try {
			return restaurantControlledServices.registerRestaurantOwner(owner);
		} catch (LoginException | RestaurantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return owner;
    }

    // Login - Delegates to LoginLogoutController
    /*@PostMapping("/owner/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) throws Exception {
        return loginLogoutController.logIn(loginDTO);
    }

    // Logout - Delegates to LoginLogoutController
    @PostMapping("/owner/logout")
    public ResponseEntity<String> logout(@RequestParam String role, @RequestParam String key) throws Exception {
        return loginLogoutController.logout(role, key);
    }*/    
    
    @PostMapping("/owner/addResturant")
    public ResponseEntity<Restaurant> addRestaurant(@RequestParam String key, @RequestParam Restaurant restaurant) throws RestaurantException, LoginException,RestaurantOwnerException
    {
    	 try {
    		 	Restaurant savedRestaurant = restaurantControlledServices.addRestaurant(key,restaurant);
    			return new ResponseEntity<Restaurant>(savedRestaurant, HttpStatus.ACCEPTED); 			
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null; 		  
    }
    
    @PutMapping("/owner/updateResturant/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestParam String key, @RequestParam Integer restaurantId, @RequestParam Restaurant restaurant) throws RestaurantException, LoginException,RestaurantOwnerException
    {
    	 try {
    		 	Restaurant savedRestaurant = restaurantControlledServices.updateRestaurant(key,restaurantId,restaurant);
    			return new ResponseEntity<Restaurant>(savedRestaurant, HttpStatus.ACCEPTED); 			
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null; 		  
    }
   
    @PostMapping("/owner/addRestMenu/{restaurantId}")
    public ResponseEntity<RestaurantMenu> addRestaurantMenu(@RequestParam String key, @RequestParam Integer restaurantId, @RequestParam RestaurantMenu menu) throws RestaurantException, LoginException,RestaurantOwnerException
    {
    	 try {
    		    RestaurantMenu savedRestMenu = restaurantControlledServices.addRestaurantMenu(key,restaurantId,menu);
    			return new ResponseEntity<RestaurantMenu>(savedRestMenu, HttpStatus.ACCEPTED); 			
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null; 		  
    }
    
    @PutMapping("/owner/updateRestMenu/{menuId}")
    public ResponseEntity<RestaurantMenu> updateRestaurantMenu(@RequestParam String key, @RequestParam Integer menuId, @RequestParam List<ItemDTO> menuitems) throws RestaurantException, LoginException
    {
    	
    	 try {
    		 	RestaurantMenu savedRestMenu = restaurantControlledServices.updateMenu(key,menuId,menuitems);
 				return new ResponseEntity<RestaurantMenu>(savedRestMenu, HttpStatus.ACCEPTED); 	
    		   
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;				  
    }
    
    @DeleteMapping("/owner/deleteMenuItems")
    public ResponseEntity<RestaurantMenu> removeRestaurantMenuItem(@RequestParam String key, @RequestParam Integer restaurantId, @RequestParam Integer menuId, @RequestParam List<String> removeitems) throws RestaurantException, LoginException
    {    	
    	RestaurantMenu menu = null;
    	 try {
    		   Restaurant restaurant = restaurantControlledServices.getRestaurant(key,restaurantId);
    		   List<ItemDTO>list = restaurant.getItemList();
    		   for (String removeItem : removeitems) {    			    
    			   Iterator<ItemDTO> iterator = list.iterator();
    			   while (iterator.hasNext()) {
    		        ItemDTO item = iterator.next();    		       
    		        if (item.getItemName().equals(removeitems)) {
    		            iterator.remove(); 
    		            break; 
    		        }
    		    }
    		}
    		   RestaurantMenu savedRestMenu = restaurantControlledServices.updateMenu(key,menuId,list);
				return new ResponseEntity<RestaurantMenu>(savedRestMenu, HttpStatus.ACCEPTED); 	
    		   
    		   
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;					  
    }
    
    
    @GetMapping("/owner/getRestMenu")
    public ResponseEntity<RestaurantMenu> getRestaurantMenu(@RequestParam String key, @RequestParam Integer restaurantId) throws RestaurantException, LoginException
    {    	
    	 try {
    		 RestaurantMenu restaurantMenu = restaurantControlledServices.getRestaurant(key,restaurantId).getRestaurantMenu();
    		   return new ResponseEntity<RestaurantMenu>(restaurantMenu, HttpStatus.ACCEPTED);
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;				  
    }
    
    @GetMapping("/owner/viewOrders")
    public ResponseEntity<List<OrderDetails>> viewOrders(@RequestParam String key, @RequestParam Integer restaurantId) throws RestaurantException, LoginException
    {    	
    	 try {
    		 List<OrderDetails> orders = restaurantControlledServices.viewOrders(key,restaurantId);
    		   return new ResponseEntity<List<OrderDetails>>(orders, HttpStatus.ACCEPTED);
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;				  
    }
    
    @PutMapping("/owner/updateStatus")
    public ResponseEntity<OrderDetails> updateOrderStatus(@RequestParam String key, @RequestParam Integer orderId, String status) throws RestaurantException, LoginException, RestaurantOwnerException
    {    	
    	 try {
    		 	OrderDetails order = restaurantControlledServices.updateOrderStatus(key,orderId,status);
    		   return new ResponseEntity<OrderDetails>(order, HttpStatus.ACCEPTED);
 		} catch (LoginException | RestaurantException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		return null;				  
    }

}
