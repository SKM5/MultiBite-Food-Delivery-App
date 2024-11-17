package com.multibite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multibite.exception.AdminException;
import com.multibite.exception.OrderDetailsException;
import com.multibite.model.ActiveUser;
import com.multibite.model.DeliveryActivity;
import com.multibite.model.OrderDetails;
import com.multibite.model.Report;
import com.multibite.repository.OrderDetailsRepo;
import com.multibite.service.AdminMonitoringService;
import com.multibite.service.AdminService;
import com.multibite.service.OrderService;
import com.multibite.service.ReportService;
import com.multibite.service.RestaurantControlledServices;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private OrderDetailsRepo orderRepo;
	
	@Autowired
	private OrderService orderService;
	
	//@Autowired
	//private RestaurantControlledServices restaurantOwnerControlledServices;
	
	//@Autowired
	//private DeliveryPersonnelService deliveryPersonnelService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private final AdminMonitoringService adminMonitoringService;
	
	@PostMapping("/new")
	public ResponseEntity<String> createAdmin() throws AdminException{
		String result=adminService.createNewAdmin();
		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}
	
	// Common endpoint for managing users (customers, restaurant owners, delivery personnel)
    @PostMapping("/users/{userType}")
    public ResponseEntity<String> manageUser(
            @RequestBody Object user, 
            @RequestParam String action,
            @PathVariable String userType) throws AdminException {

        String result = null;
        
        // Ensure userType is valid
        if (!userType.equalsIgnoreCase("customer") && 
            !userType.equalsIgnoreCase("restaurantOwner") &&
            !userType.equalsIgnoreCase("deliveryPersonnel")) {
            throw new AdminException("Invalid user type");
        }

        switch (action.toLowerCase()) {
            case "create":
                result = adminService.createUser(userType, user);
                break;
            case "update":
                result = adminService.updateUser(userType, user);
                break;
            case "deactivate":
                result = adminService.deactivateUser(userType, user);
                break;
            default:
                throw new AdminException("Invalid action");
        }
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
	
    @GetMapping("/orders")
	public ResponseEntity<Object> viewAllOrders() throws AdminException {
		List<OrderDetails> orders = orderRepo.findAll(); // Fetch all orders
		return new ResponseEntity<Object>(orders, HttpStatus.OK);
	}
    
    @GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDetails> viewOrder(@PathVariable Integer orderId) throws AdminException {
		OrderDetails order = orderRepo.findByOrderId(orderId);
		if (order == null) {
			return new ResponseEntity<OrderDetails>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDetails>(order, HttpStatus.OK);
	}
    
    @PostMapping("/orders/{orderId}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) throws AdminException {
    	OrderDetails order = orderRepo.findByOrderId(orderId);
    	if (order == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
    	else orderRepo.delete(order);
		return new ResponseEntity<String>("Order cancelled", HttpStatus.OK);
	}

	// Endpoint to reschedule an order
	@PostMapping("/orders/{orderId}/reschedule")
	public ResponseEntity<String> rescheduleOrder(@PathVariable Integer orderId, @RequestParam String newDeliveryTime) throws AdminException, OrderDetailsException {
		OrderDetails order = orderRepo.findByOrderId(orderId);
		if (order == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
    	else    		
    		orderService.rescheduleOrder(orderId, newDeliveryTime);
		return new ResponseEntity<>("rescheduled order", HttpStatus.OK);
	}
    
	@Autowired
	public AdminController(ReportService reportService) {
        this.adminMonitoringService = null;
		this.reportService = reportService;
    }
	
	@GetMapping("/most-popular-restaurants")
    public Report getMostPopularRestaurantsReport() {
        return reportService.generateMostPopularRestaurantsReport();
    }
	
	@GetMapping("/average-delivery-time")
    public Report getAverageDeliveryTimeReport() {
        return reportService.generateAverageDeliveryTimeReport();
    }
	
	@GetMapping("/order-trends")
    public Report getOrderTrendsReport() {
        return reportService.generateOrderTrendsReport();
    }
	
	public AdminController(AdminMonitoringService adminMonitoringService) {
        this.adminMonitoringService = adminMonitoringService;
    }
	
	@GetMapping("/active-users")
    public ActiveUser getActiveUsers() {
        return adminMonitoringService.getActiveUserCount();
    }

    // Endpoint to get delivery activity
    @GetMapping("/delivery-activity")
    public DeliveryActivity getDeliveryActivity() {
        return adminMonitoringService.getDeliveryActivity();
    }

    // Endpoint to get order status
    @GetMapping("/order-status")
    public String getOrderStatus() {
        // Fetch order details from DB or service layer (here using mock data)
        List<OrderDetails> orderDetailsList = getOrderDetails();  // Replace with real data fetch

        return adminMonitoringService.getOrderStatus(orderDetailsList);
    }
    
    private List<OrderDetails> getOrderDetails() {
            
    	    List<OrderDetails> listOrder = null;
    	    OrderDetails order1= new OrderDetails();
    	    order1.setOrderStatus("PENDING");
    	    OrderDetails order2= new OrderDetails();
    	    order2.setOrderStatus("COMPLETED");
    	    OrderDetails order3= new OrderDetails();
    	    order3.setOrderStatus("CANCELED");
    	    OrderDetails order4= new OrderDetails();
    	    order4.setOrderStatus("PENDING");
    	    OrderDetails order5= new OrderDetails();
    	    order5.setOrderStatus("COMPLETED");           
    	    listOrder.add(order1);
    	    listOrder.add(order2);
    	    listOrder.add(order3);
    	    listOrder.add(order4);
    	    listOrder.add(order5);
    	    
    	    return listOrder;
    }

}
