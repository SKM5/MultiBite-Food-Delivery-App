package com.multibite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multibite.exception.AdminException;
import com.multibite.model.ActiveUser;
import com.multibite.model.Admin;
import com.multibite.model.Customer;
import com.multibite.model.DeliveryActivity;
import com.multibite.model.RestaurantOwner;
import com.multibite.repository.AdminRepo;
import com.multibite.repository.CustomerRepo;
import com.multibite.repository.RestaurantOwnerRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
    private CustomerRepo customerRepository;
    
    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepository;
    
    //@Autowired
    //private DeliveryPersonnelRepo deliveryPersonnelRepository;

	@Override
	public String createNewAdmin() throws AdminException {
		// TODO Auto-generated method stub

		String name = "Admin";
		String email = "admin@gmail.com";
		String password = "password";

		Admin existsAdmin = adminRepo.findByEmail(email);
		if (existsAdmin != null)
			throw new AdminException("Admin created => Email : " + email + ", Password : " + password);

		Admin admin = new Admin();
		admin.setName(name);
		admin.setEmail(email);
		admin.setPassword(password);
		
		adminRepo.save(admin);

		return "Admin created => Email : " + email + ", Password : " + password;
	}
	
	// Common method to create a user (customer, restaurant owner, or delivery personnel)
    public String createUser(String userType, Object user) throws AdminException {
        String result = null;
        
        switch (userType.toLowerCase()) {
            case "customer":
                Customer customer = (Customer) user;
                customerRepository.save(customer);
                result = "Customer created successfully";
                break;
            case "restaurant-owner":
                RestaurantOwner restaurantOwner = (RestaurantOwner) user;
                restaurantOwnerRepository.save(restaurantOwner);
                result = "Restaurant owner created successfully";
                break;
            case "delivery-personnel":
                //DeliveryPersonnel deliveryPersonnel = (DeliveryPersonnel) user;
                //deliveryPersonnelRepository.save(deliveryPersonnel);
                result = "Delivery personnel created successfully";
                break;
            default:
                throw new AdminException("Invalid user type");
        }
        return result;
    }

    // Common method to update a user (customer, restaurant owner, or delivery personnel)
    public String updateUser(String userType, Object user) throws AdminException {
        String result = null;
        
        switch (userType.toLowerCase()) {
            case "customer":
                Customer customer = (Customer) user;
                customerRepository.save(customer); // Or use update method if available
                result = "Customer updated successfully";
                break;
            case "restaurant-owner":
                RestaurantOwner restaurantOwner = (RestaurantOwner) user;
                restaurantOwnerRepository.save(restaurantOwner); // Or use update method
                result = "Restaurant owner updated successfully";
                break;
            case "delivery-personnel":
                //DeliveryPersonnel deliveryPersonnel = (DeliveryPersonnel) user;
                //deliveryPersonnelRepository.save(deliveryPersonnel); // Or use update method
                result = "Delivery personnel updated successfully";
                break;
            default:
                throw new AdminException("Invalid user type");
        }
        return result;
    }

    // Common method to deactivate a user (customer, restaurant owner, or delivery personnel)
    public String deactivateUser(String userType, Object user) throws AdminException {
        String result = null;
        
        switch (userType.toLowerCase()) {
            case "customer":
                Customer customer = (Customer) user;
                //customer.setActive(false); // Deactivate customer
                customerRepository.save(customer);
                result = "Customer deactivated successfully";
                break;
            case "restaurant-owner":
                RestaurantOwner restaurantOwner = (RestaurantOwner) user;
                //restaurantOwner.setActive(false); // Deactivate restaurant owner
                restaurantOwnerRepository.save(restaurantOwner);
                result = "Restaurant owner deactivated successfully";
                break;
            case "delivery-personnel":
                //DeliveryPersonnel deliveryPersonnel = (DeliveryPersonnel) user;
                //deliveryPersonnel.setActive(false); // Deactivate delivery personnel
                //deliveryPersonnelRepository.save(deliveryPersonnel);
                result = "Delivery personnel deactivated successfully";
                break;
            default:
                throw new AdminException("Invalid user type");
        }
        return result;
    }
    
    public ActiveUser getActiveUserCount() {
        // In real implementation, fetch from database
        long activeUsersCount = 1200;  // Sample number
        return new ActiveUser(activeUsersCount);
    }

    // Sample method to get delivery activity
    public DeliveryActivity getDeliveryActivity() {
        // In real implementation, fetch from database
        long ongoingDeliveries = 250;  // Sample number
        long completedDeliveries = 1500;  // Sample number
        return new DeliveryActivity(ongoingDeliveries, completedDeliveries);
    }
    
    

}
