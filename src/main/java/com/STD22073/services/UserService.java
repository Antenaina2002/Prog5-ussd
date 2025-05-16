package com.STD22073.services;

import com.STD22073.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User createUser(String phoneNumber, String password) {
        User user = new User(phoneNumber, password);
        users.put(phoneNumber, user);
        return user;
    }

    public User authenticate(String phoneNumber, String password) {
        User user = users.get(phoneNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUser(String phoneNumber) {
        return users.get(phoneNumber);
    }
}