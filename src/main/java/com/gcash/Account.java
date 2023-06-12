package com.gcash;

public class Account {
    // The original record implementation creates an immutable class as record,
    // For the purposes of this activity, I changed it to private fields and explicit getters and setters that allows for mutability.
    private String id;
    private String name;
    private Double balance;

    public Account(String id, String name, Double initialBalance) {
        this.id = id;
        this.name = name;
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double newBalance) {
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = newBalance;
    }
}
