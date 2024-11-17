package com.multibite.model;

public class DeliveryActivity {
    private long ongoingDeliveries;
    private long completedDeliveries;

    // Constructor
    public DeliveryActivity(long ongoingDeliveries, long completedDeliveries) {
        this.ongoingDeliveries = ongoingDeliveries;
        this.completedDeliveries = completedDeliveries;
    }

    // Getters and Setters
    public long getOngoingDeliveries() {
        return ongoingDeliveries;
    }

    public void setOngoingDeliveries(long ongoingDeliveries) {
        this.ongoingDeliveries = ongoingDeliveries;
    }

    public long getCompletedDeliveries() {
        return completedDeliveries;
    }

    public void setCompletedDeliveries(long completedDeliveries) {
        this.completedDeliveries = completedDeliveries;
    }
}
