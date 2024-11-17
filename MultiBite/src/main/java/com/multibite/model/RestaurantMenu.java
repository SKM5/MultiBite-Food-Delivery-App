package com.multibite.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RestaurantMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_menu_id", referencedColumnName = "menuId")  
    private List<ItemDTO> restaurantMenuList;

    public RestaurantMenu() {
    }

    public RestaurantMenu(Long id, List<ItemDTO> restaurantMenuList) {
        this.menuId = id;
        this.restaurantMenuList = restaurantMenuList;
    }

    public Long getId() {
        return menuId;
    }

    public void setId(Long menuid) {
        this.menuId = menuid;
    }

    public List<ItemDTO> getRestaurantMenuList() {
        return restaurantMenuList;
    }
    
    public List<ItemDTO> getMenuList() {
        return restaurantMenuList;
    }

    public void setRestaurantMenuList(List<ItemDTO> restaurantMenuList) {
        this.restaurantMenuList = restaurantMenuList;
    }

    @Override
    public String toString() {
        return "RestaurantMenu{" +
                "menuId=" + menuId +
                ", restaurantMenuList=" + restaurantMenuList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMenu that = (RestaurantMenu) o;
        return Objects.equals(menuId, that.menuId) &&
                Objects.equals(restaurantMenuList, that.restaurantMenuList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, restaurantMenuList);
    }
}
