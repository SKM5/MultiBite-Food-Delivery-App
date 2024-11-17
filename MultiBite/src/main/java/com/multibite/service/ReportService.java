package com.multibite.service;

import com.multibite.model.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    // Method to generate most popular restaurants report
    public Report generateMostPopularRestaurantsReport() {
        // Sample data, replace with real logic
        List<String> popularRestaurants = List.of("Restaurant A", "Restaurant B", "Restaurant C");
        return new Report("Most Popular Restaurants", java.time.LocalDate.now(), popularRestaurants);
    }

    // Method to generate average delivery time report
    public Report generateAverageDeliveryTimeReport() {
        // Sample data, replace with real logic
        double averageDeliveryTime = 30.5; // example time in minutes
        return new Report("Average Delivery Time", java.time.LocalDate.now(), averageDeliveryTime);
    }

    // Method to generate order trends report
    public Report generateOrderTrendsReport() {
        // Sample data, replace with real logic
        List<String> orderTrends = List.of("Peak Hours: 6-8 PM", "Popular Orders: Pizza, Burgers");
        return new Report("Order Trends", java.time.LocalDate.now(), orderTrends);
    }
}
