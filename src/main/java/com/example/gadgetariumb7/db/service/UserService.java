package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.User;

public interface UserService {
    public void addUser(User user);
    public void addToFavorites(String username, Product product);

}
