package com.multibite.model;

public class ActiveUser {
    private long activeUsersCount;

    // Constructor
    public ActiveUser(long activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }

    // Getter and Setter
    public long getActiveUsersCount() {
        return activeUsersCount;
    }

    public void setActiveUsersCount(long activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }
}
