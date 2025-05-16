package com.STD22073.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String phoneNumber;
    private String password;
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = 0.0;
    }
}
