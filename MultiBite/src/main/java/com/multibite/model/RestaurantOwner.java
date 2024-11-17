// RestaurantOwner.java
package com.multibite.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RestaurantOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @NotNull(message = "Owner name is mandatory")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;
    
    //@OneToMany(mappedBy = "restaurantOwner")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_menu_id", referencedColumnName = "menuId")
    private RestaurantMenu restaurantMenu;
    
 // Default constructor
    public RestaurantOwner() {}

    // Parameterized constructor
    public RestaurantOwner(String email, String password, String name, Restaurant restaurant, 
                           List<OrderDetails> orderDetails) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.restaurant = restaurant;        
    }
   
    // Getters and Setters
    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant newRestaurant) {
    	restaurant = newRestaurant;
    }

    public String getRestaurantName() {
        return restaurant.getRestaurantName();
    }

    public void setRestaurantName(String restaurantName) {
    	restaurant.setRestaurantName(restaurantName);
    }

    public RestaurantMenu getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }
    
    public String getContactNumber() {
        return restaurant != null ? restaurant.getContactNunber() : null;
    }

    public void setContactNumber(String contactNumber) {
        if (restaurant != null) {
            restaurant.setContactNunber(contactNumber);
        }
    }
}