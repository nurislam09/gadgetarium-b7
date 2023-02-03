package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;
import com.example.gadgetariumb7.db.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, User> users = new HashMap<>();
    @Override
    public void addUser(User user) {
        user.put(user.getFirstName(),user);

    }

    @Override
    public void addToFavorites(String username, Product product) {
        User user = users.get(username);
        if (user != null) {
            user.addToFavorites(product);
        }

    }
}
